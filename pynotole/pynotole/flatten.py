from typing import Any, Sequence, Tuple
from toolz import mapcat
from . import myast


def _destruct(e: myast.Expr) -> Tuple[Sequence[myast.Expr], Any]:
    match e:
        case myast.NameConst() | myast.Num() | myast.Name()  as e:
            return [], lambda _: e
        case myast.Lambda() as e:
            return [], lambda _: e
        case myast.Attribute(v, attr):
            return [v], lambda vs: myast.Attribute(vs[0], attr)
        case myast.Subscript(v, myast.Expr() as slice):
            return [v, slice], lambda vs: myast.Subscript(vs[0], vs[1])
        case myast.BinOp(left, op, right):
            return [left, right], lambda vs: myast.BinOp(vs[0], op, vs[1])
        case myast.BoolOp(op, values):
            return values, lambda vs: myast.BoolOp(op, vs)
        case myast.Compare(left, [op], [right]):
            return [left, right], lambda vs: myast.Compare(vs[0], [op], [vs[1]])
        case myast.UnaryOp(op, operand):
            return [operand], lambda vs: myast.UnaryOp(op, vs[0])
        case myast.FuncCall(func, args, kwds):
            arg_names = [k for k,_ in kwds]
            return ([func] + args + [v for _, v in kwds],
                    lambda args_: myast.FuncCall(args_[0], args_[1:len(args)+1], list(zip(arg_names, args_[len(args)+1:]))))
        case _:
            assert False, e


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

    def destruct(self, e) -> Tuple[Sequence[myast.Expr], Any]:
        return _destruct(e)

    def proc_assgns(self, assgns: Sequence[Tuple[str, myast.Expr]]) -> Sequence[Tuple[str, myast.Expr]]:
        res = []
        for v,e in assgns:
            assgns_, e_ = self.proc_expr(e)
            res.extend(self.proc_assgns(assgns_))
            res.append((v, e_))
        return res

    def proc_expr(self, expr: myast.Expr) -> Tuple[Sequence[Tuple[str, myast.Expr]], myast.Expr]:
        exprs, builder = self.destruct(expr)
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

    def proc_stmt(self, s: myast.Stmt) -> Sequence[myast.Stmt]:
        def assgns_to_stmts(assgns: Sequence[Tuple[str, myast.Expr]]) -> list[myast.AssignStmt]:
            return [myast.AssignStmt(myast.Name(v), e, True) for v, e in assgns]
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
                body_ = myast.Block(list(mapcat(self.proc_stmt, body.stmts)))
                orelse_ = myast.Block(list(mapcat(self.proc_stmt, orelse.stmts)))
                return assgns_to_stmts(t_assgns) + [myast.IfStmt(t_, body_, orelse_)]
            case myast.ForStmt(tgt, iter, body):
                it_assgns, it_ = self.proc_expr(iter)
                body_ = myast.Block(list(mapcat(self.proc_stmt, body.stmts)))
                return assgns_to_stmts(it_assgns) + [myast.ForStmt(tgt, it_, body_)]
            case myast.Return(None) as ret:
                return ret
            case myast.Return(value):
                assgns, value_ = self.proc_expr(value)
                return assgns_to_stmts(assgns) + [myast.Return(value_)]
            case _:
                assert False, s
