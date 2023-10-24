from __future__ import  annotations

from dataclasses import dataclass
from typing import Sequence, Any, Tuple, Optional, Set, Mapping

import unittest

import myast
import ast


@dataclass(eq=True,frozen=True)
class ClsTemplate:
    type_vars: Sequence[str]
    attrs: Mapping[str, FuncSignature|Cls|str]

    def resolve_attribute(self, type_params: Sequence[Cls], attr):
        assert len(self.type_vars) == len(type_params)
        tv_map = dict(zip(self.type_vars, type_params))

        def replace_type_vars(c: FuncSignature|Cls|str):
            match c:
                case str(tv):
                    return tv_map[tv]
                case Cls(cname, tparams):
                    tparams_ = tuple(map(replace_type_vars, tparams))
                    return Cls(cname, tparams_)
                case FuncSignature([], args, ret):
                    args_ = [(a, replace_type_vars(t), d) for a,t,d in args]
                    ret_ = replace_type_vars(ret)
                    return FuncSignature([], args_, ret_)
                case _:
                    assert False
        return replace_type_vars(self.attrs[attr])

@dataclass(eq=True,frozen=True)
class Cls:
    name: str
    tparams: Sequence[Cls|str] = ()


@dataclass(eq=True,frozen=True)
class FuncSignature:
    type_vars: Sequence[str]
    args: Sequence[Tuple[str, Cls | str, Optional[str]]]
    ret: Cls | str

    def matches_call(self, pos_arg_count, kwd_args: Sequence[str]) -> bool:
        param_names = [n for n, _, _ in self.args]
        args_with_defaults = {n for n, _, dv in self.args if dv is not None}
        if len(self.args) < pos_arg_count:
            return False
        pos_matched_args = set(param_names[:pos_arg_count])
        rest_args = set(param_names).difference(pos_matched_args)
        if set(kwd_args).difference(rest_args):
            return False
        rest_args = rest_args.difference(kwd_args)
        if rest_args.difference(args_with_defaults):
             return False
        return True


def _module_to_expr(node: ast.Module) -> ast.expr:
    assert len(node.body) == 1
    assert isinstance(node.body[0], ast.Expr)
    return node.body[0].value


class TypeParserTest(unittest.TestCase):
    def test(self):
        def helper(s: str):
            return parse_type(_module_to_expr(ast.parse(s)))
        self.assertEqual(helper('None'), Cls("NoneType"))
        self.assertEqual(helper('A'), Cls("A"))
        self.assertEqual(helper('a.A'), Cls("a.A"))
        self.assertEqual(helper('a.b.A'), Cls("a.b.A"))
        self.assertEqual(helper('A[B]'), Cls("A", (Cls("B"),)))
        self.assertEqual(helper('A[B,C]'), Cls("A", (Cls("B"),Cls("C"))))
        self.assertEqual(helper('List[A,1*2]'), Cls("List", (Cls("A"),)))
        self.assertEqual(helper('Vector[A,1*2]'), Cls("Vector", (Cls("A"),)))
        self.assertEqual(helper('Bytelist[1*2]'), Cls("Bytelist", ()))
        self.assertEqual(helper('Bytevector[1*2]'), Cls("Bytevector", ()))
        self.assertEqual(helper('Bitlist[1*2]'), Cls("Bitlist", ()))
        self.assertEqual(helper('Bitvector[1*2]'), Cls("Bitvector", ()))

        self.assertEqual(parse_type(_module_to_expr(ast.parse("T")), {"T"}), "T")
        self.assertEqual(parse_type(_module_to_expr(ast.parse("Cls[T]")), {"T"}), Cls("Cls", ("T",)))

    def test_parse_typelib_func(self):
        fn, sig = parse_type_lib("def len[T](coll: Collection[T]) -> int: ...")[0]
        self.assertEqual(fn, "len")
        self.assertEqual(sig, FuncSignature(["T"], [("coll", Cls("Collection", ("T",)), None)], Cls("int")))

        self.assertEqual(sig.matches_call(1, set()), True)
        self.assertEqual(sig.matches_call(0, {"coll"}), True)
        self.assertEqual(sig.matches_call(0, set()), False)
        self.assertEqual(sig.matches_call(2, set()), False)
        self.assertEqual(sig.matches_call(1, {"wrong"}), False)
        self.assertEqual(sig.matches_call(0, {"wrong"}), False)
        self.assertEqual(sig.matches_call(1, {"coll"}), False)
        self.assertEqual(sig.matches_call(1, {"coll", "wrong"}), False)
        self.assertEqual(sig.matches_call(0, {"coll", "wrong"}), False)

def parse_type(node: ast.expr, type_vars: Set[str] = frozenset()):
    def resolve_name(v: ast.expr) -> str:
        match v:
            case ast.Name(id, _):
                return id
            case ast.Attribute(value, attr, _):
                return resolve_name(value) + "." + attr
            case _:
                assert False
    match node:
        case ast.Constant(c) if c is None:
            return Cls("NoneType")
        case ast.Name(t) if t in type_vars:
            return t
        case ast.Name() as t:
            return Cls(resolve_name(t))
        case ast.Attribute() as a:
            return Cls(resolve_name(a))
        case ast.Subscript(cls, _tvs, _):
            cls_name = resolve_name(cls)
            match _tvs:
                case ast.Tuple(elts):
                    tvs = elts
                case _:
                    tvs = [_tvs]
            if cls_name in ('List', 'Vector', 'Bytelist', 'Bytevector', 'Bitlist', 'Bitvector'):
                tvs = tvs[:-1]
            return Cls(resolve_name(cls), tuple(parse_type(tv, type_vars) for tv in tvs))
        case _:
            assert False, f"{node}"

pylib = """
def len[T](coll: Collection[T]) -> int: ...
"""

class TypingEnv:
    def __init__(self, names=None):
        self.names = names or {}

    def resolve_func(self, name) -> Sequence[FuncSignature]:
        return self.names[name]

    def update(self, names):
        self.names |= names

def get_base_type_env() -> TypingEnv:
    res = {}
    for n, tl in parse_type_lib(pylib):
        match tl:
            case FuncSignature():
                if n not in res:
                    res[n] = []
                res[n].append(tl)
            case _:
                assert False
    return TypingEnv(res)

def get_module_type_defs(tl_defs: Sequence[ast.stmt]):
    res = {}
    for tl in tl_defs:
        match tl:
            case ast.FunctionDef() as f:
                name, sig = parse_tl_func_def(f)
                res[name] = [sig]
    return res

def parse_tl_func_def(func_def: ast.FunctionDef) -> (str, FuncSignature):
    fname = func_def.name
    tvars = [tp.name for tp in func_def.type_params]
    args = [(arg.arg, parse_type(arg.annotation, set(tvars)), None) for arg in func_def.args.args]
    return fname, FuncSignature(tvars, args, parse_type(func_def.returns))


def parse_type_lib(tlib):
    res = []

    def parse_class_def(class_def: ast.ClassDef):
        ...

    for mod in ast.parse(tlib).body:
        match mod:
            case ast.ClassDef() as c:
                res.append(parse_class_def(c))
            case ast.FunctionDef() as f:
                res.append(parse_tl_func_def(f))
            case _:
                assert False
    return res

