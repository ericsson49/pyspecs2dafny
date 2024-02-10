from typing import Sequence, Tuple
from toolz import mapcat

from .deconstruct_utlils import std_expr_destruct, std_stmt_deconstruct
from . import myast

_deconstruct = std_expr_destruct

class FlattenExprs:
    def __init__(self):
        self._var_num = 0

    def should_pull_expr(self, e: myast.Expr) -> bool:
        match e:
            case myast.FuncCall():
                return True

    def fresh(self) -> str:
        num = self._var_num
        self._var_num += 1
        return f"t{num}"

    def proc_assgns(self, assgns: Sequence[Tuple[str, myast.Expr]]) -> Sequence[Tuple[str, myast.Expr]]:
        res = []
        for v,e in assgns:
            assgns_, e_ = self.proc_expr(e)
            res.extend(self.proc_assgns(assgns_))
            res.append((v, e_))
        return res

    def proc_expr(self, expr: myast.Expr) -> Tuple[Sequence[Tuple[str, myast.Expr]], myast.Expr]:
        exprs, builder = _deconstruct(expr)
        assgns = []
        exprs_ = []
        for e in exprs:
            assgns_, e_ = self.proc_expr(e)
            if len(assgns_) != 0:
                assgns.extend(assgns_)
            else:
                e_ = e
            if self.should_pull_expr(e_):
                var = self.fresh()
                assgns.append((var, e_))
                e_ = myast.Name(var)
            exprs_.append(e_)
        res_expr = builder(exprs_)
        assert (len(assgns) == 0) == (expr == res_expr)
        if expr == res_expr:
            return [], expr
        else:
            return self.proc_assgns(assgns), builder(exprs_)

    def proc_substmts(self, s: myast.Stmt) -> myast.Stmt:
        blocks, builder = std_stmt_deconstruct(s)
        def proc_block(b: myast.Block) -> myast.Block:
            return myast.Block(list(mapcat(self.proc_stmt, b.stmts)))

        return builder([proc_block(b) for b in blocks])

    def proc_stmt(self, s: myast.Stmt) -> Sequence[myast.Stmt]:
        def assgns_to_stmts(assgns: Sequence[Tuple[str, myast.Expr]]) -> list[myast.AssignStmt]:
            return [myast.AssignStmt(myast.Name(v), e, True) for v, e in assgns]
        s = self.proc_substmts(s)
        match s:
            case myast.ExprStmt(value):
                assgns, value_ = self.proc_expr(value)
                return assgns_to_stmts(assgns) + [myast.ExprStmt(value_)]
            case myast.AssignStmt(myast.Name() as tgt, value, new_def):
                assgns, value_ = self.proc_expr(value)
                return assgns_to_stmts(assgns) + [myast.AssignStmt(tgt, value_, new_def)]
            case myast.AnnAssign(_, _, None) as ann_assgn:
                return [ann_assgn]
            case myast.AnnAssign(tgt, anno, value):
                assgns, value_ = self.proc_expr(value)
                return assgns_to_stmts(assgns) + [myast.AnnAssign(tgt, anno, value_)]
            case myast.IfStmt(test, body, orelse):
                t_assgns, t_ = self.proc_expr(test)
                return assgns_to_stmts(t_assgns) + [myast.IfStmt(t_, body, orelse)]
            case myast.ForStmt(tgt, iter, body):
                it_assgns, it_ = self.proc_expr(iter)
                return assgns_to_stmts(it_assgns) + [myast.ForStmt(tgt, it_, body)]
            case myast.Return(None) as ret:
                return ret
            case myast.Return(value):
                assgns, value_ = self.proc_expr(value)
                return assgns_to_stmts(assgns) + [myast.Return(value_)]
            case _:
                assert False, s
