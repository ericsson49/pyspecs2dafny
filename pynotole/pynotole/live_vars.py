
from .myast import Stmt, Block, Expr, Comprehension
from .myast import Assert, ExprStmt, AssignStmt, AugAssign, AnnAssign, WhileStmt, ForStmt, IfStmt, Return, Break, Continue, Pass
from .myast import (NameConst, Num, Str, Bytes,
                   Name, Attribute, Subscript,
                   FuncCall, BinOp, UnaryOp, BoolOp, Compare,
                   PyTuple, PyList, PySet, PyDict,
                   GeneratorExpr, ListCompr, SetCompr, DictCompr,
                   IfExp, Lambda, Starred)
from typing import Set, Mapping, Any, Sequence, Tuple, Callable
from .graph_utils import get_nodes, out_nodes


def decompose_expr(e: Expr) -> Expr:
    match e:
        case GeneratorExpr(expr, Comprehension(Name(tgt), coll, comp_if)):
            if comp_if is not None:
                coll = FuncCall(Name('<filter>'), [Lambda([tgt], comp_if), coll], [])
            return FuncCall(Name('<map>'), [Lambda([tgt], expr), coll], [])
        case GeneratorExpr(expr, Comprehension(PyTuple(tgts), coll, comp_if)) if all(isinstance(t, Name) for t in tgts):
            lam_args = [t.id for t in tgts]
            if comp_if is not None:
                coll = FuncCall(Name('<filter>'), [Lambda(lam_args, comp_if), coll], [])
            return FuncCall(Name('<map>'), [Lambda(lam_args, expr), coll], [])
        case ListCompr(expr, compr):
            return FuncCall(Name('<list>'), [decompose_expr(GeneratorExpr(expr, compr))], [])
        case SetCompr(expr, compr):
            return FuncCall(Name('<set>'), [decompose_expr(GeneratorExpr(expr, compr))], [])
        case DictCompr(key, value, compr):
            return FuncCall(Name('<dict>'), [decompose_expr(GeneratorExpr(PyTuple([key, value]), compr))], [])
        case IfExp(test, body, orelse):
            return FuncCall(Name('<ite>'), [test, Lambda([], body), Lambda([], orelse)], [])
        case _:
            assert False, e


def deconstruct2(e: Expr) -> (Sequence[Expr], Callable[[Sequence[Expr]],Expr]):
    match e:
        case NameConst() | Num() | Str() | Bytes() | Name() | Lambda():
            return [], lambda _: e
        case Attribute(value, attr):
            return [value], lambda es: Attribute(es[0], attr)
        case Subscript(value, (l, u, s)):
            res = [value]
            if l is not None:
                l_idx = len(res)
                res.append(l)
            else:
                l_idx = None
            if u is not None:
                u_idx = len(res)
                res.append(u)
            else:
                u_idx = None
            if s is not None:
                s_idx = len(res)
                res.append(s)
            else:
                s_idx = None
            return (res, lambda es: Subscript(es[0],
                                         (None if l_idx is None else es[l_idx],
                                          None if u_idx is None else es[u_idx],
                                          None if s_idx is None else es[s_idx])))
        case Subscript(value, idx) if not (isinstance(idx, tuple)):
            return [value, idx], lambda es: Subscript(es[0], es[1])
        case BinOp(left, op, right):
            return [left, right], lambda es: BinOp(es[0], op, es[1])
        case BoolOp(op, values):
            return values, lambda es: BoolOp(op, values)
        case Compare(left, op, rights):
            return [left] + rights, lambda es: Compare(es[0], op, es[1:])
        case UnaryOp(op, value):
            return [value], lambda es: UnaryOp(op, es[0])
        case FuncCall(func, args, kwargs):
            args_len = len(args)
            kws = [kw for kw, _ in kwargs]
            return ([func] + args + [arg for _, arg in kwargs],
                    lambda es: FuncCall(es[0], es[1:1+args_len], list(zip(kws, es[1+args_len:1+args_len+len(kws)]))))
        case PyTuple(elts):
            return elts, lambda es: PyTuple(es)
        case PyList(elts):
            return elts, lambda es: PyList(es)
        case PySet(elts):
            return elts, lambda es: PySet(es)
        case PyDict(keys, values):
            res = []
            for k,v in zip(keys, values):
                res.extend([k,v])
            return res, lambda es: PyDict(es[:-1:2],es[1::2])
        case ListCompr() | SetCompr() | DictCompr() | GeneratorExpr() | IfExp():
            exprs, builder = deconstruct2(decompose_expr(e))
            return exprs, lambda es: builder(es)
        case IfExp() as if_exp:
            assert False
            return deconstruct(decompose_expr(if_exp)), None
        case Starred(value):
            return [value], lambda es: Starred(es[0])
        case _:
            assert False, e


def deconstruct(e: Expr) -> Sequence[Expr]:
    match e:
        case NameConst() | Num() | Str() | Bytes() | Name():
            return []
        case Attribute(value, _):
            return [value]
        case Subscript(value, (l, u, s)):
            res = [value]
            if l is not None:
                res.append(l)
            if u is not None:
                res.append(u)
            if s is not None:
                res.append(s)
            return res
        case Subscript(value, idx) if not (isinstance(idx, tuple)):
            return [value, idx]
        case BinOp(left, _, right):
            return [left, right]
        case BoolOp(_, values):
            return values
        case Compare(left, _, rights):
            return [left] + rights
        case UnaryOp(_, value):
            return [value]
        case FuncCall(func, args, kwargs):
            return [func] + args + [arg for _, arg in kwargs]
        case PyTuple(elts):
            return elts
        case PyList(elts):
            return elts
        case PySet(elts):
            return elts
        case PyDict(keys, values):
            res = []
            for k,v in zip(keys, values):
                res.extend([k,v])
            return res
        case ListCompr() | SetCompr() | DictCompr() | GeneratorExpr() | IfExp():
            return deconstruct(decompose_expr(e))
        case Starred(value):
            return [value]
        case _:
            assert False, e

