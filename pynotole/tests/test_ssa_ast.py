import unittest

from pynotole.graph.builders import *
from utils.ssa import *


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
        b.add_edge(b.add_node(entry, DummyBlock()), l0)
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
        b.add_edge(b.add_node(entry, DummyBlock()), l0)
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
        b.add_edge(b.add_node(entry, DummyBlock()), l0)
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
        b.add_edge(b.add_node(entry, DummyBlock()), l0)
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

        lvs = live_vars(cfg)
        self.assertEqual({
            entry: {'a'},
            l0: {'a'},
            body: {'a'},
            exit: set()
        }, lvs)

        phis = phi_nodes(cfg)

        self.assertEqual({l0: {'a'}}, phis)

    def test_complex(self):
        b = CFGBuilder(labeler, bbf)
        entry, exit = b.get_entry(), b.get_exit()

        code = '''
n = 5
i = 0
while n:
    i = i + 1
    n = n -1
_return_ = i
        '''

        cfg = b.make_cfg(parse_stmts(code), set())

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

        lvs = live_vars(cfg)
        self.assertEqual(8, len(lvs))
        self.assertEqual(set(), lvs[entry])
        self.assertEqual(set(), lvs[l0])
        self.assertEqual({'n'}, lvs[l1])
        self.assertEqual({'n', 'i'}, lvs[l_head])
        self.assertEqual({'n', 'i'}, lvs[l_body])
        self.assertEqual({'n', 'i'}, lvs[l_body_2])
        self.assertEqual({'i'}, lvs[l_exit])
        self.assertEqual(set(), lvs[exit])

        phis = phi_nodes(cfg)
        self.assertEqual(1, len(phis))
        self.assertEqual({'n', 'i'}, phis[l_head])


class InstrBlockTestCase(unittest.TestCase):
    def test_defs_uses_simple(self):
        bb = BBlock(SimpleInstructionRenamer, mk_instrs('a = b\nc = a'))
        self.assertEqual(({'a', 'c'}, {'b'}), bb.get_defs_uses())

    def test_defs_uses_expr_simple(self):
        bb = BBlock(SimpleInstructionRenamer, mk_instrs('a'))
        self.assertEqual((set(), {'a'}), bb.get_defs_uses())

    def test_defs_uses_pyast(self):
        bb = BBlock(AstStmtRenamer, parse_stmts('a = b\nc = a'))
        self.assertEqual(({'a', 'c'}, {'b'}), bb.get_defs_uses())

    def test_defs_uses_expr_pyast(self):
        bb = BBlock(AstStmtRenamer, parse_stmts('a'))
        self.assertEqual((set(), {'a'}), bb.get_defs_uses())


class SSAConvertorTest(unittest.TestCase):
    def test_make_SSA_cfg_builder(self):
        builder = CFGBuilder(labeler, bbf)
        code = '''
x = 0
y = 0
while a:
    if t:
        y = 1
        x = 0
    else:
        tmp = x
        x = y
        y = tmp
f(x,y)
        '''
        cfg = builder.make_cfg(parse_stmts(code), {'a', 't'})
        a,b = make_SSA(cfg)
        pass

    def test_make_SSA(self):
        cfg = CFG.make([
            ('r', mk_block(''), ['A']),
            ('A', mk_block(''), ['B', 'C']),
            ('B', mk_block('y = 1\nx = 0'), ['D']),
            ('C', mk_block('tmp = x\nx = y\ny = tmp'), ['D', 'E']),
            ('D', mk_block('y = f(x, y)'), ['A', 'E']),
            ('E', mk_block('f(x, y)'), []),
        ])

        expected_instrs = {
            'r': [], 'A': [],
            'B': mk_instrs('y2 = 1\nx2 = 0'),
            'C': mk_instrs('tmp = x1\nx3 = y1\ny3 = tmp'),
            'D': mk_instrs('y5 = f(x4, y4)'),
            'E': mk_instrs('f(x5, y6)')
        }
        expected_phis = {
            'A': [PhiFunc('x1', {'D': 'x4', 'r': None}), PhiFunc('y1', {'D': 'y5', 'r': None})],
            'D': [PhiFunc('x4', {'B': 'x2', 'C': 'x3'}), PhiFunc('y4', {'B': 'y2', 'C': 'y3'})],
            'E': [PhiFunc('x5', {'D': 'x4', 'C': 'x3'}), PhiFunc('y6', {'D': 'y5', 'C': 'y3'})],
        }

        block_instrs, phi_funcs = make_SSA(cfg)

        self.assertEqual(block_instrs.keys(), block_instrs.keys())

        instr_places = extract_places_from_blocks(block_instrs)
        phi_places = extract_places_from_phis(phi_funcs)

        expected_instr_places = extract_places_from_blocks(expected_instrs)
        expected_phi_places = extract_places_from_phis(expected_phis)

        matches = match_places(self, instr_places | phi_places, expected_instr_places | expected_phi_places)
        unify(self, matches)


if __name__ == '__main__':
    unittest.main()
