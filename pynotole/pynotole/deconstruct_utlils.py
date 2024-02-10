from typing import Any, Sequence, Tuple
from . import myast

def std_expr_destruct(e: myast.Expr) -> Tuple[Sequence[myast.Expr], Any]:
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
        case myast.PyTuple(elts):
            return elts, lambda vs: myast.PyTuple(vs)
        case _:
            assert False, e

def std_stmt_deconstruct(s: myast.Stmt) -> Tuple[Sequence[myast.Block], Any]:
    match s:
        case myast.IfStmt(test, body, orelse):
            return [body, orelse], lambda blocks: myast.IfStmt(test, blocks[0], blocks[1])
        case myast.ForStmt(tgt, iter, body):
            return [body], lambda blocks: myast.ForStmt(tgt, iter, blocks[0])
        case myast.WhileStmt(test, body):
            return [body], lambda blocks: myast.WhileStmt(test, blocks[0])
        case _:
            return [], lambda _: s
