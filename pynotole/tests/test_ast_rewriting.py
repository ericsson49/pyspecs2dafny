import unittest

import ast
import astor
from pynotole.simplify_ast import AstRewriting


def parse_stmt(s):
    stmts = ast.parse(s).body
    assert len(stmts) == 1
    return stmts[0]


class AstRewritingTestCase(unittest.TestCase):
    def assertAstEqual(self, a, b):
        a_ = astor.dump_tree(a)
        b_ = astor.dump_tree(b)
        return self.assertEqual(a_, b_)

    def test_some(self):
        rule = AstRewriting.some(lambda x: x)
        self.assertEqual(None, rule(parse_stmt('pass')))
        self.assertEqual(None, rule(parse_stmt('while True: pass')))
        self.assertEqual(None, rule(parse_stmt('if True: pass')))
        self.assertEqual(None, rule(parse_stmt('if True:\n pass\nelse:\n 1')))
        self.assertEqual(None, rule(parse_stmt('if True:\n 1\nelse:\n pass')))
        rule = AstRewriting.some(lambda x: None)
        self.assertEqual(None, rule(parse_stmt('pass')))
        self.assertEqual(None, rule(parse_stmt('while True: pass')))
        self.assertEqual(None, rule(parse_stmt('if True: pass')))
        self.assertEqual(None, rule(parse_stmt('if True:\n pass\nelse:\n 1')))
        self.assertEqual(None, rule(parse_stmt('if True:\n 1\nelse:\n pass')))

        def r(s):
            match s:
                case ast.Pass():
                    return ast.Expr(value=ast.Ellipsis())
        rule = AstRewriting.some(r)
        self.assertAstEqual(None, rule(parse_stmt('pass')))
        self.assertAstEqual(parse_stmt('while True: ...'), rule(parse_stmt('while True: pass')))
        self.assertAstEqual(parse_stmt('if True: ...'), rule(parse_stmt('if True: pass')))
        self.assertAstEqual(parse_stmt('if True:\n ...\nelse:\n 1'), rule(parse_stmt('if True:\n pass\nelse:\n 1')))
        self.assertAstEqual(parse_stmt('if True:\n 1\nelse:\n ...'), rule(parse_stmt('if True:\n 1\nelse:\n pass')))

    def test_reduce(self):
        def r(s):
            match s:
                case ast.Pass():
                    return ast.Expr(value=ast.Ellipsis())
        rule = AstRewriting.reduce(r)
        self.assertAstEqual(
            parse_stmt('while True: ...'),
            rule(parse_stmt('while True: pass'))
        )
        self.assertAstEqual(
            parse_stmt('while True:\n while True:\n  ...'),
            rule(parse_stmt('while True:\n while True:\n  pass'))
        )

        rule = AstRewriting.reduce(lambda x: x)
        self.assertAstEqual(None, rule(parse_stmt('pass')))
        self.assertAstEqual(None, rule(parse_stmt('pass')))



if __name__ == '__main__':
    unittest.main()
