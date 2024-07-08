import unittest

from pynotole.graph_utils import *

class GraphUtilsTestCase(unittest.TestCase):
    def test_graph(self):
        g = Graph([(1,2), (2,3), (2,4), (2,6), (3,5), (4,5), (5,2)])

        self.assertEqual({1,2,3,4,5,6}, g.get_nodes())
        self.assertEqual(set(), g.get_preds(1))
        self.assertEqual({1,5}, g.get_preds(2))
        self.assertEqual({2}, g.get_preds(3))
        self.assertEqual({2}, g.get_preds(4))
        self.assertEqual({3,4}, g.get_preds(5))
        self.assertEqual({2}, g.get_preds(6))

        self.assertEqual({2}, g.get_succs(1))
        self.assertEqual({3,4,6}, g.get_succs(2))
        self.assertEqual({5}, g.get_succs(3))
        self.assertEqual({5}, g.get_succs(4))
        self.assertEqual({2}, g.get_succs(5))
        self.assertEqual(set(), g.get_succs(6))

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

        self.assertEqual({2: 1, 3: 2, 4: 2, 5: 2, 6: 2}, imm_dom(g.edges))

        self.assertEqual({1: set(), 2: {2}, 3: {5}, 4: {5}, 5: {2}, 6: set()}, dom_frontier(g.edges))

    def test_kosaraju(self):
        g = Graph([(1,2), (2,3), (2,4), (2,6), (3,5), (4,5), (5,2)])

        res = kosaraju(g.edges)

        cycles = set()
        for rep, cycle in in_nodes(res.items()).items():
            self.assertIn(rep, cycle, "the cycle's representative should belong to the cycle")
            cycles.add(fset(cycle))

        self.assertEqual(g.get_nodes(), fset(concat(cycles)))
        self.assertEqual({fset({1}), fset({2,3,4,5}), fset({6})}, cycles)


if __name__ == '__main__':
    unittest.main()
