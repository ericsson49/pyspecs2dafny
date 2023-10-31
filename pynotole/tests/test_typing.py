import unittest

from pynotole.typing import *
from pynotole.type_parser import get_base_type_env, str_to_type

class TypingTest(unittest.TestCase):
    def test_get_super_type(self):
        def s2t(expr: str): return str_to_type(expr)
        type_env = get_base_type_env()
        self.assertEqual(get_super_type(type_env, s2t("list[int]")), s2t("Sequence[int]"))
        self.assertEqual(get_ancestors(type_env, s2t("list[int]")), [s2t("list[int]"), s2t("Sequence[int]"), s2t("object")])

        self.assertEqual(try_check_sub_type(type_env, s2t("list[int]"), s2t("Sequence[int]")), {(s2t('int'), s2t('int'))})

    def test_try_join(self):
        def s2t(expr: str): return str_to_type(expr)
        type_env = get_base_type_env()
        self.assertEqual(try_join(type_env, s2t("Sequence[int]"), s2t("list[int]")), s2t("Sequence[int]"))
        self.assertEqual(try_join(type_env, s2t("Sequence[object]"), s2t("list[int]")), s2t("Sequence[object]"))
        self.assertEqual(try_join(type_env, s2t("Sequence[bool]"), s2t("list[int]")), s2t("Sequence[int]"))
        self.assertEqual(try_join(type_env, s2t("list[bool]"), s2t("list[int]")), s2t("Sequence[int]"))

    def test_try_meet(self):
        def s2t(expr: str): return str_to_type(expr)
        type_env = get_base_type_env()

        self.assertEqual(try_meet(type_env, s2t("list[int]"), s2t("Sequence[int]")), s2t("list[int]"))
        self.assertEqual(try_meet(type_env, s2t("Sequence[int]"), s2t("list[int]")), s2t("list[int]"))
        self.assertEqual(try_meet(type_env, s2t("list[int]"), s2t("list[bool]")), None)
        self.assertEqual(try_meet(type_env, s2t("Sequence[int]"), s2t("Sequence[bool]")), s2t("Sequence[bool]"))

        self.assertEqual(try_meet(type_env, s2t("Sequence[int]"), s2t("int")), None)


