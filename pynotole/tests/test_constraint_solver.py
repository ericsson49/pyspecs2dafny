import unittest
from pynotole.constraint_parser import parse_constrs, parse_term
from pynotole.constraint_solver import *
from toolz.itertoolz import mapcat, groupby

def t(s):
    return parse_term(s)


def c(s):
    def convert(c):
        match c:
            case '=:=', a, b:
                return [(a, b), (b, a)]
            case '<:', a, b:
                return [(a, b)]
    return set(mapcat(convert, parse_constrs(s)))

def mk_constrs(subst: dict, cs):
    return set(mapcat(lambda item: [(item[0], item[1]), (item[1], item[0])], subst.items())) | cs


class ConstraintSolverTestCase(unittest.TestCase):
    def test_substitution(self):
        self.assertEqual(t('?B'), apply_subst({t('?A'): t('?B')}, t('?A')))
        self.assertEqual(t('B'), apply_subst({t('?A'): t('B')}, t('B')))
        self.assertEqual(t('B'), apply_subst({t('?A'): t('B')}, t('?A')))
        self.assertEqual(t('A'), apply_subst({t('?A'): t('B')}, t('A')))

        self.assertEqual(t('A[B]'), apply_subst({t('?A'): t('B')}, t('A[?A]')))

    def test_merge_substitutions(self):
        self.assertEqual({}, merge_substs({}, {}))
        self.assertEqual({t('?A'): t('?B')}, merge_substs({t('?A'): t('?B')}, {}))
        self.assertEqual({t('?A'): t('?B'), t('?X'): t('?B')}, merge_substs({t('?A'): t('?B')}, {t('?X'): t('?A')}))
        self.assertEqual({t('?A'): t('?B'), t('?X'): t('?B')}, merge_substs({t('?A'): t('?B')}, {t('?X'): t('?B')}))
        self.assertEqual({t('?A'): t('?B'), t('?X'): t('A')}, merge_substs({t('?A'): t('?B')}, {t('?X'): t('A')}))

    def test_simplify_step(self):
        self.assertEqual(({}, set()), simplify_step(c('?A <: ?A')))
        self.assertEqual(({}, c('?A <: ?B')), simplify_step(c('?A <: ?B')))
        self.assertEqual(({}, c('?A <: ?B, ?B <: ?C')), simplify_step(c('?A <: ?B, ?B <: ?C')))
        self.assertEqual(c('?A =:= ?B'), mk_constrs(*simplify_step(c('?A <: ?B, ?B <: ?A'))))
        self.assertEqual(({t('?A'): t('int'), t('?B'): t('int')}, set()), simplify_step(c('int <: ?A, ?A <: ?B, ?B <: int')))
        # self.assertEqual(
        #     c('?B =:= seq[?A], ?A =:= ?C'),
        #     mk_constrs(*simplify_step(c('seq[?A] <: ?B, ?B <: seq[?C], ?A =:= ?C'))))


if __name__ == '__main__':
    unittest.main()
