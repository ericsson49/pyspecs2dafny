from abc import ABC, abstractmethod
from typing import Callable, Sequence, Tuple, TypeVar

from myast import Expr, Stmt, Block, Comprehension
from myast import ExprStmt, VarDecl, AssignStmt, IfStmt, ForStmt, WhileStmt
from myast import (Num, Name, Subscript, Attribute, FuncCall, GeneratorExpr, ListCompr, SetCompr, DictCompr,
                   PyTuple)


Strategy = Callable[[Stmt], Stmt|None]


_T = TypeVar('_T')
_Strategy = Callable[[_T], _T|None]

def unzip(coll):
    res1, res2 = [], []
    for a, b in coll:
        res1.append(a)
        res2.append(b)
    return res1, res2

def rewrite(rule: _Strategy, t: _T) -> _T:
    return rule(t) or t


def apply_to_seq(rule: _Strategy, ts: Sequence[_T]) -> Sequence[_T] | None:
    results = list(map(rule, ts))
    if any(results):
        return [res or orig for orig, res in zip(ts, results)]


class Rewriting(ABC):
    @classmethod
    @abstractmethod
    def some(cls, rule: _Strategy) -> _Strategy: ...

    @classmethod
    def repeat(cls, rule: _Strategy) -> _Strategy:
        def f(t: _T) -> _T|None:
            curr, prev = rule(t), None
            while curr is not None:
                curr, prev = rule(curr), curr
            return prev
        return f

    @classmethod
    def plus(cls, r1: _Strategy, r2: _Strategy) -> _Strategy:
        def f(t: _T) -> _T|None:
            return r1(t) or r2(t)
        return f

    @classmethod
    def reduce(cls, rule: _Strategy) -> _Strategy:
        def x(t: _T) -> _T|None:
            return cls.plus(cls.some(x), rule)(t)
        return cls.repeat(x)


class StmtRewriting(Rewriting):
    @classmethod
    def some(cls, rule: _Strategy) -> _Strategy:
        def f(s: Stmt|Block) -> Stmt|Block|None:
            match s:
                case Block(stmts):
                    new_stmts = apply_to_seq(rule, stmts)
                    if new_stmts:
                        return Block(new_stmts)
                case IfStmt(test, body, or_else):
                    new_body = f(body)
                    new_else = f(or_else)
                    if new_body or new_else:
                        return IfStmt(test, new_body or body, new_else or or_else)
                case WhileStmt(test, body):
                    new_body = f(body)
                    if new_body:
                        return WhileStmt(test, new_body)
        return f

class ExprRewriting(Rewriting):
    @classmethod
    def some(cls, rule: _Strategy) -> _Strategy:
        def f(e: Expr) -> Expr|None:
            match e:
                case FuncCall(f, args, kwds):
                    new_f = rule(f)
                    new_args = apply_to_seq(rule, args)
                    kw_names, kw_args = unzip(kwds)
                    new_kw_args = apply_to_seq(rule, kw_args)
                    new_kwds = list(zip(kw_names, new_kw_args)) if new_kw_args else None
                    if new_f or new_args or new_kwds:
                        return FuncCall(new_f or f, new_args or args, new_kwds or kwds)
        return f


def some(rule: Strategy, s: Stmt|Block) -> Stmt|Block|None:
    match s:
        case IfStmt(t, body, orElse):
            new_body = some(rule, body)
            new_or_else = some(rule, orElse)
            if new_body is not None or new_or_else is not None:
                return IfStmt(t, new_body or body, new_or_else or orElse)
            else:
                return None
        case WhileStmt(t, body):
            new_body = some(rule, body)
            if new_body is not None:
                return WhileStmt(t, new_body)
            else:
                return None
        case Block(stmts):
            new_stmts = apply_to_seq(rule, stmts)
            if new_stmts:
                return Block(new_stmts)
            else:
                return None
        case _:
            return None

def repeat(rule: Strategy, s: Stmt) -> Stmt|None:
    curr, prev = rule(s), None
    while curr is not None:
        curr, prev = rule(curr), curr
    return prev

