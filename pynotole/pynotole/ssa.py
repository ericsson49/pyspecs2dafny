
from dataclasses import dataclass
from typing import Any, Sequence, Set, Optional, Tuple, Mapping
import ast
from . import myast

from .live_vars import calc_live_vars, lv_gen_kill_store_ctx, deconstruct2
from .graph_utils import in_nodes, out_nodes, get_nodes, dom_frontier


def _mk_cfg(idx, s: myast.Stmt|myast.Block, ret, loop, nxt, acc):
    match s:
        case myast.Block(stmts):
            def proc(i):
                if i < len(stmts):
                    return _mk_cfg(idx + (i,), stmts[i], ret, loop, proc(i + 1), acc)
                else:
                    return nxt
            return proc(0)
        case myast.IfStmt(test, body, orelse):
            if len(body.stmts) == 0:
                body = myast.Block([myast.Pass()])
            if len(orelse.stmts) == 0:
                orelse = myast.Block([myast.Pass()])
            b = _mk_cfg(idx + ('ib',), body, ret, loop, nxt, acc)
            e = _mk_cfg(idx + ('ie',), orelse, ret, loop, nxt, acc)
            acc.append((idx, b))
            acc.append((idx, e))
            return idx
        case myast.WhileStmt(test, body):
            while_test_node = idx + ('wt',)
            new_loop = [while_test_node, nxt]
            body_node = _mk_cfg(idx + ('wb',), body, ret, new_loop, while_test_node, acc)
            acc.append((while_test_node, body_node))
            acc.append((while_test_node, nxt))
            acc.append((idx, while_test_node))
            return idx
        case myast.ForStmt(tgts, coll, body):
            for_test_node = idx + ('ft',)
            new_loop = [for_test_node, nxt]
            pre_body_node = idx + ('pb',)
            body_node = _mk_cfg(idx + ('fb',), body, ret, new_loop, for_test_node, acc)
            acc.append((for_test_node, pre_body_node))
            acc.append((pre_body_node, body_node))
            acc.append((for_test_node, nxt))
            acc.append((idx, for_test_node))
            return idx
        case myast.Break():
            return loop[1]
        case myast.Continue():
            return loop[0]
        case myast.Return():
            acc.append((idx, ret))
            return idx
        case myast.AssignStmt() | myast.AnnAssign() | myast.AugAssign() | myast.ExprStmt() | myast.Assert() | myast.Pass():
            acc.append((idx, nxt))
            return idx
        case e:
            assert False, s
            #acc.append((idx, nxt))
            #return idx


def mk_cfg(s: myast.Stmt|myast.Block):
    acc = []
    r = _mk_cfg((), s, None, None, None, acc)
    acc.append(((), r))
    return set(acc)


def resolve(b: myast.Block, idx) -> myast.Stmt:
    match idx:
        case None:
            return None
        case ():
            return None
        case [int(i)] if len(b.stmts) == 0 and i == 0:
            return myast.Pass()
        case [int(i)]:
            return b.stmts[i]
        case [int(i), 'wt']:
            s = b.stmts[i]
            assert isinstance(s, myast.WhileStmt)
            return s
        case [int(i), 'wb', *rest]:
            s = b.stmts[i]
            assert isinstance(s, myast.WhileStmt)
            return resolve(s.body, rest)
        case [int(i), 'ft']:
            s = b.stmts[i]
            assert isinstance(s, myast.ForStmt)
            return s
        case [int(i), 'pb']:
            s = b.stmts[i]
            assert isinstance(s, myast.ForStmt)
            return s
        case [int(i), 'fb', *rest]:
            s = b.stmts[i]
            assert isinstance(s, myast.ForStmt)
            return resolve(s.body, rest)
        case [int(i), 'ib', *rest]:
            s = b.stmts[i]
            assert isinstance(s, myast.IfStmt)
            return resolve(s.body, rest)
        case [int(i), 'ie', *rest]:
            s = b.stmts[i]
            assert isinstance(s, myast.IfStmt)
            return resolve(s.orElse, rest)
        case [int(i), *rest]:
            s = b.stmts[i]
            assert isinstance(s, myast.Block)
            return resolve(s, rest)
        case _:
            assert False, idx


def get_def_nodes(b, cfg) -> Sequence[Tuple[Any,Set[str]]]:
    res = []
    for n in get_nodes(cfg):
        vs = get_defined_vars(b, n)
        if vs is not None:
            res.append((n, vs))
    return res


def get_defined_vars(b, n) -> Set[str]:
    s = resolve(b, n)
    match s:
        case myast.AssignStmt(target, _):
            return lv_gen_kill_store_ctx(target)[1]
        case myast.AnnAssign(tgt, _, _):
            return lv_gen_kill_store_ctx(tgt)[1]
        case myast.AugAssign(tgt, _, _):
            return lv_gen_kill_store_ctx(tgt)[1]
        case myast.ForStmt(tgt, _, _) if n[-1] == 'pb':
            return lv_gen_kill_store_ctx(tgt)[1]


