import unittest

import ast
from pynotole import myast
from pynotole.pyparser2 import parse_stmt

from pynotole.pull_consts import PullConsts

def parse(code: str) -> list[myast.Stmt]:
    return list(map(parse_stmt, ast.parse(code).body))

def as_expr(code: str) -> myast.Expr:
    return parse(code)[0].value

class PullConstTest(unittest.TestCase):
    def test_pull_pkg(self):
        puller = PullConsts(pkgs={'pkg'})

        expr = as_expr("pkg.Cls()")
        consts, expr_ = puller.proc_expr(expr)
        self.assertEqual(len(consts), 1)
        v, e2 = list(consts.items())[0]
        self.assertEqual(e2, as_expr('pkg.Cls'))
        self.assertEqual(expr_, as_expr(f'{v}()'))

    def test_pull_cls_attr(self):
        puller = PullConsts(classes={'Cls'})

        expr = as_expr("Cls.func()")
        consts, expr_ = puller.proc_expr(expr)
        self.assertEqual(len(consts), 1)
        v, e2 = list(consts.items())[0]
        self.assertEqual(e2, as_expr('Cls.func'))
        self.assertEqual(expr_, as_expr(f'{v}()'))

    def test_pull_cls_subscript(self):
        puller = PullConsts(classes={'Cls'})

        expr = as_expr("Cls[A]()")
        consts, expr_ = puller.proc_expr(expr)
        self.assertEqual(len(consts), 1)
        v, e2 = list(consts.items())[0]
        self.assertEqual(e2, as_expr('Cls[A]'))
        self.assertEqual(expr_, as_expr(f'{v}()'))

        expr = as_expr("Cls[A,B]()")
        consts, expr_ = puller.proc_expr(expr)
        self.assertEqual(len(consts), 1)
        v, e2 = list(consts.items())[0]
        self.assertEqual(e2, as_expr('Cls[A,B]'))
        self.assertEqual(expr_, as_expr(f'{v}()'))
