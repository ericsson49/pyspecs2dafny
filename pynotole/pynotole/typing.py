from __future__ import annotations

from dataclasses import dataclass
from typing import Sequence, Any, Tuple, Optional, Set, Mapping


@dataclass(eq=True, frozen=True)
class ClsTemplate:
    type_vars: Sequence[str]
    base: Cls | None
    attrs: Mapping[str, FuncSignature | Cls | str]

    def replace_type_vars(self, type_params: Sequence[Cls], expr):
        assert len(self.type_vars) == len(type_params)
        tv_map = dict(zip(self.type_vars, type_params))

        def replace(c: FuncSignature | Cls | str):
            match c:
                case str(tv):
                    return tv_map[tv]
                case Cls(cname, tparams):
                    tparams_ = tuple(map(replace, tparams))
                    return Cls(cname, tparams_)
                case FuncSignature([], args, ret):
                    args_ = [(a, replace(t), d) for a, t, d in args]
                    ret_ = replace(ret)
                    return FuncSignature([], args_, ret_)
                case _:
                    assert False

        return replace(expr)


@dataclass(eq=True, frozen=True)
class Cls:
    name: str
    tparams: Sequence[Cls | str] = ()


@dataclass(eq=True, frozen=True)
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


CLS_OBJECT = ClsTemplate([], None, {})


class TypingEnv:
    def __init__(self, names=None):
        self.names = names or {}

    def try_resolve_attr(self, cls: Cls, attr: str):
        cls_info = self.resolve_class(cls.name)
        if attr in cls_info.attrs:
            return cls_info.replace_type_vars(cls.tparams, cls_info.attrs[attr])
        elif cls_info.base is not None:
            return self.try_resolve_attr(cls_info.base, attr)
        else:
            return None

    def resolve_attr(self, cls: Cls, attr: str):
        res = self.try_resolve_attr(cls, attr)
        assert res is not None, f"cannot resolve {cls.name}#{attr}"
        return res

    def resolve_var(self, name) -> Cls:
        assert isinstance(self.names[name], Cls)
        return self.names[name]

    def resolve_class(self, name) -> ClsTemplate:
        if name == "object":
            return CLS_OBJECT
        else:
            assert isinstance(self.names[name], ClsTemplate)
            return self.names[name]

    def resolve_func(self, name) -> Sequence[FuncSignature]:
        return self.names[name]

    def update(self, names):
        self.names |= names

    def copy(self) -> TypingEnv:
        return TypingEnv(self.names)


def get_ancestors(type_env: TypingEnv, a: Cls):
    super = get_super_type(type_env, a)
    if super is None:
        return [a]
    else:
        return [a] + get_ancestors(type_env, super)


def get_super_type(type_env: TypingEnv, a: Cls):
    if a.name == "object":
        return None
    else:
        cls = type_env.resolve_class(a.name)
        return cls.replace_type_vars(a.tparams, cls.base)


def try_meet(type_env: TypingEnv, a: Cls, b: Cls) -> Cls | None:
    if a.name == b.name:
        cls = a.name
        a_tps, b_tps = a.tparams, b.tparams
        co_vs, in_vs, contra_vs = get_type_param_variances(Cls(cls, a_tps))
        res_tps = [None] * len(a_tps)
        for i in co_vs:
            res = try_meet(type_env, a_tps[i], b_tps[i])
            if res is None:
                return None
            else:
                res_tps[i] = res
        for i in in_vs:
            if a_tps[i] != b_tps[i]:
                return None
            else:
                res_tps[i] = a_tps[i]
        for i in contra_vs:
            res = try_join(type_env, a_tps[i], b_tps[i])
            if res is None:
                return None
            else:
                res_tps[i] = res
        return Cls(cls, tuple(res_tps))
    else:
        ancestors_of_a = {c.name: c.tparams for c in get_ancestors(type_env, a)}
        ancestors_of_b = {c.name: c.tparams for c in get_ancestors(type_env, b)}
        if a.name in ancestors_of_b:
            if try_check_sub_type(type_env, ancestors_of_b[a.name], a):
                return b
            else:
                return None
        elif b.name in ancestors_of_a:
            if try_check_sub_type(type_env, ancestors_of_a[b.name], b):
                return a
            else:
                return None
        else:
            return None

def try_join(type_env: TypingEnv, a: Cls, b: Cls) -> Cls | None:
    ancestors_of_a = list(get_ancestors(type_env, a))
    ancestors_of_b = list(get_ancestors(type_env, b))
    common = [ac for ac,bc in zip((c.name for c in reversed(ancestors_of_a)),(c.name for c in reversed(ancestors_of_b))) if ac==bc]
    for res_cls in reversed(common):
        a_map = {c.name: c.tparams for c in ancestors_of_a}
        a_tps = a_map[res_cls]
        b_map = {c.name: c.tparams for c in ancestors_of_b}
        b_tps = b_map[res_cls]
        co_vs, in_vs, contra_vs = get_type_param_variances(Cls(res_cls, b_tps))
        assert len(contra_vs) == 0, "todo"
        res_tps = [None] * len(b_tps)
        match_error = False
        for i in co_vs:
            res_tps[i] = try_join(type_env, a_tps[i], b_tps[i])
            assert res_tps[i] is not None
        for i in in_vs:
            if a_tps[i] != b_tps[i]:
                match_error = True
                break
            res_tps[i] = a_tps[i]
        if match_error:
            continue
        return Cls(res_cls, tuple(res_tps))
    assert False, "todo try_join"


def get_type_param_variances(cls: Cls) -> Tuple[Sequence[int], Sequence[int], Sequence[int]]:
    match cls:
        case Cls(_, []):
            return (), (), ()
        case Cls("list", _):
            return (), (0,), ()
        case Cls("Sequence", _):
            return (0,), (), ()
        case _:
            assert False, f"type variances {cls.name}"


def try_check_sub_type(type_env: TypingEnv, a: Cls | str, b: Cls | str):
    match a, b:
        case Cls() as a, Cls(b_cls, b_tps):
            ancestors_of_a = {c.name: c.tparams for c in get_ancestors(type_env, a)}
            if b_cls in ancestors_of_a:
                a_tps = ancestors_of_a[b_cls]
                co_vs, in_vs, contra_vs = get_type_param_variances(b)
                return {(a_tps[i], b_tps[i]) for i in list(co_vs) + list(in_vs)} \
                    | {(b_tps[i], a_tps[i]) for i in list(in_vs) + list(contra_vs)}
            else:
                return None
        case _:
            return [(a, b)]
