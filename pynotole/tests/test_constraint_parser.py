import unittest
from pynotole.constraint_parser import parse_constrs, parse_term
from pynotole.constr_solver import Atom, Var


class ConstraintParserTestCase(unittest.TestCase):
    def test_parse_term(self):
        self.assertEqual(Var('A'), parse_term('?A'))

        self.assertEqual(Atom('A'), parse_term('A'))

        self.assertEqual(Atom('A',(Atom('B'),)), parse_term('A[B]'))
        self.assertEqual(Atom('A',(Atom('B'),Atom('C'))), parse_term('A[B,C]'))
        self.assertEqual(Atom('A',(Var('B'),)), parse_term('A[?B]'))
        self.assertEqual(Atom('A',(Var('B'),Var('C'))), parse_term('A[?B,?C]'))
        self.assertEqual(Atom('A',(Atom('B'),Var('C'))), parse_term('A[B,?C]'))
        self.assertEqual(Atom('A',(Var('B'),Atom('C'))), parse_term('A[?B,C]'))

    def test_parse_constraints(self):
        self.assertEqual([('<:', Var('A'), Var('B'))], parse_constrs('?A <: ?B'))  # add assertion here
        self.assertEqual([('=:=', Var('A'), Var('B'))], parse_constrs('?A =:= ?B'))  # add assertion here
        self.assertEqual([('<:', Atom('A'), Atom('B'))], parse_constrs('A <: B'))  # add assertion here
        self.assertEqual([('=:=', Atom('A'), Atom('B'))], parse_constrs('A =:= B'))  # add assertion here


if __name__ == '__main__':
    unittest.main()
