import unittest

import ast
from pynotole import myast
from pynotole.pyparser2 import parse_stmt

from pynotole.pull_consts import PullConsts

def parse(code: str) -> list[myast.Stmt]:
    return list(map(parse_stmt, ast.parse(code).body))

def as_expr(code: str) -> myast.Expr:
    return parse(code)[0].value

def as_stmt(code: str) -> myast.Stmt:
    return parse(code)[0]

class PullConstTest(unittest.TestCase):
    def test_pull_pkg(self):
        puller = PullConsts(pkgs={'pkg'})

        expr = as_expr("pkg.Cls()")
        consts, expr_ = puller.proc_expr(expr)
        (v, e2), = list(consts.items())
        self.assertEqual(e2, as_expr('pkg.Cls'))
        self.assertEqual(expr_, as_expr(f'{v}()'))

    def test_pull_cls_attr(self):
        puller = PullConsts(classes={'Cls'})

        expr = as_expr("Cls.func()")
        consts, expr_ = puller.proc_expr(expr)
        (v, e2), = list(consts.items())
        self.assertEqual(e2, as_expr('Cls.func'))
        self.assertEqual(expr_, as_expr(f'{v}()'))

    def test_pull_cls_subscript(self):
        puller = PullConsts(classes={'Cls'})

        expr = as_expr("Cls[A]()")
        consts, expr_ = puller.proc_expr(expr)
        (v, e2), = list(consts.items())
        self.assertEqual(e2, as_expr('Cls[A]'))
        self.assertEqual(expr_, as_expr(f'{v}()'))

        expr = as_expr("Cls[A,B]()")
        consts, expr_ = puller.proc_expr(expr)
        (v, e2), = list(consts.items())
        self.assertEqual(e2, as_expr('Cls[A,B]'))
        self.assertEqual(expr_, as_expr(f'{v}()'))

    def test_expr_stmt(self):
        self._test_stmt_template('{expr}')

    def test_assgn_stmt(self):
        self._test_stmt_template('a = {expr}')

    def test_if(self):
        self._test_stmt_template('if {expr}:\n  pass\n')
        self._test_stmt_template('if a:\n  {expr}\n')
        self._test_stmt_template('if a:\n  pass\nelse:  {expr}\n')

    def test_while(self):
        self._test_stmt_template('while {expr}:\n  pass\n')
        self._test_stmt_template('while a:\n  {expr}\n')

    def _test_stmt_template(self, template):
        puller = PullConsts(pkgs={'pkg'}, classes={'Cls'})

        for expr in ['pkg.func', 'Cls.func', 'Cls[A]']:
            stmt = as_stmt(template.format(expr=expr))
            consts, stmt_ = puller.proc_stmt(stmt)
            (v, e2), = list(consts.items())
            self.assertEqual(e2, as_expr(expr))
            expected = as_stmt(template.format(expr=v))
            self.assertEqual(stmt_, expected)
