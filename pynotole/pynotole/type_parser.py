from __future__ import  annotations

from dataclasses import dataclass
from typing import Sequence, Any, Tuple, Optional, Set, Mapping

import unittest

from .typing import Cls, ClsTemplate, FuncSignature, TypingEnv
from . import myast
import ast

def str_to_type(type_expr: str, type_vars: Set[str] = frozenset()):
    body = ast.parse(type_expr).body
    assert len(body) == 1
    assert isinstance(body[0], ast.Expr)
    return parse_type(body[0].value, type_vars)

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
class int(object):
    def __add__(self, other: int) -> int: ...

class bool(int):
    ...

class str(object):
    ...

class Sequence[T](object):
    ...

class list[T](Sequence[T]):
    ...

def len[T](coll: Collection[T]) -> int: ...
"""

def get_base_type_env() -> TypingEnv:
    res = {}
    for n, tl in parse_type_lib(pylib):
        match tl:
            case FuncSignature():
                if n not in res:
                    res[n] = []
                res[n].append(tl)
            case ClsTemplate():
                assert n not in res
                res[n] = tl
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
    return fname, FuncSignature(tvars, args, parse_type(func_def.returns, set(tvars)))


def parse_class_def(class_def: ast.ClassDef) -> (str, ClsTemplate):
    cls_name = class_def.name
    assert len(class_def.bases) <= 1
    type_params = [tp.name for tp in class_def.type_params]
    base_class = parse_type(class_def.bases[0], set(type_params)) if len(class_def.bases) == 1 else Cls("object")
    attrs = {}
    for field in class_def.body:
        match field:
            case ast.Pass():
                continue
            case ast.Expr(ast.Constant(c)) if c is Ellipsis:
                continue
            case ast.AnnAssign(ast.Name(fn, _), typ, init):
                attrs[fn] = parse_type(typ, set(type_params))
            case ast.FunctionDef() as meth:
                attr_name = meth.name
                args = meth.args.args
                assert len(args) >= 1
                assert args[0].arg == "self"
                def parse_arg(i, arg):
                    if i != 0:
                        assert arg.annotation is not None
                        anno = arg.annotation
                    elif arg.annotation is None:
                        anno = ast.Name(cls_name, ast.Load())
                        if len(type_params) == 1:
                            anno = ast.Subscript(
                                ast.Name(cls_name, ast.Load()),
                                ast.Name(type_params[0], ast.Load()),
                                ast.Load()
                            )
                        elif len(type_params) > 1:
                            tps = [ast.Name(tp, ast.Load()) for tp in type_params]
                            anno = ast.Subscript(
                                ast.Name(cls_name, ast.Load()),
                                ast.Tuple(tps, ast.Load()),
                                ast.Load())
                    else:
                        anno = arg.annotation
                    return (arg.arg, parse_type(anno, set(type_params)), None)

                args = [parse_arg(i, arg) for i, arg in enumerate(args)]
                ret = parse_type(meth.returns, set(type_params)) if meth.returns is not None else parse_type(ast.Constant(None))
                attrs[attr_name] = FuncSignature([], args, ret)
            case _:
                assert False
    return cls_name, ClsTemplate(type_params, base_class, attrs)



def parse_type_lib(tlib):
    res = []

    for mod in ast.parse(tlib).body:
        match mod:
            case ast.ClassDef() as c:
                res.append(parse_class_def(c))
            case ast.FunctionDef() as f:
                res.append(parse_tl_func_def(f))
            case _:
                assert False
    return res

