import ast
from typing import Sequence, Callable, Set, Tuple, TypeVar, Optional, Mapping

from dataclasses import dataclass
from .myast import Stmt, Expr, Block, Comprehension, Slice
from .myast import (Assert, VarDecl, AnnAssign, AssignStmt, AugAssign, ExprStmt, IfStmt, WhileStmt, ForStmt, Return, Break,
                   Continue, Pass)
from .myast import NameConst, Num, Str,Bytes, Name, Attribute, Subscript, FuncCall, GeneratorExpr, ListCompr, SetCompr, \
    BinOp, Compare, BoolOp, UnaryOp, \
    Attribute, Subscript, PyTuple, PyList, PySet, PyDict, IfExp, Lambda, Starred

from .type_parser import Cls, ClsTemplate, FuncSignature, TypingEnv, parse_type_lib


BOTTOM = Cls("Nothing")


class ConstraintStore:
    def __init__(self):
        pass

    def add_sub_type(self, a: str|Cls, b: str|Cls):
        ...

    def query_upper(self, v: str) -> Cls|str:
        ...


def get_super_class(t_env: TypingEnv, cls: str) -> str|None:
    match cls:
        case "object":
            return None
        case "int" | "str":
            return "object"
        case "bool":
            return "int"
        case _:
            assert False

def get_ancestor_classes(t_env: TypingEnv, cls: str) -> Set[str]:
    super_cls = get_super_class(t_env, cls)
    if super_cls is None:
        return {cls}
    else:
        return {cls} | get_ancestor_classes(t_env, super_cls)

def is_subclass(t_env: TypingEnv, a: Cls, b: Cls) -> bool:
    return b.name in get_ancestor_classes(t_env, a.name)


def check_st(a: str|Cls, b: str|Cls, cs: ConstraintStore):
    if cs:
        cs.add_sub_type(a, b)


def check_sub_typing(e: Expr, expected, type_env, cs: ConstraintStore):
    match e:
        case NameConst() | Num() | Str() | Bytes():
            result = calculate_result_type(e, type_env)
        case Name():
            result = calculate_result_type(e, type_env)
        case Attribute() as e:
            vt = calculate_result_type(e.value, type_env)
            assert isinstance(vt, Cls)
            result = type_env.resolve_attr(vt, e.attr)
            assert isinstance(result, Cls)
        case BinOp() | UnaryOp() as e:
            result = calculate_result_type(e, type_env, cs)
        case BoolOp() as e:
            for value in e.values:
                check_sub_typing(value, Cls("bool"), type_env, cs)
            result = Cls("bool")
        case Compare() as e:
            ...
            result = Cls("bool")
        case _:
            assert False
    check_st(expected, result, cs)


def check_func_app(sig: FuncSignature, args: Sequence[Cls | str], cs: ConstraintStore=None) -> Cls | str:
    assert len(sig.args) == len(args)
    for (_, param_type, _), arg in zip(sig.args, args):
        check_st(arg, param_type, cs)
    return sig.ret


def map_operator_to_str(op: ast.operator) -> str:
    op_map = {ast.Add: "add", ast.Sub: "sub"}
    if op.__class__ in op_map:
        return op_map[op.__class__]
    else:
        assert False

def calculate_result_type(e: Expr, type_env: TypingEnv, cs: ConstraintStore=None) -> Cls | str:
    match e:
        case NameConst(None):
            return Cls("NoneType")
        case NameConst():
            return Cls("bool")
        case Num():
            return Cls("int")
        case Str():
            return Cls("str")
        case Bytes():
            return Cls("bytes")
        case Name(v):
            return type_env.resolve_var(v)
        case Attribute(v, attr):
            vt = calculate_result_type(v, type_env, cs)
            assert isinstance(vt, Cls)
            return type_env.resolve_attr(vt, attr)
        case Subscript(v, (l,u,s)):
            vt = calculate_result_type(v, type_env, cs)
            assert isinstance(vt, Cls)
            func_sig: FuncSignature = type_env.resolve_attr(vt, '__get_slice__')
            return func_sig.ret
        case Subscript(v, idx):
            vt = calculate_result_type(v, type_env, cs)
            assert isinstance(vt, Cls)
            func_sig: FuncSignature = type_env.resolve_attr(vt, '__get_elem__')
            return func_sig.ret
        case BinOp(left, op, right):
            left_ = calculate_result_type(left, type_env, cs)
            right_ = calculate_result_type(right, type_env, cs)
            if left_ is str or right_ is str:
                return BOTTOM
            else:
                op = map_operator_to_str(op)
                sig = None
                if is_subclass(type_env, right_, left_) and not is_subclass(type_env, left_, right_):
                    sig, args = type_env.try_resolve_attr(right_, f"__r{op}__"), [right_, left_]
                if sig is None:
                    sig, args = type_env.resolve_attr(left_, f"__{op}__"), [left_, right_]
                return check_func_app(sig, args, cs)
        case Compare(left, ops, comparators):
            return Cls("bool")
        case BoolOp(op, args):
            # todo check args
            return Cls("bool")
        case UnaryOp(ast.Not(), value):
            return Cls("bool")
        case FuncCall(Name(fname), args, kwargs):
            func = type_env[fname]
            assert isinstance(func, FuncSignature)
            return check_func_app(func, args, cs)
        case PyList(elems):
            elems_ = {calculate_result_type(elem, type_env, cs) for elem in elems}
            if len(elems_) == 1:
                return Cls("list", tuple(elems_))
            else:
                assert False
        case PySet(elems):
            elems_ = {calculate_result_type(elem, type_env, cs) for elem in elems}
            if len(elems_) == 1:
                return Cls("set", tuple(elems_))
            else:
                assert False
        case _:
            assert False, e

