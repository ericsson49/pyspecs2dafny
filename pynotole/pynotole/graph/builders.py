import ast

from toolz.itertoolz import concat, mapcat
from typing import Collection, Mapping

from .cfg import BBlock, BBlockFactory, InstructionRenamer, DummyBlock

type PyAST = ast.stmt | list[ast.stmt]


class AstStmtRenamer(InstructionRenamer[ast.stmt]):
    @classmethod
    def get_defs_uses(cls, instr: ast.stmt) -> tuple[Collection[str], Collection[str]]:
        def get_target_defs_uses(e: ast.expr):
            match e:
                case ast.Name(tgt):
                    return {tgt}, set()
                case ast.Attribute(e, _):
                    return set(), _get_uses(e)
                case ast.Subscript(e, idx) if not isinstance(idx, (ast.Tuple, ast.slice)):
                    return set(), _get_uses(e) | _get_uses(idx)
                case ast.Tuple(elts):
                    defs, uses = set(), set()
                    for e in elts:
                        d, u = get_target_defs_uses(e)
                        defs |= d
                        uses |= u
                    return defs, uses
                case _:
                    assert False
        match instr:
            case ast.Assert(test, None):
                return (set(), _get_uses(test))
            case ast.Assign([tgt], value):
                defs, uses = get_target_defs_uses(tgt)
                return defs, uses | _get_uses(value)
            case ast.Assign([ast.Name(target)], value):
                return ({target}, _get_uses(value))
            case ast.Assign([ast.Attribute(v, _)], value):
                return (set(), list(mapcat(_get_uses, [v, value])))
            case ast.Assign([ast.Subscript(tgt, idx)], value) if not isinstance(idx, (ast.Tuple, ast.slice)):
                return (set(), list(mapcat(_get_uses, [tgt, value])))
            case ast.AnnAssign(ast.Name(target), _, None):
                return (set(), set())
            case ast.AnnAssign(ast.Name(target), _, value):
                return ({target}, _get_uses(value))
            case ast.Expr(value):
                return (set(), _get_uses(value))
            case _:
                assert False, instr

    @classmethod
    def rename_defs(cls, instr: ast.stmt, var_map: Mapping[str, str]) -> ast.stmt:
        def rename_target(e: ast.expr):
            match e:
                case ast.Name(v):
                    return ast.Name(var_map.get(v, v))
                case ast.Attribute() | ast.Subscript():
                    return e
                case ast.Tuple(elts):
                    return ast.Tuple([rename_target(e) for e in elts])
                case _:
                    assert False
        match instr:
            case ast.Assert():
                return instr
            case ast.Assign([target], value):
                return ast.Assign([rename_target(target)], value)
            case ast.AnnAssign(ast.Name() as target, anno, value, is_simple):
                return ast.AnnAssign(rename_target(target), anno, value, is_simple)
            case ast.Expr():
                return instr
            case _:
                assert False, instr

    @classmethod
    def rename_uses(cls, instr: ast.stmt, var_map: Mapping[str, str]) -> ast.stmt:
        def rename_in_targets(e: ast.expr):
            match e:
                case ast.Name():
                    return e
                case ast.Attribute(v, attr):
                    return ast.Attribute(_rename_uses(v, var_map), attr)
                case ast.Subscript(v, idx) if not isinstance(idx, (ast.slice, ast.Tuple)):
                    return ast.Subscript(_rename_uses(v, var_map), _rename_uses(idx, var_map))
                case ast.Tuple(elts):
                    return ast.Tuple([_rename_uses(e, var_map) for e in elts])
                case _:
                    assert False
        match instr:
            case ast.Assert(test, None):
                return ast.Assert(_rename_uses(test, var_map), None)
            case ast.Expr(value):
                return ast.Expr(_rename_uses(value, var_map))
            case ast.Assign([target], value):
                return ast.Assign([rename_in_targets(target)], _rename_uses(value, var_map))
            case ast.AnnAssign(ast.Name() as target, anno, None, is_simple):
                return instr
            case ast.AnnAssign(ast.Name() as target, anno, value, is_simple):
                return ast.AnnAssign(target, anno, _rename_uses(value, var_map), is_simple)
            case _:
                assert False, instr

