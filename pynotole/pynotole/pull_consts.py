from typing import Any, Tuple, Sequence

from . import myast
from .deconstruct_utlils import std_expr_destruct, std_stmt_deconstruct

_deconstruct = std_expr_destruct

class PullConsts:
    def __init__(self, pkgs=frozenset(), classes=frozenset()):
        self._var_num = 0
        self._pkgs = pkgs
        self._classes = classes

    def fresh(self) -> str:
        num = self._var_num
        self._var_num += 1
        return f"t{num}"

    def is_pkg(self, n) -> bool:
        return isinstance(n, myast.Name) and n.id in self._pkgs

    def is_class(self, n) -> bool:
        return isinstance(n, myast.Name) and n.id in self._classes

    def should_pull(self, e: myast.Expr) -> bool:
        match e:
            case myast.Attribute(v, attr):
                return self.is_pkg(v) or self.is_class(v)
            case myast.Subscript(v, slice):
                return self.is_class(v)

    def proc_subexprs(self, e: myast.Expr) -> Tuple[dict, myast.Expr]:
        sub_exprs, builder = _deconstruct(e)

        consts = {}
        sub_exprs_ = []
        for ex in sub_exprs:
            consts_, ex_ = self.proc_expr(ex)
            consts |= consts_
            sub_exprs_.append(ex_)

        res = builder(sub_exprs_)
        assert (len(consts) == 0) == (res == e)
        if res == e:
            return {}, e
        else:
            return consts, res

    def proc_expr(self, e: myast.Expr) -> Tuple[dict, myast.Expr]:
        consts, e2 = self.proc_subexprs(e)

        if self.should_pull(e2):
            fv = self.fresh()
            consts |= {fv: e2}
            res = myast.Name(fv)
        else:
            res = e2

        assert (len(consts) == 0) == (res == e)
        if res == e:
            return {}, e
        else:
            return consts, res

    def proc_substmts(self, s: myast.Stmt) -> Tuple[dict, myast.Stmt]:
        blocks, builder = std_stmt_deconstruct(s)

        consts = {}
        blocks_ = []
        for block in blocks:
            block_ = []
            for st in block:
                consts_, st_ = self.proc_stmt(st)
                consts |= consts_
                block_.append(st_)
            blocks_.append(block_)

        return consts, builder(blocks_)


    def proc_stmt(self, s: myast.Stmt) -> Tuple[dict, myast.Stmt]:
        consts, s = self.proc_substmts(s)
        match s:
            case myast.ExprStmt(value):
                consts_, value_ = self.proc_expr(value)
                return consts | consts_, myast.ExprStmt(value_)
            case myast.AssignStmt(myast.Name() as tgt, value, new_def):
                consts_, value_ = self.proc_expr(value)
                return consts | consts_, myast.AssignStmt(tgt, value_, new_def)
            case _:
                assert False