@dataclass
class PhiFunc:
    def_: str
    uses: Mapping[Any, str]


def place_phi_funcs(stmts, cfg):
    df = dom_frontier(cfg)
    def get_df(ns):
        res = set()
        for n in ns:
            res |= df.get(n, set())
        return res

    def get_idf(ns):
        curr = get_df(ns)
        while True:
            new = curr | get_df(curr)
            if new == curr:
                return curr
            else:
                curr = new

    lv_ins, _ = calc_live_vars(lambda idx: resolve(stmts, idx), cfg)
    preds = in_nodes(cfg)
    phi_funcs: dict[Any,dict[str,PhiFunc]] = {}
    for n, vs in get_def_nodes(stmts, cfg):
        for p in get_idf({n}):
            for v in vs:
                assert p in lv_ins, (p, v)
                if v in lv_ins[p]:
                    phi_func = PhiFunc(None, {i: None for i in preds.get(p, set())})
                    phi_funcs[p] = phi_funcs.get(p, {}) | {v: phi_func}
    return phi_funcs


def rename_vars_in_expr(e: myast.Expr, remap) -> myast.Expr:
    match e:
        case myast.Name(v) if v in remap:
            return myast.Name(remap[v])
        case myast.Name():
            return e
        case _:
            exprs, builder = deconstruct2(e)
            exprs_ = [rename_vars_in_expr(ex, remap) for ex in exprs]
            return builder(exprs_)

def rename_vars_in_expr_opt(e: myast.Expr|None, remap) -> myast.Expr|None:
    if e is not None:
        return rename_vars_in_expr(e, remap)

def rename_vars_in_slice(e: myast.Expr|Tuple[myast.Expr,myast.Expr,myast.Expr], remap) -> myast.Expr|Tuple[myast.Expr,myast.Expr,myast.Expr]:
    match e:
        case (a, b, c):
            return rename_vars_in_expr_opt(a, remap), rename_vars_in_expr_opt(b, remap), rename_vars_in_expr_opt(c, remap)
        case e:
            return rename_vars_in_expr(e, remap)


def flatten_block(b: myast.Block) -> myast.Block:
    stmts = []
    for s in b.stmts:
        match s:
            case myast.Block(sts):
                stmts.extend(sts)
            case e:
                stmts.append(e)
    return myast.Block(stmts)

