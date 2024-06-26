from dataclasses import dataclass
from typing import Sequence, Tuple, Dict, Set, Optional, Callable
from .graph_utils import kosaraju, in_nodes, out_nodes
from toolz.dicttoolz import valmap


@dataclass(frozen=True)
class Term(object):
    pass


@dataclass(frozen=True)
class Var(Term):
    v: str

    def __repr__(self):
        return f"?{self.v}"


@dataclass(frozen=True)
class Atom(Term):
    f: str
    args: Tuple[Term, ...] = ()

    def __repr__(self):
        return f"{self.f}{self.args}"


def apply_subst(st: Dict[Var, Term], t: Term) -> Term:
    assert t is not None
    match t:
        case Var(v):
            return st.get(Var(v)) or t
        case Atom(f, args):
            return Atom(f, tuple(apply_subst(st, a) for a in args))


def merge_substs(st, st2):
    return valmap(lambda v: apply_subst(st, v), st2) | st


def unify(eqs):
    subst = {}
    for a, b in eqs:
        match (apply_subst(subst, a), apply_subst(subst, b)):
            case (x, y) if x == y:
                st = {}
            case (Var(v), t):
                st = {Var(v): t}
            case (t, Var(v)):
                st = {Var(v): t}
            case (Atom(f1, as1), Atom(f2, as2)):
                if f1 == f2 and len(as1) == len(as2):
                    st = unify(zip(as1, as2))
                else:
                    raise Exception()
            case _:
                raise Exception()
        subst = merge_substs(st, subst)
    return subst


def simplify_step(constrs):
    ccs = kosaraju(constrs)
    subst = unify(ccs.items())
    new_constrs = set()
    for a, b in constrs:
        aa, ab = apply_subst(subst, a), apply_subst(subst, b)
        if aa != ab:
            new_constrs.add((aa, ab))
    return subst, list(new_constrs)


def simplify(consts):
    subst = {}
    cs = consts
    while True:
        new_st, new_cs = simplify_step(cs)
        subst = merge_substs(new_st, subst)
        if set(new_cs) != set(cs):
            cs = new_cs
        else:
            break
    return subst, cs


def trans_closure(cs):
    outs = out_nodes(cs)
    res = set(cs)
    curr = res
    while len(curr) > 0:
        curr = set((k, v2) for k, v in curr for v2 in outs.get(v, set())).difference(res)
        res.update(curr)
    return res


def simplify2(constrs):
    subst, cs = simplify(constrs)
    while True:
        tcs = trans_closure(cs)
        cs2 = set()
        for a, b in tcs:
            match (a,b):
                case (Atom() as a, Atom() as b):
                    cs2.update(check_st(a, b))
        eqs, new_cs = simplify(cs2.union(cs))
        new_subst = subst.copy()
        new_subst.update(eqs)
        if new_cs != cs or new_subst != subst:
            cs = new_cs
            subst = new_subst
        else:
            break
    return subst, cs


def convert_cs(cs: Set[Tuple[str, Term, Term]]) -> Set[Tuple[Term, Term]]:
    res = set()
    for k, a, b in cs:
        match k:
            case '=:=':
                res.add((a, b))
                res.add((b, a))
            case '<:':
                res.add((a, b))
            case _:
                assert False
    return  res


def simplify_ext(cs: Set[Tuple[str, Term, Term]]):
    return simplify(convert_cs(cs))


def simplify2_ext(cs: Set[Tuple[str, Term, Term]]):
    return simplify2(convert_cs(cs))


def get_parent(cls: Atom) -> Atom|None:
    args = cls.args
    match cls.f:
        case "object":
            assert len(args) == 0
            return None
        case "seq":
            assert len(args) == 1
            return Atom("coll", args)
        case "set":
            assert len(args) == 1
            return Atom("coll", args)
        case "dict":
            assert len(args) == 2
            return Atom("coll", (Atom("tuple2", args),))
        case "list":
            assert len(args) == 1
            return Atom("seq", args)
        case _:
            return Atom("object", ())


def get_tp_variances(cls: str) -> Tuple[Sequence[int], Sequence[int], Sequence[int]]:
    match cls:
        case "list":
            return ((),(),(0,))
        case "seq":
            return ((0,),(),())
        case _:
            assert False, "todo"


def get_ancestors(a: Atom) -> Sequence[Atom]:
    parent = get_parent(a)
    return [a] + list(get_ancestors(parent) if parent is not None else [])


def check_st(a: Atom, b: Atom) -> Sequence[Tuple[Term, Term]]:
    anc_of_a = { c.f: c.args for c in get_ancestors(a)}
    cls_of_b = get_ancestors(b)[0]
    args_of_a = anc_of_a[cls_of_b.f]
    args_of_b = cls_of_b.args
    co_vs, in_vs, contra_vs = get_tp_variances(cls_of_b.f)
    return [(args_of_a[i], args_of_b[i]) for i in list(co_vs) + list(in_vs)] \
            + [(args_of_b[i], args_of_a[i]) for i in list(in_vs) + list(contra_vs)]



# print(kosaraju([(0,1), (0,2), (1,3), (3,4), (4,1)]))
# cs = [
#     (Atom("f", (Var("c"),)), Var("d")),
#     (Var("d"), Atom("f", (Var("b"),))),
#     (Var("b"), Var("c")),
#     (Var("c"), Var("b"))
# ]
#print(get_ancestors(Atom("list", (Var("a"),))))
#print(check_st(Atom("list",(Var("v"),)), Atom("seq", (Var("v"),))))
