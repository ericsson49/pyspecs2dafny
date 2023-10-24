import ast
from typing import Sequence, Callable, Set, Tuple, TypeVar, Optional

from dataclasses import dataclass
import myast
from myast import Stmt, Expr, Block, Comprehension
from myast import (Assert, VarDecl, AnnAssign, AssignStmt, AugAssign, ExprStmt, IfStmt, WhileStmt, ForStmt, Return, Break,
                   Continue, Pass)
from myast import NameConst, Num, Str,Bytes, Name, FuncCall, GeneratorExpr, ListCompr, SetCompr, \
    BinOp, Compare, BoolOp, UnaryOp, \
    Attribute, Subscript, PyTuple, PyList, PySet, PyDict, IfExp, Lambda, Starred

from live_vars import live_var_st, calc_live_vars, lv_gen_kill_store_ctx

def new_defs_and_updates(s: Stmt|Block, known_vs: Set[str], out_lvs: Set[str]) -> Tuple[Set[str], Set[str]]:
    match s:
        case ExprStmt() | Assert() | Return():
            return set(), set()
        case AssignStmt(tgt, _) | AnnAssign(tgt, _, _):
            vs = lv_gen_kill_store_ctx(tgt)[1]
            return vs.difference(known_vs), vs.intersection(known_vs)
        case IfStmt(_, body, or_else):
            body_defs, body_updates = new_defs_and_updates(body, known_vs, out_lvs)
            else_defs, else_updates = new_defs_and_updates(or_else, known_vs, out_lvs)
            assert body_defs == else_defs
            return body_defs, body_updates.union(else_updates)
        case WhileStmt(_, body):
            body_defs, body_updates = new_defs_and_updates(body, known_vs, out_lvs)
            assert len(body_defs) == 0
            return set(), body_updates
        case ForStmt(tgt, coll, body):
            tgt_vs = lv_gen_kill_store_ctx(tgt)[1]
            body_defs, body_updates = new_defs_and_updates(body, known_vs | tgt_vs, out_lvs)
            return set(), body_updates.difference(tgt_vs)
        case Block([]):
            return set(), set()
        case Block([s, *rest]):
            lvs_after = live_var_st(Block(rest), out_lvs)
            st_new_defs, st_updates = new_defs_and_updates(s, known_vs, lvs_after)
            rest_new_defs, rest_updates = new_defs_and_updates(Block(rest), known_vs.union(st_new_defs), out_lvs)
            return st_new_defs.union(rest_new_defs).intersection(out_lvs),\
                st_updates.union(rest_updates.difference(st_new_defs))
        case Break() | Continue() | Pass():
            return set(), set()
        case _:
            assert False, s

def infer_var_decls(s: Stmt|Block, known_vs: Set[str], out_lvs: Set[str]) -> Stmt|Block:
    match s:
        case ExprStmt() | Assert() | Return():
            return s
        case AssignStmt(Name(t), _) if t in known_vs:
            return s
        case AssignStmt(Name(t), v):
            return VarDecl(t, None, v)
        case AssignStmt(Subscript() | Attribute(), _):
            return s
        case AnnAssign(Name(t), anno, value):
            assert not t in known_vs
            return VarDecl(t, anno, value)
        case IfStmt(test, body, or_else):
            new_vs, _ = new_defs_and_updates(s, known_vs, out_lvs)
            new_body = infer_var_decls(body, known_vs.union(new_vs), out_lvs)
            new_else = infer_var_decls(or_else, known_vs.union(new_vs), out_lvs)
            vdecls = [VarDecl(v, None, None) for v in new_vs]
            return Block(vdecls + [IfStmt(test, new_body, new_else)])
        case WhileStmt(test, body):
            new_body = infer_var_decls(body, known_vs, out_lvs)
            return WhileStmt(test, new_body)
        case ForStmt(tgt, coll, body):
            tgt_vs = lv_gen_kill_store_ctx(tgt)[1]
            new_body = infer_var_decls(body, known_vs | tgt_vs, out_lvs)
            return ForStmt(tgt, coll, new_body)
        case Block([]):
            return s
        case Block([s, *rest]):
            lvs_after = live_var_st(Block(rest), out_lvs)
            new_s = infer_var_decls(s, known_vs, lvs_after)
            new_s_stmts = list(new_s.stmts) if isinstance(new_s, Block) else [new_s]
            new_vs, _ = new_defs_and_updates(s, known_vs, lvs_after)
            new_rest: Block = infer_var_decls(Block(rest), known_vs.union(new_vs), out_lvs)
            return Block(new_s_stmts + list(new_rest.stmts))
        case Break() | Continue() | Pass():
            return s
        case _:
            assert False, s