def _rename_uses(e: ast.expr, var_map: Mapping[str, str]) -> ast.expr:
    match e:
        case ast.Constant():
            return e
        case ast.Name(v) if v in var_map:
            return ast.Name(var_map[v])
        case ast.Name():
            return e
        case ast.Attribute(v, attr):
            return ast.Attribute(_rename_uses(v, var_map), attr)
        case ast.Subscript(v, idx) if not isinstance(idx, (ast.slice, ast.Tuple)):
            return ast.Subscript(_rename_uses(v, var_map), _rename_uses(idx, var_map))
        case ast.BoolOp(op, exprs):
            return ast.BoolOp(op, [_rename_uses(e, var_map) for e in exprs])
        case ast.BinOp(a, op, b):
            return ast.BinOp(_rename_uses(a, var_map), op, _rename_uses(b, var_map))
        case ast.Compare(a, [op], [b]):
            return ast.Compare(_rename_uses(a, var_map), [op], [_rename_uses(b, var_map)])
        case ast.UnaryOp(op, value):
            return ast.UnaryOp(op, _rename_uses(value, var_map))
        case ast.Call(func, args, kwds):
            args_ = [_rename_uses(a, var_map) for a in args]
            kwds_ = [ast.keyword(kwd.arg, _rename_uses(kwd.value, var_map)) for kwd in kwds]
            return ast.Call(_rename_uses(func, var_map), args_, kwds_)
        case ast.Tuple(elts):
            return ast.Tuple([_rename_uses(e, var_map) for e in elts])
        case ast.List(elts):
            return ast.List([_rename_uses(e, var_map) for e in elts])
        case ast.Set(elts):
            return ast.Set([_rename_uses(e, var_map) for e in elts])
        case ast.Dict(keys, values):
            return ast.Dict([_rename_uses(k, var_map) for k in keys], [_rename_uses(v, var_map) for v in values])
        case ast.IfExp(test, body, orelse):
            return ast.IfExp(_rename_uses(test, var_map), _rename_uses(body, var_map), _rename_uses(orelse, var_map))
        case _:
            assert False, e


class PyASTBBlockFactory(BBlockFactory[PyAST]):
    def block_from_stmt(self, stmt):
        return BBlock(AstStmtRenamer, [stmt])

    def block_from_expr(self, expr):
        return BBlock(AstStmtRenamer, [ast.Expr(expr)])

    def unapply_block(self, n: PyAST) -> None | list[PyAST]:
        match n:
            case [*stmts]:
                return stmts

    def unapply_while(self, n: PyAST) -> None | tuple[BBlock, list[PyAST]]:
        match n:
            case ast.While(test, body, []):
                test_block = self.block_from_expr(test)
                return test_block, body

    def unapply_for(self, n: PyAST) -> None | tuple[BBlock, BBlock, list[PyAST]]:
        match n:
            case ast.For(ast.Name(target), iter, body, []):
                pre_head = self.block_from_expr(iter)
                pre_body = self.block_from_stmt(ast.Assign([ast.Name(target)], ast.Constant(None)))
                return pre_head, pre_body, body
            case ast.For():
                assert False

    def unapply_if(self, n: PyAST) -> None | tuple[BBlock, list[PyAST], list[PyAST]]:
        match n:
            case ast.If(test, body, orelse):
                return self.block_from_expr(test), body, orelse

    def unapply_break(self, n: PyAST) -> None | tuple:
        match n:
            case ast.Break():
                return ()

    def unapply_continue(self, n: PyAST) -> None | tuple:
        match n:
            case ast.Continue():
                return ()

    def unapply_return(self, n: PyAST) -> None | tuple[()] | BBlock:
        match n:
            case ast.Return(None):
                return ()
            case ast.Return(value):
                return self.block_from_expr(value)

    def unapply_simple(self, n: PyAST) -> BBlock:
        match n:
            case ast.Assert() | ast.Assign() | ast.AnnAssign() | ast.Expr():
                return self.block_from_stmt(n)
            case ast.Pass():
                return DummyBlock()
            case _:
                assert False, n


