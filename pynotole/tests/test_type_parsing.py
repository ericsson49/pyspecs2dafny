import unittest

from pynotole.type_parser import *


class TypeParserTest(unittest.TestCase):
    def test(self):
        def helper(s: str):
            return str_to_type(s)
        self.assertEqual(helper('None'), Cls("NoneType"))
        self.assertEqual(helper('A'), Cls("A"))
        self.assertEqual(helper('a.A'), Cls("a.A"))
        self.assertEqual(helper('a.b.A'), Cls("a.b.A"))
        self.assertEqual(helper('A[B]'), Cls("A", (Cls("B"),)))
        self.assertEqual(helper('A[B,C]'), Cls("A", (Cls("B"),Cls("C"))))
        self.assertEqual(helper('List[A,1*2]'), Cls("List", (Cls("A"),)))
        self.assertEqual(helper('Vector[A,1*2]'), Cls("Vector", (Cls("A"),)))
        self.assertEqual(helper('Bytelist[1*2]'), Cls("Bytelist", ()))
        self.assertEqual(helper('Bytevector[1*2]'), Cls("Bytevector", ()))
        self.assertEqual(helper('Bitlist[1*2]'), Cls("Bitlist", ()))
        self.assertEqual(helper('Bitvector[1*2]'), Cls("Bitvector", ()))

        self.assertEqual(str_to_type("T", {"T"}), "T")
        self.assertEqual(str_to_type("Cls[T]", {"T"}), Cls("Cls", ("T",)))

    def test_parse_typelib_func(self):
        fn, sig = parse_type_lib("def len[T](coll: Collection[T]) -> int: ...")[0]
        self.assertEqual(fn, "len")
        self.assertEqual(sig, FuncSignature(["T"], [("coll", Cls("Collection", ("T",)), None)], Cls("int")))

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
        cn, cls_templ = parse_type_lib("""
class int(object):
    def __add__(self, other: int) -> int: ...
        """)[0]
        self.assertEqual(cn, 'int')
        self.assertIn('__add__', cls_templ.attrs)
        self.assertEqual(cls_templ.attrs['__add__'], FuncSignature([], [('self', Cls('int'), None), ('other', Cls('int'), None)], Cls("int")))

    def test_parse_typelib_generic_class(self):
        cn, cls_templ = parse_type_lib("""
class A[T](object):
    a: int
    f: T
    def func(self, a: T) -> T: ...
        """)[0]
        self.assertEqual(cn, 'A')
        self.assertIn('a', cls_templ.attrs)
        self.assertEqual(cls_templ.attrs['a'], Cls('int'))
        self.assertIn('f', cls_templ.attrs)
        self.assertEqual(cls_templ.attrs['f'], 'T')
        self.assertIn('func', cls_templ.attrs)
        self.assertEqual(cls_templ.attrs['func'], FuncSignature([], [('self', Cls('A',('T',)), None), ('a', 'T', None)], 'T'))

    def test_base_type_env(self):
        get_base_type_env()