from constr_solver import Term, Var, Atom
from type_parser import get_base_type_env, TypingEnv

def resolve_bin_op(left, op, right, expected):
    ...

def gen_constraints(s: Stmt|Block, type_env: TypingEnv) -> Set[Tuple[str, Term, Term]]:
    cs = set()

    def add_eq(a: Term, b: Term):
        cs.add(('=:=', a, b))

    def add_st(a: Term, b: Term):
        cs.add(('<:', a, b))

    def get_type_var(v: str) -> Var:
        return Var(f"T{v}")

    def match_func_call(e: FuncCall):
        for sig in type_env.resolve_func(e.func.id):
            if sig.matches_call(len(e.args), [k for k, _ in e.kwargs]):
                return sig
        assert False, f"not func {e.func}"

    def check_expr_term(e: Expr, expected: Term):
        match e:
            case Num(_):
                add_st(Atom("int", ()), expected)
            case Name(n):
                add_st(get_type_var(n), expected)
            case FuncCall(Name(), args, kwds):
                fv = match_func_call(e)
                add_st(fv.ret, expected)
                for (param, typ, _), value in zip(fv.args[:len(args)], args):
                    check_expr_term(value, typ)
                for kwd, value in kwds:
                    _, typ, _ = fv.get_param(kwd)
                    check_expr_term(value, typ)
            case BinOp(left, op, right):
                pass
            case _:
                assert False

    match s:
        case ExprStmt(_):
            pass
        case VarDecl(tv, None, v):
            check_expr_term(v, get_type_var(tv))
        case AssignStmt(Name(t), v):
            check_expr_term(v, get_type_var(t))
        case IfStmt(t, b, e):
            check_expr_term(t, Atom("bool", ()))
            cs.update(gen_constraints(b, type_env))
            cs.update(gen_constraints(e, type_env))
        case WhileStmt(t, b):
            check_expr_term(t, Atom("bool", ()))
            cs.update(gen_constraints(b, type_env))
        case Block(stmts):
            for st in stmts:
                cs.update(gen_constraints(st, type_env))
        case _:
            assert False
    return cs

# s = parse_py(s)
#
# decls = infer_var_decls(s, set(), set("a"))
# typ_vars = gen_typ_vars(decls)
#print_st(typ_vars)

#constraints = gen_constraints(typ_vars).union([('=:=', Var("Tx"), Atom("int",())), ('=:=', Var("Tc"), Atom("int", ()))])

#print(constraints)
#print(simplify2_ext(constraints))


from marko.ext.gfm import gfm
from marko.block import FencedCode
from glob import glob

spec_dir = '/Users/avlasov/Documents/GitHub/consensus-specs/specs/'
phases = ['phase0', 'altair', 'bellatrix', 'capella', 'deneb']

from pyparser2 import parse_py2, parse_top_level
from pyprint import print_st
from simplify import simplify_assignments
from type_parser import get_module_type_defs, get_base_type_env

def parse_markup(data) -> Sequence[ast.stmt]:
    md = gfm.parse(data)
    res = []
    for ch in md.children:
        if isinstance(ch, FencedCode) and ch.lang == 'python':
            code = ch.children[0].children
            res.extend(ast.parse(code).body)
    return res

if __name__ == '__main__':
    for phase in phases:
        type_env = get_base_type_env()

        phase_ast_defs = []
        files = glob(f"{spec_dir}{phase}/*.md")
        for fn in files:
            print('---------')
            print(fn[len(spec_dir):])
            with open(fn, 'r') as f:
                phase_ast_defs.extend(parse_markup(f.read()))

            type_env.update(get_module_type_defs(phase_ast_defs))

        for ast_def in phase_ast_defs:
            tl = parse_top_level(ast_def)
            match tl:
                case ('func', fn, args, _, stmts):
                    from ssa import mk_cfg, resolve, place_phi_funcs, split_var_ranges
                    print('--')
                    print(fn, [a for a, _ in args])
                    cfg = mk_cfg(stmts)
                    #infer_var_decls()
                    stmts2 = simplify_assignments(stmts)
                    code = split_var_ranges(stmts2)
                    code2 = infer_var_decls(code, {a for a, _ in args}, set())
                    #print_st(code2, '  ')
                    print(gen_constraints(code2, type_env))


