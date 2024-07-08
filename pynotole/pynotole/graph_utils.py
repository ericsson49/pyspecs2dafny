from builtins import frozenset as fset
from typing import Iterable

from toolz.dicttoolz import *
from toolz.itertoolz import *


class Graph[N]:
    def __init__(self, edges: Iterable[tuple[N, N]]):
        self.edges = fset(edges)

    def get_nodes(self) -> fset[N]:
        return fset(mapcat(lambda e: e, self.edges))

    def get_edges(self) -> fset[tuple[N, N]]:
        return self.edges

    def get_preds(self, n: N) -> Iterable[N]:
        return fset(a for a,b in self.edges if b == n)

    def get_succs(self, n: N) -> Iterable[N]:
        return fset(b for a,b in self.edges if a == n)


class CFG[N,V](Graph[N]):
    def __init__(self, nodes: Iterable[tuple[N, Iterable[V], Iterable[V], Iterable[N]]]):
        super().__init__((n, s) for n, _, _, succs in nodes for s in succs)
        self.defs_and_uses = {n: (defs, uses) for n, defs, uses, succs in nodes}

    def get_defs(self, n: N) -> Iterable[V]:
        return self.defs_and_uses[n][0]

    def get_uses(self, n: N) -> Iterable[V]:
        return self.defs_and_uses[n][1]

    def live_vars(self) -> dict[N, fset[N]]:
        def step(ins: dict[N, Iterable[N]]):
            res = ins.copy()
            for n in self.get_nodes():
                out: set[V] = set()
                for s in self.get_succs(n):
                    out |= ins.get(s, set())
                gen, kill = set(self.get_uses(n)), set(self.get_defs(n))
                in_ = (out - kill) | gen
                res[n] = res.get(n, set()) | in_
            return res
        return _fixp(step)({})

    def phi_nodes(self, min=True):
        df = dom_frontier(self.get_edges())

        def get_df(ns: Iterable[N]) -> set[N]:
            return set(mapcat(lambda n: df.get(n, set()), ns))

        def get_idf(ns: Iterable[N]) -> set[N]:
            return _fixp(lambda xs: xs | get_df(xs))(get_df(ns))

        if min:
            lvs = self.live_vars()

        res = {}
        for n in self.get_nodes():
            defs = set(self.get_defs(n))
            for m in get_idf({n}):
                defs_ = defs.intersection(lvs.get(m)) if min else defs
                if defs_:
                    res[m] = res.get(m, set()) | defs_
        return res


def _fixp(f):
    def g(xs):
        return xs if (xs_ := f(xs)) == xs else g(xs_)
    return g


def out_nodes(g):
    return valmap(lambda ps: set(b for a,b in ps), groupby(0, g))


def in_nodes(g):
    return valmap(lambda ps: set(a for a,b in ps), groupby(1, g))


def get_nodes(g):
    froms = {a for a, b in g}
    tos = {b for a, b in g}
    return froms.union(tos)


def get_start_node(g):
    froms = {a for a, b in g}
    tos = {b for a, b in g}
    starts = froms.difference(tos)
    assert len(starts) == 1, f"{starts}"
    return list(starts)[0]


def kosaraju(g):
    outs = out_nodes(g)
    ins = in_nodes(g)
    visited = set()
    L = []

    def visit(u):
        if u not in visited:
            visited.add(u)
            for v in outs.get(u, set()):
                visit(v)
            L.insert(0, u)
    for u in set(outs.keys()).union(ins.keys()):
        visit(u)
    comps = {}

    def assign(u, root):
        if u not in comps:
            comps[u] = root
            for v in ins.get(u, set()):
                assign(v, root)
    for u in L:
        assign(u, u)
    return comps


def dominance(g):
    nodes = get_nodes(g)
    start = get_start_node(g)
    preds = in_nodes(g)
    domsOf = { start: {start} }
    for n in nodes:
        if n != start:
            domsOf[n] = nodes
    while True:
        changed = False
        for n in nodes:
            if n != start:
                intr = nodes
                for p in preds[n]:
                    intr = intr.intersection(domsOf[p])
                new_dom = {n}.union(intr)
                if new_dom != domsOf[n]:
                    domsOf[n] = new_dom
                    changed = True
        if not changed:
            break
    return domsOf


def imm_dom(g):
    domOf = dominance(g)
    idom = {}
    def strict_dom(p):
        return domOf[p].difference({p})
    for n in domOf.keys():
        res = strict_dom(n)
        for p in strict_dom(n):
            res = res.difference(strict_dom(p))
        assert len(res) <= 1
        if res:
            idom[n] = list(res)[0]
    return idom


def dom_frontier(g):
    nodes = get_nodes(g)
    id = imm_dom(g)
    preds = in_nodes(g)
    res = {}
    for b in nodes:
        res[b] = set()
    for b in nodes:
        if len(preds.get(b, ())) >= 2:
            for p in preds[b]:
                runner = p
                while runner != id[b]:
                    res[runner] = res[runner].union({b})
                    runner = id[runner]
    return res


