import ast

from pynotole.type_parser import get_base_type_env
from pynotole.type_checking import *
from pynotole.pyparser2 import parse_expr

import unittest


def parse(code):
    body = ast.parse(code).body
    assert len(body) == 1
    assert isinstance(body[0], ast.Expr)
    return parse_expr(body[0].value)


class TypeCheckTest(unittest.TestCase):
    def test_backward_checking(self):
        env = get_base_type_env()
        defs = dict(parse_type_lib("""
class A:
    f: B
    def __get_elem__(self, idx: int) -> C: ...
    def __get_slice__(self, idx: int) -> Sequence[C]: ...
class A_gen[T]:
    f: T
"""))
        env.update({**defs, 'a_gen': Cls("A_gen", [Cls('int')]), 'a': Cls('A')})

        def check(code, expected):
            self.assertEqual(calculate_result_type(parse(code), env), expected)

        check("None", Cls("NoneType"))
        check("True", Cls("bool"))
        check("False", Cls("bool"))
        check("1", Cls("int"))
        check('"string"', Cls("str"))
        check("b'111'", Cls("bytes"))

        check("1 + 2", Cls("int"))
        check("True + 1", Cls("int"))
        check("1 + False", Cls("int"))

        check("a or b", Cls("bool"))
        check("a and b or False", Cls("bool"))
        check("not False", Cls("bool"))

        check("a <= b", Cls("bool"))

        check("[1]", Cls("list", (Cls("int"),)))
        check("{1}", Cls("set", (Cls("int"),)))


        check("a", Cls("A"))
        check("a.f", Cls("B"))
        check("a_gen.f", Cls("int"))
        check("a[1]", Cls("C"))
        check("a[:3:]", Cls("Sequence", (Cls("C"),)))

    def test_bidirectional_type_checking(self):
        env = get_base_type_env()
        env.update(
            {'bv0': Cls("bool"), 'bv1': Cls("bool")}
        )

        def check(code, expected):
            expr = parse(code)
            cs = ConstraintStore()
            check_sub_typing(expr, expected, env, cs)

        check("None", Cls("NoneType"))
        check("True", Cls("bool"))
        check("False", Cls("bool"))
        check("1", Cls("int"))
        check('"string"', Cls("str"))
        check("b'111'", Cls("bytes"))

        check("1 + 2", Cls("int"))
        check("True + 1", Cls("int"))
        check("1 + False", Cls("int"))

        check("bv0 or bv1", Cls("bool"))
        check("bv0 and bv1 or False", Cls("bool"))
        check("not False", Cls("bool"))

        check("a <= b", Cls("bool"))

        check("[1]", Cls("list", (Cls("int"),)))
        check("{1}", Cls("set", (Cls("int"),)))


        check("a", Cls("A"))
        check("a.f", Cls("B"))
        check("a_gen.f", Cls("int"))
        check("a[1]", Cls("C"))
        check("a[:3:]", Cls("Sequence", (Cls("C"),)))
