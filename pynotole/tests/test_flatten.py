import unittest

import ast
from pynotole.pyparser2 import parse_stmt
from pynotole import myast
from pynotole.flatten import FlattenExprs

class FlattenTest(unittest.TestCase):
    def test_flatten_expr(self):
        def parse(code: str) -> myast.Expr:
            return parse_stmt(ast.parse(code).body[0]).value
        flattener = FlattenExprs()

        e = parse("f(g(1),2)")
        assgns, e1 = flattener.proc_expr(e)
        self.assertEqual(len(assgns), 1)
        v, e2 = assgns[0]
        self.assertEqual(e2, parse("g(1)"))
        self.assertEqual(e1, parse(f"f({v}, 2)"))

        e = parse("f(g(h(1)),2)")
        assgns, e1 = flattener.proc_expr(e)
        self.assertEqual(len(assgns), 2)
        v3, e3 = assgns[0]
        v2, e2 = assgns[1]
        self.assertEqual(e1, parse(f"f({v2},2)"))
        self.assertEqual(e2, parse(f"g({v3})"))
        self.assertEqual(e3, parse("h(1)"))

        e = parse("f(g(1).a)")
        assgns, e1 = flattener.proc_expr(e)
        pass

    def test_flatten_stmt(self):
        def parse(code: str) -> myast.Stmt:
            return parse_stmt(ast.parse(code).body[0])
        flattener = FlattenExprs()

        s = parse("f(g(1),2)")
        stmts = flattener.proc_stmt(s)
        self.assertEqual(len(stmts), 2)
        self.assertEqual(stmts[0], parse("t0 = g(1)"))
        self.assertEqual(stmts[1], parse("f(t0,2)"))

        s = parse("a = f(g(1),2)")
        stmts = flattener.proc_stmt(s)
        self.assertEqual(len(stmts), 2)
        self.assertEqual(stmts[0], parse("t1 = g(1)"))
        self.assertEqual(stmts[1], parse("a = f(t1,2)"))
