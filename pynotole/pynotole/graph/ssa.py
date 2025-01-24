from dataclasses import dataclass, replace

from toolz.itertoolz import *

from .utils import get_start_node, dominance, imm_dom, out_nodes, phi_nodes


class DomHelper:
    def __init__(self, edges):
        self.edges = edges
        self.start = get_start_node(edges)
        self.dominance = dominance(edges)
        self.dom_tree_edges = [(b, a) for a, b in imm_dom(edges).items()]

    def dominates(self, b1, b2):
        return b1 in self.dominance[b2]

    def dom_tree_preorder(self):
        succs = out_nodes(self.dom_tree_edges)

        def preorder(n):
            yield n
            for s in succs.get(n, []):
                yield from preorder(s)

        return preorder(self.start)


class NameRemapper:
    def __init__(self, dom_helper, init_params: set[str]):
        self.varCounts = {}
        start = dom_helper.start
        self.rd = {v: (v, start, -1) for v in init_params}
        self.dom_helper = dom_helper
        self.ii = None

    def fresh(self, v):
        cnt = self.varCounts.get(v, 0) + 1
        self.varCounts[v] = cnt
        return f'{v}_{cnt}'

    def dominates(self, i1, i2):
        b1, id1 = i1
        b2, id2 = i2
        if b1 == b2:
            return id2 >= id1
        else:
            return self.dom_helper.dominates(b1, b2)

    def update_reaching_def(self, v):
        assert self.ii is not None
        r = self.rd.get(v)
        while not (r is None or self.dominates(r[1:], self.ii)):
            r = self.rd.get(r[0])
        if r is not None:
            self.rd[v] = r

    def set_instr(self, ii):
        self.ii = ii

    def new_def_name(self, v):
        self.update_reaching_def(v)
        v_ = self.fresh(v)
        if self.rd.get(v) is not None:
            self.rd[v_] = self.rd.get(v)
        self.rd[v] = (v_, *self.ii)
        return v_

    def new_use_name(self, v):
        self.update_reaching_def(v)
        res = self.rd.get(v)
        return res[0] if res is not None else None


@dataclass
class PhiFunc:
    vdef: str
    uses: dict[object, str]

    def __repr__(self) -> str:
        return f'{self.vdef} := \u03d5({', '.join(map(str, self.uses.values()))})'

def make_phi_funcs(cfg):
    phis = {}
    for n, vs in phi_nodes(cfg).items():
        preds = cfg.get_preds(n)
        phis[n] = [PhiFunc(v, {p: v for p in preds}) for v in vs]
    return phis


def make_SSA(cfg):
    phis = make_phi_funcs(cfg)
    dom_helper = DomHelper(cfg.get_edges())
    name_remapper = NameRemapper(dom_helper, cfg.blocks[dom_helper.start].get_defs_uses()[0])

    def rename_phi_def(phi, var_map):
        v = phi.vdef
        phi.vdef = var_map.get(v, v)

    def rename_phi_use(phi, bb, var_map):
        v = phi.uses[bb]
        phi.uses[bb] = var_map.get(v, v)

    def get_phis_by_def(bb):
        return phis.get(bb, [])

    def get_phis_by_use(bb):
        return filter(lambda phi: bb in phi.uses, concat(phis.values()))

    block_instrs_ = {}

    for bb in dom_helper.dom_tree_preorder():
        phi_def_ii = (bb, -1)
        name_remapper.set_instr(phi_def_ii)
        for phi in get_phis_by_def(bb):
            v = phi.vdef
            rename_phi_def(phi, {v: name_remapper.new_def_name(v)})

        instr_block = cfg.blocks[bb]
        instr_ifc, instrs = instr_block.instr_renamer, instr_block.instrs
        instrs_ = []
        for idx, i in enumerate(instrs):
            defs, uses = instr_ifc.get_defs_uses(i)
            name_remapper.set_instr((bb, idx))

            var_map = {v: name_remapper.new_use_name(v) for v in uses}
            i_ = instr_ifc.rename_uses(i, var_map)

            var_map = {v: name_remapper.new_def_name(v) for v in defs}
            i_ = instr_ifc.rename_defs(i_, var_map)
            instrs_.append(i_)
        block_instrs_[bb] = instrs_

        phi_use_ii = (bb, len(instrs))
        name_remapper.set_instr(phi_use_ii)
        for phi in get_phis_by_use(bb):
            v = phi.uses[bb]
            rename_phi_use(phi, bb, {v: name_remapper.new_use_name(v)})

    return block_instrs_, phis