def reduce(rule: Strategy, s: Stmt) -> Stmt|None:
    def x(ss: Stmt) -> Stmt|None:
        return some(x, ss) or rule(ss)
    return repeat(x, s)



class FreshVars:
    def __init__(self):
        self.tmps = set()

    def fresh(self, prefix: str = 'tmp') -> str:
        fv = prefix + str(len(self.tmps))
        self.tmps.add(fv)
        return fv


def gen_typ_vars(st: Stmt|Block) -> Stmt|Block:
    fv = FreshVars()
    def rew_rule(s: Stmt|Block) -> Stmt|None:
        match s:
            case VarDecl(vn, None, vl):
                return VarDecl(vn, fv.fresh("?"), vl)
    return reduce(rew_rule, st) or st


def simplify_assignments(s: Stmt|Block) -> Stmt|Block:
    fvs = FreshVars()
    def rule(s: Stmt|Block) -> Stmt|Block|None:
        match s:
            case AssignStmt(PyTuple(tgts), value):
                tv = fvs.fresh()
                assgns = [AssignStmt(Name(tv), value)]
                for i, tgt in enumerate(tgts):
                    assgns.append(AssignStmt(tgt, Subscript(Name(tv), Num(i))))
                return Block(assgns)
    return reduce(rule, s) or s


def simpl(s: Stmt|Block) -> Stmt|Block:
    fvs = FreshVars()

    def has_non_var_args(fc: FuncCall):
        return any(not isinstance(a, Name) for a in fc.args)
    def proc_fc(fc: FuncCall):
        _args = { a: Name(fvs.fresh()) for a in fc.args if not isinstance(a, Name) }
        stmts = [AssignStmt(v, ex) for ex, v in _args.items()]
        return stmts, FuncCall(fc.func, [_args.get(a) or a for a in fc.args], fc.kwargs)

    def rew_rule(s: Stmt|Block) -> Stmt|Block|None:
        match s:
            case ExprStmt(FuncCall() as fc) if has_non_var_args(fc):
                stmts, new_fc = proc_fc(fc)
                return Block(stmts + [ExprStmt(new_fc)])
            case AssignStmt(t, FuncCall() as fc) if has_non_var_args(fc):
                stmts, new_fc = proc_fc(fc)
                return Block(stmts + [AssignStmt(t, new_fc)])
            case VarDecl(v, t, FuncCall() as fc) if has_non_var_args(fc):
                stmts, new_fc = proc_fc(fc)
                return Block(stmts + [VarDecl(v, t, new_fc)])

    return reduce(rew_rule, s) or s

def compr_inline(s: Stmt|Block) -> Stmt|Block:
    fvs = FreshVars()

    def compr_to_loop(e: Expr, compr: Comprehension, acc_fac: Tuple[str,str] = ('list', 'list_append')):
        acc_fac_mk, acc_fac_update = acc_fac
        acc_var = Name(fvs.fresh())
        acc_init = AssignStmt(acc_var, FuncCall(Name(acc_fac_mk), [], []))
        body = ExprStmt(FuncCall(Name(acc_fac_update), [acc_var, e], []))
        if compr.comp_if:
            body = IfStmt(compr.comp_if, Block([body]), Block([]))
        loop = ForStmt(compr.iter, compr.coll, Block([
            body
        ]))
        return acc_var, Block([acc_init, loop])

    def rew_rule(s: Stmt|Block) -> Stmt|Block|None:
        match s:
            case ExprStmt(ListCompr(value, compr)):
                return compr_to_loop(value, compr)[1]
            case ExprStmt(SetCompr(value, compr)):
                return compr_to_loop(value, compr, ('set', 'set_add'))[1]
            case AssignStmt(tgt, ListCompr(value, compr)):
                acc, stmts = compr_to_loop(value, compr)
                return Block(stmts.stmts + [AssignStmt(tgt, acc)])

    return reduce(rew_rule, s) or s

if __name__ == "__main__":
    import ast
    from pyparser2 import parse_stmts
    from pyprint import print_st

    stmts = parse_stmts(ast.parse("""
a.f, (b, c) = 1, (2,3)
    """).body)
    code = simplify_assignments(stmts)
    print_st(code)