from __future__ import  annotations

from abc import abstractmethod
from dataclasses import dataclass
from enum import Enum
from typing import Sequence, Any, Tuple, Optional, Set, Mapping, Callable

from .type_defs import Cls, ClsTemplate, FuncSignature, PkgDefinition
from .type_registry import TypingEnv, NameRegistry
import ast


class NameCtx:
    def __init__(self, names):
        self.names = names if names is not None else {}

    def resolve(self, name: str):
        return self.names[name]

    @abstractmethod
    def updated(self, names) -> NameCtx: ...

class ClassParamKind(Enum):
    TYPE_PARAM = 0
    EXPR_PARAM = 0


@dataclass
class ClassInfo:
    name: str
    class_param_kinds: Sequence[ClassParamKind] = ()

@dataclass
class FuncInfo:
    name: str

@dataclass
class PkgInfo:
    name: str

class TypeParseCtx(NameCtx):
    def __init__(self, names=None):
        super().__init__(names)

    def updated(self, names) -> NameCtx:
        return TypeParseCtx(self.names | names)

    def resolve_class(self, name) -> ClassInfo:
        res = self.resolve(name)
        assert isinstance(res, ClassInfo)
        return res

    def resolve_func(self, name) -> FuncInfo:
        res = self.resolve(name)
        assert isinstance(res, FuncInfo)
        return res

    def resolve_pkg(self, name) -> PkgInfo:
        res = self.resolve(name)
        assert isinstance(res, PkgInfo)
        return res


def str_to_type(type_expr: str, type_vars: Set[str] = frozenset()):
    body = ast.parse(type_expr).body
    assert len(body) == 1
    assert isinstance(body[0], ast.Expr)
    return parse_type(body[0].value, type_vars)


def parse_type2(node: ast.expr, parse_ctx: TypeParseCtx, type_vars: Sequence[str]):
    def resolve_ref(v: ast.expr, kind=None) -> ClassInfo | FuncInfo | PkgInfo:
        match v:
            case ast.Name(id, _):
                res = parse_ctx.resolve(id)
            case ast.Attribute(value, attr, _):
                ref = resolve_ref(value)
                res = parse_ctx.resolve(f"{ref.name}.{attr}")
            case _:
                assert False
        if kind is not None:
            assert isinstance(res, kind)
        return res
    match node:
        case ast.Constant(c) if c is None:
            return Cls("NoneType")
        case ast.Name(t) if t in type_vars:
            return t
        case ast.Name() as t:
            return Cls(resolve_ref(t, ClassInfo).name)
        case ast.Attribute() as a:
            return Cls(resolve_ref(a, ClassInfo).name)
        case ast.Subscript(cls, _tvs, _):
            cls_info = resolve_ref(cls, ClassInfo)
            match _tvs:
                case ast.Tuple(elts):
                    tvs = elts
                case _:
                    tvs = [_tvs]
            if cls_info.name in ('py$List', 'py$Vector', 'py$ByteList', 'py$ByteVector', 'py$Bitlist', 'py$Bitvector'):
                tvs = tvs[:-1]
            return Cls(cls_info.name, tuple(parse_type2(tv, parse_ctx, type_vars) for tv in tvs))
        case _:
            assert False, f"{node}"


def parse_type(node: ast.expr, type_vars: Sequence[str] = ()):
    return parse_type2(node, mk_std_parser_ctx(), type_vars)


pylib_code = """
class object:
    ...

class int(object):
    def __add__(self, other: int) -> int: ...
    def __radd__(self, other: int) -> int: ...
    def __sub__(self, other: int) -> int: ...
    def __rsub__(self, other: int) -> int: ...
    def __mul__(self, other: int) -> int: ...
    def __rmul__(self, other: int) -> int: ...
    def __floordiv__(self, other: int) -> int: ...
    def __rfloordiv__(self, other: int) -> int: ...

class bool(int):
    ...

class str(object):
    ...

class bytes(object):
    ...

class Optional:
    ...

class Tuple:
    ...

class Iterable[T](object):
    ...

class Collection[T](Iterable[T]):
    ...

class Sequence[T](Iterable[T]):
    def __get_elem__(self, idx: int) -> T: ...

class list[T](Sequence[T]):
    ...

def len[T](coll: Collection[T]) -> int: ...

class Set[T](Collection[T]):
    ...

class PySet[T](Collection[T]):
    ...

class List[T](Sequence[T]):
    ...

class PyList[T](Sequence[T]):
    ...

class Vector[T](Sequence[T]):
    ...

class Dict[K,V](Collection[Tuple[K,V]]):
    ...

class uint64(int):
    def __init__(self, value: int=0): ...
    def __add__(self, other: int) -> uint64: ...
    def __radd__(self, other: int) -> uint64: ...
    def __sub__(self, other: int) -> uint64: ...
    def __rsub__(self, other: int) -> uint64: ...
    def __mul__(self, other: int) -> uint64: ...
    def __rmul__(self, other: int) -> uint64: ...
    def __floordiv__(self, other: int) -> uint64: ...
    def __rfloordiv__(self, other: int) -> uint64: ...

class boolean(int):
    ...

class bit(int):
    ...

class Container:
    ...

class Bytes4(bytes):
    def __init__(self, value: int = 0): ...

class Bytes32(bytes):
    def __init__(self, value: int = 0): ...

class Bytes48(bytes):
    def __init__(self, value: int = 0): ...

class Bytes96(bytes):
    def __init__(self, value: int = 0): ...

class Bitlist(Sequence[bit]):
    ...

class Bitvector(Sequence[bit]):
    ...

class SSZObject:
    ...
"""

