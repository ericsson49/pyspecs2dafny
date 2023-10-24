from typing import Sequence, Callable, Set, Tuple, TypeVar, Optional, Mapping

from dataclasses import dataclass
from myast import Stmt, Expr, Block, Comprehension, Slice
from myast import (Assert, VarDecl, AnnAssign, AssignStmt, AugAssign, ExprStmt, IfStmt, WhileStmt, ForStmt, Return, Break,
                   Continue, Pass)
from myast import NameConst, Num, Str,Bytes, Name, Attribute, Subscript, FuncCall, GeneratorExpr, ListCompr, SetCompr, \
    BinOp, Compare, BoolOp, UnaryOp, \
    Attribute, Subscript, PyTuple, PyList, PySet, PyDict, IfExp, Lambda, Starred

from type_parser import Cls, ClsTemplate, FuncSignature

import unittest


class TypeCheckTest(unittest.TestCase):
    def test_backward_checking(self):
        self.assertEqual(calculate_result_type(NameConst(None), {}), Cls("NoneType"))
        self.assertEqual(calculate_result_type(NameConst(True), {}), Cls("bool"))
        self.assertEqual(calculate_result_type(NameConst(False), {}), Cls("bool"))
        self.assertEqual(calculate_result_type(Num(1), {}), Cls("int"))
        self.assertEqual(calculate_result_type(Str("string"), {}), Cls("str"))
        self.assertEqual(calculate_result_type(Bytes(b"111"), {}), Cls("bytes"))

        self.assertEqual(calculate_result_type(Name("a"), {"a": Cls("A")}), Cls("A"))
        #self.assertEqual(calculate_result_type(Name("a"), {}), ...)

        cls_A = ClsTemplate([], {
            "f": Cls("B"),
            "__get_elem__": FuncSignature([], [("idx", Cls("int"), None)], Cls("C")),
            "__get_slice__": FuncSignature([], [("idx", Cls("int"), None)], Cls("Sequence", (Cls("C"),)))
        })
        cls_A_gen = ClsTemplate(["T"], {"f": "T"})

        self.assertEqual(calculate_result_type(Attribute(Name("a"), "f"), {"a": Cls("A"), "A": cls_A}), Cls("B"))
        self.assertEqual(calculate_result_type(
            Attribute(Name("a"), "f"),
            {"a": Cls("A_gen", [Cls('int')]), "A_gen": cls_A_gen}), Cls("int"))

        self.assertEqual(calculate_result_type(Subscript(Name("a"), Num(1)), {"a": Cls("A"), "A": cls_A}), Cls("C"))
        self.assertEqual(calculate_result_type(
            Subscript(Name("a"), (None,Num(3),None)), {"a": Cls("A"), "A": cls_A}), Cls("Sequence", (Cls("C"),)))


class TypingCtx:
    def __init__(self):
        self.type_env = {}

    def fresh(self) -> str:
        ...


BOTTOM = Cls("Nothing")

def check_st(a: str|Cls, b: str|Cls):
    ...


def check_sub_typing(e: Expr, expected, type_env):
    if isinstance(e, (NameConst, Num, Str, Bytes)):
        check_st(expected, calculate_result_type(e, type_env))
    elif isinstance(e, Name):
        check_st(expected, calculate_result_type(e, type_env))
    elif isinstance(e, Attribute):
        vt = calculate_result_type(e.value, type_env)
        assert isinstance(vt, Cls)
        check_st(expected, resolve_attr(vt, e.attr, type_env))
    elif isinstance(e, BinOp):
        left_t = calculate_result_type(e.left, type_env)
        right_t = calculate_result_type(e.right, type_env)
        assert isinstance(left_t, Cls) and isinstance(right_t, Cls)
        func_sig = resolve_attr(left_t, '__op__', type_env)
        assert isinstance(func_sig, FuncSignature)
        check_st(expected, check_func_app(func_sig, ...))
    elif isinstance(e, UnaryOp):
        value_t = calculate_result_type(e.value, type_env)
        assert isinstance(value_t, Cls)
        func_sig = resolve_attr(value_t, '__op__', type_env)
        assert isinstance(func_sig, FuncSignature)
        return check_st(expected, check_func_app(func_sig, ...))
    elif isinstance(e, BoolOp):
        for value in e.values:
            check_sub_typing(value, Cls("bool"), type_env)
        check_st(expected, Cls("bool"))
    elif isinstance(e, Compare):
        ...
        check_st(expected, Cls("bool"))



def check_func_app(sig: FuncSignature, args: Sequence[Cls | str]) -> Cls | str:
    ...


def resolve_attr(c: Cls, attr: str, type_env: Mapping[str, Cls]) -> Cls | FuncSignature:
    cls_info = type_env[c.name]
    assert isinstance(cls_info, ClsTemplate)
    return cls_info.resolve_attribute(c.tparams, attr)

def calculate_result_type(e: Expr, type_env: Mapping[str, Cls]) -> Cls | str:
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
            return type_env[v]
        case Attribute(v, attr):
            vt = calculate_result_type(v, type_env)
            assert isinstance(vt, Cls)
            return resolve_attr(vt, attr, type_env)
        case Subscript(v, (l,u,s)):
            vt = calculate_result_type(v, type_env)
            assert isinstance(vt, Cls)
            func_sig: FuncSignature = resolve_attr(vt, '__get_slice__', type_env)
            return func_sig.ret
        case Subscript(v, idx):
            vt = calculate_result_type(v, type_env)
            assert isinstance(vt, Cls)
            func_sig: FuncSignature = resolve_attr(vt, '__get_elem__', type_env)
            return func_sig.ret
        case BinOp(left, op, right):
            left_ = calculate_result_type(left, type_env)
            right_ = calculate_result_type(right, type_env)
            if left_ is str or right_ is str:
                return BOTTOM
            else:
                if right_ > left_:
                    sig, args = resolve_attr(right_, f"__r{op}__", type_env), [right_, left_]
                else:
                    sig, args = resolve_attr(left_, f"__{op}__", type_env), [left_, right_]
                return check_func_app(sig, args)
        case BoolOp(op, args):
            # todo check args
            return Cls("bool")
        case FuncCall(Name(fname), args, kwargs):
            func = type_env[fname]
            assert isinstance(func, FuncSignature)
            return check_func_app(func, args)
        case PyList(elems):
            elems_ = [calculate_result_type(elem, type_env) for elem in elems]
            return check_func_app()

