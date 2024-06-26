import unittest

from pynotole.type_parser import *
from utils import *


class TypeParserTest(unittest.TestCase):
    def test(self):
        name_ctx = TypeParseCtx(mk_test_name_ctx(
            pkgs=["a", "a.b"],
            classes=["A", "a.A", "a.b.A", "B", "C", "Cls",
                     ("List", "py$List"), ("Vector", "py$Vector"),
                     ("ByteList", "py$ByteList"), ("ByteVector", "py$ByteVector"),
                     ("Bitlist", "py$Bitlist"), ("Bitvector", "py$Bitvector"),],
        ).names)
        def helper(s: str, type_vars = ()):
            node = ast.parse(s).body[0].value
            return parse_type2(node, name_ctx, type_vars)
        self.assertEqual(helper('None'), Cls("NoneType"))
        self.assertEqual(helper('A'), Cls("A"))
        self.assertEqual(helper('a.A'), Cls("a.A"))
        self.assertEqual(helper('a.b.A'), Cls("a.b.A"))
        self.assertEqual(helper('A[B]'), Cls("A", (Cls("B"),)))
        self.assertEqual(helper('A[B,C]'), Cls("A", (Cls("B"),Cls("C"))))
        self.assertEqual(helper('List[A,1*2]'), Cls("py$List", (Cls("A"),)))
        self.assertEqual(helper('Vector[A,1*2]'), Cls("py$Vector", (Cls("A"),)))
        self.assertEqual(helper('ByteList[1*2]'), Cls("py$ByteList", ()))
        self.assertEqual(helper('ByteVector[1*2]'), Cls("py$ByteVector", ()))
        self.assertEqual(helper('Bitlist[1*2]'), Cls("py$Bitlist", ()))
        self.assertEqual(helper('Bitvector[1*2]'), Cls("py$Bitvector", ()))

        self.assertEqual(helper("T", {"T"}), "T")
        self.assertEqual(helper("Cls[T]", {"T"}), Cls("Cls", ("T",)))

    def test_parse_typelib_func(self):
        resolver = TypeParseCtx({'Collection': ClassInfo('Collection'), 'int': ClassInfo('int')})
        sig = parse_type_lib(resolver, "", "def len[T](coll: Collection[T]) -> int: ...")[0]
        fn = sig.name
        self.assertEqual(fn, "len")
        self.assertEqual(sig, FuncSignature("len", ["T"], [("coll", Cls("Collection", ("T",)), None)], Cls("int")))

        self.assertEqual(sig.matches_call(1, set()), True)
        self.assertEqual(sig.matches_call(0, {"coll"}), True)
        self.assertEqual(sig.matches_call(0, set()), False)
        self.assertEqual(sig.matches_call(2, set()), False)
        self.assertEqual(sig.matches_call(1, {"wrong"}), False)
        self.assertEqual(sig.matches_call(0, {"wrong"}), False)
        self.assertEqual(sig.matches_call(1, {"coll"}), False)
        self.assertEqual(sig.matches_call(1, {"coll", "wrong"}), False)
        self.assertEqual(sig.matches_call(0, {"coll", "wrong"}), False)

    def test_parse_typelib_class(self):
        parser_ctx = TypeParseCtx({'object': ClassInfo('object')})
        cls_templ = parse_type_lib(parser_ctx,"","""
class int(object):
    def __add__(self, other: int) -> int: ...
        """)[0]
        cn = cls_templ.name
        self.assertEqual(cn, 'int')
        self.assertIn('__add__', cls_templ.attrs)
        self.assertEqual(cls_templ.attrs['__add__'], FuncSignature(None, [], [('self', Cls('int'), None), ('other', Cls('int'), None)], Cls("int")))

    def test_parse_typelib_generic_class(self):
        resolver = TypeParseCtx({'object': ClassInfo('object'), 'int': ClassInfo('int')})
        cls_templ = parse_type_lib(resolver,"","""
class A[T](object):
    a: int
    f: T
    def func(self, a: T) -> T: ...
        """)[0]
        cn = cls_templ.name
        self.assertEqual(cn, 'A')
        self.assertIn('a', cls_templ.attrs)
        self.assertEqual(cls_templ.attrs['a'], Cls('int'))
        self.assertIn('f', cls_templ.attrs)
        self.assertEqual(cls_templ.attrs['f'], 'T')
        self.assertIn('func', cls_templ.attrs)
        self.assertEqual(cls_templ.attrs['func'], FuncSignature(None, [], [('self', Cls('A',('T',)), None), ('a', 'T', None)], 'T'))

    def test_base_type_env(self):
        get_base_type_env()

    def test_std_ctx(self):
        ctx = mk_std_parser_ctx()

        def helper(s):
            node = ast.parse(s).body[0].value
            return parse_type2(node, ctx, ())

        self.assertEqual(Cls('NoneType'), helper('None'))
        self.assertEqual(Cls('py$int'), helper('int'))
        self.assertEqual(Cls('py$str'), helper('str'))
        self.assertEqual(Cls('py$list', (Cls('py$int'),)), helper('list[int]'))