def mk_std_parser_ctx() -> TypeParseCtx:
    resolver = TypeParseCtx()
    definitions = parse_type_lib(resolver, 'py', pylib_code)
    def to_class_info(cls_template: ClsTemplate) -> ClassInfo:
        return ClassInfo(cls_template.name, (ClassParamKind.TYPE_PARAM,)*len((cls_template.type_vars)))

    class_infos = {td.name: to_class_info(td) for td in definitions if isinstance(td, ClsTemplate)}
    ctx = TypeParseCtx(class_infos)

    def import_pkg(ctx: TypeParseCtx, pkg):
        names = {}
        for k, v in ctx.names.items():
            if k.startswith(pkg + '$'):
                names[k[len(pkg) + 1:]] = v
        return ctx.updated(names)

    ctx = import_pkg(ctx, 'py')
    return ctx


def mk_registry() -> NameRegistry:
    registry = NameRegistry()
    registry.register_pkg('py', parse_type_lib2(mk_std_parser_ctx(), 'py', pylib_code))
    return registry


def get_base_type_env() -> TypingEnv:
    env = mk_registry().mk_typing_env()
    env.from_pkg_import('py')
    return env


def parse_tl_func_def(parse_ctx: TypeParseCtx, func_def: ast.FunctionDef) -> FuncSignature:
    fname = func_def.name
    tvars = [tp.name for tp in func_def.type_params]
    args = [(arg.arg, parse_type2(arg.annotation, parse_ctx, tvars), None) for arg in func_def.args.args]
    return FuncSignature(parse_ctx.resolve_func(fname).name, tvars, args, parse_type2(func_def.returns, parse_ctx, tvars))


def parse_class_def(parse_ctx: TypeParseCtx, class_def: ast.ClassDef) -> ClsTemplate:
    cls_name = class_def.name
    assert len(class_def.bases) <= 1
    type_params = [tp.name for tp in class_def.type_params]
    base_class = parse_type2(class_def.bases[0], parse_ctx, type_params) if len(class_def.bases) == 1 else Cls("object")
    attrs = {}
    for field in class_def.body:
        match field:
            case ast.Pass():
                continue
            case ast.Expr(ast.Constant(c)) if c is Ellipsis:
                continue
            case ast.AnnAssign(ast.Name(fn, _), typ, init):
                attrs[fn] = parse_type2(typ, parse_ctx, type_params)
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
                    return (arg.arg, parse_type2(anno, parse_ctx, type_params), None)

                args = [parse_arg(i, arg) for i, arg in enumerate(args)]
                ret = parse_type2(meth.returns or ast.Constant(None), parse_ctx, type_params)
                attrs[attr_name] = FuncSignature(None, [], args, ret)
            case _:
                assert False
    return ClsTemplate(parse_ctx.resolve_class(cls_name).name, type_params, base_class, attrs)

def parse_type_lib0(name_ctx: NameCtx, pkg: str, tl_defs: Sequence[ast.stmt]):
    assert isinstance(name_ctx, NameCtx)
    res = []
    def get_name(n: str) -> str:
        return f"{pkg}${n}" if pkg else n
    cls_names = {tl.name: ClassInfo(get_name(tl.name)) for tl in tl_defs if isinstance(tl, ast.ClassDef)}
    func_names = {tl.name: FuncInfo(get_name(tl.name)) for tl in tl_defs if isinstance(tl, ast.FunctionDef)}
    name_ctx = name_ctx.updated(cls_names)
    name_ctx = name_ctx.updated(func_names)
    name_ctx = TypeParseCtx(name_ctx.names)

    for mod in tl_defs:
        match mod:
            case ast.FunctionDef() as f:
                res.append(parse_tl_func_def(name_ctx, f))
            case ast.ClassDef() as c:
                res.append(parse_class_def(name_ctx, c))
            case _:
                assert False
    return res

def parse_type_lib(resolver: TypeParseCtx, pkg: str, tlib: str):
    tl_defs = ast.parse(tlib).body
    return parse_type_lib0(resolver, pkg, tl_defs)


def parse_type_lib2(resolver: TypeParseCtx, pkg: str, tlib: str) -> dict:
    res = {}
    for tl in parse_type_lib(resolver, pkg, tlib):
        n = tl.name
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
    return res