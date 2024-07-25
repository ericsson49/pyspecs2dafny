import unittest

import ast

from pynotole.ssa_ast import *
from pynotole.graph_utils import get_start_node, in_nodes, out_nodes
from pynotole.pyparser2 import parse_stmts as my_parse_stmts, parse_stmt as my_parse_stmt

def parse_stmts(code: str) -> list[ast.stmt]:
    return ast.parse(code).body


def parse_stmt(code: str) -> ast.stmt:
    stmts = parse_stmts(code)
    assert len(stmts) == 1
    return stmts[0]


labeler = IndexingLabeler()
bbf = PyASTBBlockFactory()


class CFGBuilderTest(unittest.TestCase):
    def base_checks(self, cfg):
        start_node = get_start_node(cfg.get_edges())
        end_node = get_start_node([(b,a) for a,b in cfg.get_edges()])
        self.assertEqual(cfg.entry, start_node)
        self.assertEqual(cfg.exit, end_node)

    def test_on_assign(self):
        b = CFGBuilder(labeler, bbf)
        entry, exit = b.get_entry(), b.get_exit()

        l0 = b.on_stmt(b.get_label(entry, 0), parse_stmt('a = b'), exit)
        b.add_edge(b.add_node(entry, BBlock(set(), set())), l0)
        cfg = b.build()

        self.base_checks(cfg)

        self.assertEqual([l0], cfg.get_succs(entry))
        self.assertEqual({'a'}, cfg.get_defs(l0))
        self.assertEqual({'b'}, cfg.get_uses(l0))
        self.assertEqual([exit], cfg.get_succs(l0))

    def test_on_expr(self):
        b = CFGBuilder(labeler, bbf)
        entry, exit = b.get_entry(), b.get_exit()

        l0 = b.on_stmt(b.get_label(entry, 0), parse_stmt('f(a, b, c)'), exit)
        b.add_edge(b.add_node(entry, BBlock(set(), set())), l0)
        cfg = b.build()

        self.base_checks(cfg)

        self.assertEqual([l0], cfg.get_succs(entry))
        self.assertEqual(set(), cfg.get_defs(l0))
        self.assertEqual({'a', 'b', 'c'}, cfg.get_uses(l0))
        self.assertEqual([exit], cfg.get_succs(l0))

    def test_on_if(self):
        b = CFGBuilder(labeler, bbf)
        entry, exit = b.get_entry(), b.get_exit()

        l0 = b.on_stmt(b.get_label(entry, 0), parse_stmt('if a:\n  x = b\nelse:\n  x = c'), exit)
        b.add_edge(b.add_node(entry, BBlock(set(), set())), l0)
        cfg = b.build()

        self.base_checks(cfg)

        self.assertEqual([l0], cfg.get_succs(entry))
        self.assertEqual(set(), cfg.get_defs(l0))
        self.assertEqual({'a'}, cfg.get_uses(l0))

        l1, l2 = cfg.get_succs(l0)
        self.assertEqual([exit], cfg.get_succs(l1))
        self.assertEqual({'x'}, cfg.get_defs(l1))
        self.assertEqual({'b'}, cfg.get_uses(l1))

        self.assertEqual([exit], cfg.get_succs(l2))
        self.assertEqual({'x'}, cfg.get_defs(l2))
        self.assertEqual({'c'}, cfg.get_uses(l2))

    def test_on_while(self):
        b = CFGBuilder(labeler, bbf)
        entry, exit = b.get_entry(), b.get_exit()

        l0 = b.on_stmt(b.get_label(entry, 0), parse_stmt('while a:\n  a = a - 1'), exit)
        b.add_edge(b.add_node(entry, BBlock(set(), set())), l0)
        cfg = b.build()

        self.base_checks(cfg)

        self.assertEqual([l0], cfg.get_succs(entry))
        self.assertEqual(set(), cfg.get_defs(l0))
        self.assertEqual({'a'}, cfg.get_uses(l0))

        body, loop_exit = list(cfg.get_succs(l0))

        self.assertEqual({'a'}, cfg.get_defs(body))
        self.assertEqual({'a'}, cfg.get_uses(body))
        self.assertEqual([l0], cfg.get_succs(body))

        self.assertEqual(exit, loop_exit)

        lvs = cfg.live_vars()
        self.assertEqual({
            entry: {'a'},
            l0: {'a'},
            body: {'a'},
            exit: set()
        }, lvs)

        phis = cfg.phi_nodes()

        self.assertEqual({l0: {'a'}}, phis)

    def test_complex(self):
        b = CFGBuilder(labeler, MyASTBBlockFactory())
        entry, exit = b.get_entry(), b.get_exit()

        code = '''
n = 5
i = 0
while n:
    i = i + 1
    n = n -1
_return_ = i
        '''

        cfg = b.make_cfg(my_parse_stmts(parse_stmts(code)), set())

        self.base_checks(cfg)

        l0, = cfg.get_succs(entry)
        self.assertEqual({'n'}, cfg.get_defs(l0))
        self.assertEqual(set(), cfg.get_uses(l0))

        l1, = cfg.get_succs(l0)

        l_head, = cfg.get_succs(l1)

        l_body, l_exit = cfg.get_succs(l_head)

        self.assertEqual({'i'}, cfg.get_defs(l_body))
        self.assertEqual({'i'}, cfg.get_uses(l_body))

        l_body_2, = cfg.get_succs(l_body)
        self.assertEqual({'n'}, cfg.get_defs(l_body_2))
        self.assertEqual({'n'}, cfg.get_uses(l_body_2))
        self.assertEqual([l_head], cfg.get_succs(l_body_2))

        self.assertEqual({'_return_'}, cfg.get_defs(l_exit))
        self.assertEqual({'i'}, cfg.get_uses(l_exit))
        self.assertEqual([exit], cfg.get_succs(l_exit))

        lvs = cfg.live_vars()
        self.assertEqual(8, len(lvs))
        self.assertEqual(set(), lvs[entry])
        self.assertEqual(set(), lvs[l0])
        self.assertEqual({'n'}, lvs[l1])
        self.assertEqual({'n', 'i'}, lvs[l_head])
        self.assertEqual({'n', 'i'}, lvs[l_body])
        self.assertEqual({'n', 'i'}, lvs[l_body_2])
        self.assertEqual({'i'}, lvs[l_exit])
        self.assertEqual(set(), lvs[exit])

        phis = cfg.phi_nodes()
        self.assertEqual(1, len(phis))
        self.assertEqual({'n', 'i'}, phis[l_head])

        from pynotole.ssa import mk_cfg

        res = mk_cfg(my_parse_stmts(parse_stmts(code)))
        pass


if __name__ == '__main__':
    unittest.main()