def live_var_exprs(es: Sequence[Expr]) -> Set[str]:
    res = set()
    for e in es:
        res |= live_var_expr(e)
    return res

def live_var_expr(e: Expr) -> Set[str]:
    match e:
        case Name(v):
            return {v}
        case Lambda(args, body):
            return live_var_expr(body).difference(args)
        case _:
            return live_var_exprs(deconstruct(e))


def lv_gen_kill_store_ctx(e: Expr) -> (Set[str], Set[str]):
    match e:
        case Name(v):
            return set(), {v}
        case Attribute() | Subscript():
            return live_var_expr(e), set()
        case PyTuple(elts) if all(isinstance(elt, (Name, PyTuple)) for elt in elts):
            gen, kill = set(), set()
            for elt in elts:
                g, k = lv_gen_kill_store_ctx(elt)
                gen |= g
                kill |= k
            return gen, kill
        case _:
            assert False, e

def lv_gen_kill_assgns(s: AssignStmt|AnnAssign|AugAssign) -> (Set[str], Set[str]):
    match s:
        case AssignStmt(tgt, e):
            tgt_gen, kill = lv_gen_kill_store_ctx(tgt)
            gen = live_var_expr(e) | tgt_gen
            return gen, kill
        case AnnAssign(tgt, _, e):
            tgt_gen, kill = lv_gen_kill_store_ctx(tgt)
            gen = live_var_expr(e) | tgt_gen
            return gen, kill
        case AugAssign(tgt, op, e):
            tgt_gen, kill = lv_gen_kill_store_ctx(tgt)
            gen = live_var_expr(e) | tgt_gen
            return gen, kill
        case _:
            assert False

def live_var_st(s: Stmt|Block, out: Set[str]) -> Set[str]:
    match s:
        case Assert(test, None):
            return live_var_expr(test)
        case ExprStmt(value):
            return live_var_expr(value) | out
        case AssignStmt(tgt, e):
            tgt_gen, kill = lv_gen_kill_store_ctx(tgt)
            gen = live_var_expr(e) | tgt_gen
            return out.difference(kill) | gen
        case AnnAssign(tgt, _, e):
            tgt_gen, kill = lv_gen_kill_store_ctx(tgt)
            gen = live_var_expr(e) | tgt_gen
            return out.difference(kill) | gen
        case AugAssign(tgt, op, e):
            tgt_gen, kill = lv_gen_kill_store_ctx(tgt)
            gen = live_var_expr(e) | tgt_gen
            return out.difference(kill) | gen
        case IfStmt(test, body, or_else):
            body_lvs = live_var_st(body, out)
            else_lvs = live_var_st(or_else, out)
            return live_var_expr(test) | body_lvs | else_lvs
        case WhileStmt(test, body):
            curr = out
            while True:
                prev = curr
                body_lvs = live_var_st(body, curr)
                curr = body_lvs | out | live_var_expr(test)
                if prev == curr:
                    return curr
        case ForStmt(tgts, coll, body):
            tgt_gen, tgt_kill = lv_gen_kill_store_ctx(tgts)
            curr = out
            while True:
                prev = curr
                body_lvs = live_var_st(body, curr)
                curr = body_lvs.difference(tgt_kill) | out | tgt_gen
                if prev == curr:
                    break
            return curr | live_var_expr(coll)
        case Return(None):
            return set()
        case Return(value):
            return live_var_expr(value)
        case Block([]):
            return out
        case Block([s, *rest]):
            return live_var_st(s, live_var_st(Block(rest), out))
        case Break() | Continue():
            return out
        case _:
            assert False, s


def calc_live_vars(bmap: Callable[[Any], Stmt], cfg):
    ins = {None: set()}
    outs = {}
    nexts = out_nodes(cfg)
    nodes = get_nodes(cfg)

    def gen_kill(n, out):
        match bmap(n):
            case Assert(test, None):
                return live_var_expr(test)
            case ExprStmt(value):
                return out | live_var_expr(value)
            case (AssignStmt() | AnnAssign() | AugAssign()) as s:
                gen, kill = lv_gen_kill_assgns(s)
                return (out - kill) | gen
            case IfStmt(test, _, _):
                return out | live_var_expr(test)
            case WhileStmt(test, _) if n[-1] == 'wt':
                return out | live_var_expr(test)
            case WhileStmt(_, _):
                # pre-head, which is empty
                return out
            case ForStmt(_, _, _) if n[-1] == 'ft':
                return out
            case ForStmt(tgt, _, _) if n[-1] == 'pb':
                gen, kill = lv_gen_kill_store_ctx(tgt)
                return (out - kill) | gen
            case ForStmt(_, coll, _):
                return out | live_var_expr(coll)
            case Return(None):
                return out
            case Return(value):
                return out | live_var_expr(value)
            case Pass():
                return out
            case None:
                return set()
            case _ as s:
                assert False, (n, s)
    while True:
        changed = False
        for n in nodes:
            out = set()
            for p in nexts.get(n, set()):
                out = out.union(ins.get(p, set()))
            new_in = ins.get(n, set()).union(gen_kill(n, out))
            if new_in != ins.get(n, set()):
                ins[n] = new_in
                changed = True
        if not changed:
            break
    return ins, outs


