import ast

from toolz.itertoolz import concat, mapcat
from typing import Collection, Mapping

from .cfg import BBlock, BBlockFactory, InstructionRenamer


type PyAST = ast.stmt | list[ast.stmt]


class AstStmtRenamer(InstructionRenamer[ast.stmt]):
    @classmethod
    def get_defs_uses(cls, instr: ast.stmt) -> tuple[Collection[str], Collection[str]]:
        match instr:
            case ast.Assert(test, None):
                return (set(), _get_uses(test))
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
                assert False

    @classmethod
    def rename_defs(cls, instr: ast.stmt, var_map: Mapping[str, str]) -> ast.stmt:
        match instr:
            case ast.Assign([ast.Name(v) as target], value):
                return ast.Assign([ast.Name(var_map.get(v, v))], value)
            case ast.Expr():
                return instr
            case _:
                assert False

    @classmethod
    def rename_uses(cls, instr: ast.stmt, var_map: Mapping[str, str]) -> ast.stmt:
        match instr:
            case ast.Expr(value):
                return ast.Expr(_rename_uses(value, var_map))
            case ast.Assign([ast.Name() as target], value):
                return ast.Assign([target], _rename_uses(value, var_map))
            case _:
                assert False

def _rename_uses(e: ast.expr, var_map: Mapping[str, str]) -> ast.expr:
    match e:
        case ast.Constant():
            return e
        case ast.Name(v) if v in var_map:
            return ast.Name(var_map[v])
        case ast.Name():
            return e
        case ast.Call(func, args, []):
            return ast.Call(_rename_uses(func, var_map), [_rename_uses(a, var_map) for a in args], [])
        case _:
            assert False


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
        case ast.Dict(keys, values):
            return set(mapcat(_get_uses, concat([keys, values])))
        case ast.Tuple(elems):
            return set(mapcat(_get_uses, elems))
        case ast.Lambda(args, body):
            arg_names = {arg.arg for arg in args.args}
            return _get_uses(body) - arg_names
        case _:
            assert False


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

