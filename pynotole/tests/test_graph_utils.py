import unittest

from pynotole.graph.utils import *
from tests.utils.graph import mk_block


class GraphUtilsTestCase(unittest.TestCase):
    def test_graph(self):
        g = Graph([(1,2), (2,3), (2,4), (2,6), (3,5), (4,5), (5,2)])

        self.assertEqual({1,2,3,4,5,6}, g.get_nodes())
        self.assertEqual([], g.get_preds(1))
        self.assertEqual([1,5], g.get_preds(2))
        self.assertEqual([2], g.get_preds(3))
        self.assertEqual([2], g.get_preds(4))
        self.assertEqual([3,4], g.get_preds(5))
        self.assertEqual([2], g.get_preds(6))

        self.assertEqual([2], g.get_succs(1))
        self.assertEqual([3,4,6], g.get_succs(2))
        self.assertEqual([5], g.get_succs(3))
        self.assertEqual([5], g.get_succs(4))
        self.assertEqual([2], g.get_succs(5))
        self.assertEqual([], g.get_succs(6))

    def test_dominance(self):
        g = Graph([(1,2), (2,3), (2,4), (2,6), (3,5), (4,5), (5,2)])

        self.assertEqual({
            1: {1},
            2: {1, 2},
            3: {1, 2, 3},
            4: {1, 2, 4},
            5: {1, 2, 5},
            6: {1, 2, 6}
        }, dominance(g.edges))

        self.assertEqual({2: 1, 3: 2, 4: 2, 5: 2, 6: 2}, imm_dom(g.get_edges()))

        self.assertEqual({1: set(), 2: {2}, 3: {5}, 4: {5}, 5: {2}, 6: set()}, dom_frontier(g.get_edges()))

    def test_kosaraju(self):
        g = Graph([(1,2), (2,3), (2,4), (2,6), (3,5), (4,5), (5,2)])

        res = kosaraju(g.get_edges())

        cycles = set()
        for rep, cycle in in_nodes(res.items()).items():
            self.assertIn(rep, cycle, "the cycle's representative should belong to the cycle")
            cycles.add(fset(cycle))

        self.assertEqual(g.get_nodes(), fset(concat(cycles)))
        self.assertEqual({fset({1}), fset({2,3,4,5}), fset({6})}, cycles)

    def test_cfg(self):
        cfg = CFG.make([
            (1, mk_block('i = 0\nn = 0\na = 0'), [2]),
            (2, mk_block('f(i, n)'), [3, 4, 6]),
            (3, mk_block('b = 1'), [5]),
            (4, mk_block('b = f(i, n)'), [5]),
            (5, mk_block('i = f(a, i)\nb = g(a, b)'), [2]),
            (6, mk_block('a'), set())
        ])

        self.assertEqual({1,2,3,4,5,6}, cfg.get_nodes())
        self.assertEqual([(1,2), (2,3), (2,4), (2,6), (3,5), (4,5), (5,2)], cfg.get_edges())

        res = live_vars(cfg)
        self.assertEqual(set(), res[1])
        self.assertEqual({'i', 'n', 'a'}, res[2])
        self.assertEqual({'i', 'a', 'n'}, res[3])
        self.assertEqual({'i', 'a', 'n'}, res[4])
        self.assertEqual({'i', 'a', 'b', 'n'}, res[5])
        self.assertEqual({'a'}, res[6])

        min_phis = phi_nodes(cfg, min=True)
        self.assertEqual({2: {'i'}, 5: {'b'}}, min_phis)
        phis = phi_nodes(cfg, min=False)
        self.assertEqual({2: {'i', 'b'}, 5: {'b'}}, phis)


if __name__ == '__main__':
    unittest.main()