def split_var_ranges(b: myast.Block|myast.Stmt):
    cfg = mk_cfg(b)
    succs = out_nodes(cfg)

    phi_funcs = place_phi_funcs(b, cfg)

    new_vars = {}

    def fresh(v):
        new_v = f"{v}_{len(new_vars.get(v, set()))}"
        new_vars[v] = new_vars.get(v, set()) | { new_v }
        return new_v

    def rename_vars(idx, s: myast.Block|myast.Stmt, vs: dict[str,str]) -> (myast.Block|myast.Stmt, Mapping[str,str]):
        def process_target_uses(t: myast.Expr, vs: Mapping[str,str]) -> myast.Expr:
            match t:
                case myast.Name():
                    return t
                case myast.Attribute(v, attr):
                    v_ = rename_vars_in_expr(v, vs)
                    return myast.Attribute(v_, attr)
                case myast.Subscript(v, slice):
                    v_ = rename_vars_in_expr(v, vs)
                    slice_ = rename_vars_in_slice(slice, vs)
                    return myast.Subscript(v_, slice_)
                case myast.PyTuple(elts):
                    elts_ = []
                    for e in elts:
                        e_ = process_target_uses(e, vs)
                        elts_.append(e_)
                    return myast.PyTuple(elts_)
        def process_target_defs(t: myast.Expr, vs: Mapping[str,str]) -> (myast.Expr, Mapping[str,str]):
            match t:
                case myast.Name(v):
                    v_ = fresh(v)
                    return myast.Name(v_), vs | {v: v_}
                case myast.Attribute() | myast.Subscript():
                    return t, vs
                case myast.PyTuple(elts):
                    vs__ = vs
                    elts_ = []
                    for e in elts:
                        e_, vs__ = process_target_defs(e, vs__)
                        elts_.append(e_)
                    return myast.PyTuple(elts_), vs__

        vs = vs.copy()
        def update_phi_defs(idx):
            for v, phi_func in phi_funcs.get(idx, {}).items():
                assert phi_func.def_ is not None, idx
                vs[v] = phi_func.def_

        def make_phi_copies(vs_):
            copies = []
            for p in succs.get(idx, set()):
                for v, phi_func in phi_funcs.get(p, {}).items():
                    phi_func.uses[idx] = vs_.get(v, v)
                    if phi_func.def_ is None:
                        phi_func.def_ = fresh(v)
                    copies.append(myast.AssignStmt(myast.Name(phi_func.def_), myast.Name(vs_.get(v, v))))
            return copies

        update_phi_defs(idx)
        match s:
            case myast.Block(stmts):
                stmts_ = []
                for i, stmt in enumerate(stmts):
                    stmt_, vs = rename_vars(idx + (i,), stmt, vs)
                    stmts_.append(stmt_)
                s_, vs_ = myast.Block(stmts_), vs
            case myast.ExprStmt(value):
                value_ = rename_vars_in_expr(value, vs)
                s_, vs_ = myast.ExprStmt(value_), vs
            case myast.Assert(value, None):
                s_, vs_ = myast.Assert(rename_vars_in_expr(value, vs), None), vs
            case myast.AssignStmt(tgt, value):
                tgt_ = process_target_uses(tgt, vs)
                value_ = rename_vars_in_expr(value, vs)
                tgt__, vs_ = process_target_defs(tgt_, vs)
                s_, vs_ = myast.AssignStmt(tgt__, value_), vs_
            case myast.AnnAssign(myast.Name() as tgt, anno, value):
                value_ = rename_vars_in_expr(value, vs)
                tgt_, vs_ = process_target_defs(tgt, vs)
                s_, vs_ = myast.AnnAssign(tgt_, anno, value_), vs_
            case myast.AugAssign(tgt, op, value):
                value_ = rename_vars_in_expr(value, vs)
                tgt_ = process_target_uses(tgt, vs)
                tgt__, vs_ = process_target_defs(tgt_, vs)
                s_, vs_ = myast.AssignStmt(tgt__, myast.BinOp(tgt_, op, value_)), vs_
            case myast.WhileStmt(test, body):
                pre_head_copies = make_phi_copies(vs)
                update_phi_defs(idx + ('wt',))
                test_ = rename_vars_in_expr(test, vs)
                body_, _ = rename_vars(idx + ('wb',), body, vs)
                s_ = myast.WhileStmt(test_, body_)
                if pre_head_copies:
                    s_ = myast.Block(pre_head_copies + [s_])
                vs_ = vs
                idx = idx + ('wt',)
            case myast.ForStmt(tgt, coll, body):
                coll_ = rename_vars_in_expr(coll, vs)
                pre_head_copies = make_phi_copies(vs)
                update_phi_defs(idx + ('ft',)) # no much sense, since for test check is hidden
                tgt_ = process_target_uses(tgt, vs)
                tgt__, vs2 = process_target_defs(tgt_, vs)
                body_, _ = rename_vars(idx + ('fb',), body, vs2)
                s_ = myast.ForStmt(tgt__, coll_, body_)
                if pre_head_copies:
                    s_ = myast.Block(pre_head_copies + [s_])
                vs_ = vs2
                idx = idx + ('ft',)
            case myast.IfStmt(test, body, orelse):
                test_ = rename_vars_in_expr(test, vs)
                if len(body.stmts) == 0:
                    body = myast.Block([myast.Pass()])
                    body_, _ = rename_vars(idx + ('ib',), body, vs)
                    body_ = flatten_block(body_)
                    if len(body_.stmts) > 0 and isinstance(body_.stmts[0], myast.Pass):
                        body_ = myast.Block(body_.stmts[1:])
                else:
                    body_, _ = rename_vars(idx + ('ib',), body, vs)
                if len(orelse.stmts) == 0:
                    orelse = myast.Block([myast.Pass()])
                    orelse_, _ = rename_vars(idx + ('ie',), orelse, vs)
                    orelse_ = flatten_block(orelse_)
                    if len(orelse_.stmts) > 0 and isinstance(orelse_.stmts[0], myast.Pass):
                        orelse_ = myast.Block(orelse_.stmts[1:])
                else:
                    orelse_, _ = rename_vars(idx + ('ie',), orelse, vs)
                s_, vs_ = myast.IfStmt(test_, body_, orelse_), vs
            case myast.Break():
                s_, vs_ = s, vs
            case myast.Continue():
                s_, vs_ = s, vs
            case myast.Return(value):
                value_ = rename_vars_in_expr_opt(value, vs)
                s_, vs_ = myast.Return(value_), vs
            case myast.Pass():
                s_, vs_ = s, vs
            case _:
                assert False, s
        copies = make_phi_copies(vs_)
        if copies:
            s_ = myast.Block([s_] + copies)
        return s_, vs_

    return rename_vars((), b, {})[0]


if __name__ == "__main__":
    from transform import parse_stmts, print_st

    prog1 = """
a = 1
while a:
  a = 0
  c = 3
  while c:
    if a:
      c = 0
    else:
      c = 2
      continue
  a = c
b = a
"""
    prog2 = """
if a:
  b = a
c = b
"""
    stmts = parse_stmts(ast.parse(prog2).body)
    cfg = mk_cfg(stmts)
    print_st(split_var_ranges(stmts))