def _get_uses(e: ast.expr) -> set[str]:
    match e:
        case ast.Constant():
            return set()
        case ast.Name(v):
            return {v}
        case ast.Attribute(value, _):
            return _get_uses(value)
        case ast.Subscript(value, idx) if not isinstance(idx, (ast.Tuple, ast.slice)):
            return set(mapcat(_get_uses, [value, idx]))
        case ast.Call(ast.Name(f), args, keywords):
            return set(mapcat(_get_uses, args + [kw.value for kw in keywords]))
        case ast.Call(ast.Attribute(value, _), args, keywords):
            return _get_uses(value) | set(mapcat(_get_uses, args + [kw.value for kw in keywords]))
        case ast.BoolOp(_, values):
            return set(mapcat(_get_uses, values))
        case ast.Compare(left, [op], [operand]):
            return set(mapcat(_get_uses, [left, operand]))
        case ast.BinOp(left, _, right):
            return set(mapcat(_get_uses, [left, right]))
        case ast.UnaryOp(_, operand):
            return _get_uses(operand)
        case ast.IfExp(test, body, orelse):
            return set(mapcat(_get_uses, [test, body, orelse]))
        case ast.List(elems):
            return set(mapcat(_get_uses, elems))
        case ast.Set(elems):
            return set(mapcat(_get_uses, elems))
        case ast.Dict(keys, values):
            return set(mapcat(_get_uses, concat([keys, values])))
        case ast.Tuple(elems):
            return set(mapcat(_get_uses, elems))
        case ast.Lambda(args, body):
            arg_names = {arg.arg for arg in args.args}
            return _get_uses(body) - arg_names
        case _:
            assert False, e


# import myast
#
# type MyAST = myast.AST
#
#
# class MyASTBBlockFactory(BBlockFactory[MyAST]):
#     @staticmethod
#     def _get_uses(e: myast.Expr) -> set[str]:
#         _get_uses = MyASTBBlockFactory._get_uses
#         match e:
#             case myast.NameConst() | myast.Num() | myast.Str() | myast.Bytes():
#                 return set()
#             case myast.Name(v):
#                 return {v}
#             case myast.FuncCall(myast.Name(f), args, keywords):
#                 return set(mapcat(_get_uses, args + [v for _,v in keywords]))
#             case myast.BinOp(left, _, right):
#                 return set(mapcat(_get_uses, [left, right]))
#             case _:
#                 assert False
#
#     def unapply_block(self, n: MyAST) -> None | list[MyAST]:
#         match n:
#             case myast.Block(stmts):
#                 return stmts
#
#     def unapply_while(self, n: MyAST) -> None | tuple[BBlock, list[MyAST]]:
#         match n:
#             case myast.WhileStmt(test, body):
#                 return BBlock(set(), self._get_uses(test)), body
#
#     def unapply_if(self, n: MyAST) -> None | tuple[BBlock, list[MyAST], list[MyAST]]:
#         match n:
#             case myast.IfStmt(test, body, or_else):
#                 return BBlock(set(), self._get_uses(test)), body, or_else
#
#     def unapply_simple(self, n: MyAST) -> BBlock:
#         match n:
#             case myast.AssignStmt(myast.Name(target), value):
#                 return BBlock({target}, self._get_uses(value))
#             case myast.ExprStmt(value):
#                 return BBlock(set(), self._get_uses(value))
#         assert False
#
#

