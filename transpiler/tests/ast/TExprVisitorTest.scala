package ast

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import python3_grammar.{Python3Lexer, Python3Parser}
import utest.*
import ast.TExpr.*
import util.parseTExpr

object TExprVisitorTest extends TestSuite {
  override def tests: Tests = Tests {
    test("plus tests") {
      val e1 = parseTExpr("a + b")
      assert(e1 == BinOp(Name("a"), "+", Name("b")))
      val e2 = parseTExpr("a + b + c")
      assert(e2 == BinOp(BinOp(Name("a"), "+", Name("b")), "+", Name("c")))
      val e3 = parseTExpr("(a + b) + c")
      assert(e3 == BinOp(BinOp(Name("a"), "+", Name("b")), "+", Name("c")))
    }
    test("compare") {
      parseTExpr("a == 1") ==> Compare(Name("a"), List("=="), List(Num(1)))
    }
    test("tuples") {
      parseTExpr("()") ==> PyTuple(List.empty)
      parseTExpr("(a,)") ==> PyTuple(List(Name("a")))
      parseTExpr("(a,b,)") ==> PyTuple(List(Name("a"), Name("b")))
      parseTExpr("(a,b)") ==> PyTuple(List(Name("a"), Name("b")))
    }
    test("list lit") {
      parseTExpr("[]") ==> PyList(List.empty)
      parseTExpr("[a,]") ==> PyList(List(Name("a")))
      parseTExpr("[a]") ==> PyList(List(Name("a")))
      parseTExpr("[a,b]") ==> PyList(List(Name("a"), Name("b")))
      parseTExpr("[a,b,]") ==> PyList(List(Name("a"), Name("b")))
    }
    test("dict lit") {
      parseTExpr("{}") ==> PyDict(List.empty, List.empty)
      parseTExpr("{a:b}") ==> PyDict(List(Name("a")), List(Name("b")))
      parseTExpr("{a:b,}") ==> PyDict(List(Name("a")), List(Name("b")))
      parseTExpr("{a:b,c:d}") ==> PyDict(List(Name("a"), Name("c")), List(Name("b"), Name("d")))
      parseTExpr("{a:b,c:d,}") ==> PyDict(List(Name("a"), Name("c")), List(Name("b"), Name("d")))
    }
    test("set lit") {
      parseTExpr("{a,}") ==> PySet(List(Name("a")))
      parseTExpr("{a}") ==> PySet(List(Name("a")))
      parseTExpr("{a,b}") ==> PySet(List(Name("a"), Name("b")))
      parseTExpr("{a,b,}") ==> PySet(List(Name("a"), Name("b")))
    }
    test("subscript") {
      parseTExpr("a[1]") ==> Subscript(Name("a"),List(Num(1)))
    }
    test("attribute") {
      parseTExpr("a.f") ==> Attribute(Name("a"),"f")
    }
    test("if expr") {
      parseTExpr("b if a else c") ==> IfExp(Name("a"), Name("b"), Name("c"))
    }
    test("lambda") {
      parseTExpr("lambda a: a + 1") ==> Lambda(List("a"), BinOp(Name("a"), "+", Num(1)))
      parseTExpr("lambda a, b: a + b") ==> Lambda(List("a", "b"), BinOp(Name("a"), "+", Name("b")))
      parseTExpr("map(lambda a: a + 1, coll)") ==> Call(Name("map"), List(Lambda(List("a"), BinOp(Name("a"), "+", Num(1))), Name("coll")))
    }
    test("list comp") {
      parseTExpr("[a * 2 for a in coll]") ==>
        //Call(Name("list"), List(
          GeneratorExp(BinOp(Name("a"), "*", Num(2)), Comprehension(Name("a"), Name("coll"), List()))
        //))
      parseTExpr("[a * 2 for a in coll if a > 0]") ==>
        //Call(Name("list"), List(
          GeneratorExp(BinOp(Name("a"), "*", Num(2)), Comprehension(Name("a"), Name("coll"), List(Compare(Name("a"), List(">"), List(Num(0))))))
        //))
    }
    test("set comp") {
      parseTExpr("{a * 2 for a in coll}") ==>
        Call(Name("set"), List(GeneratorExp(BinOp(Name("a"), "*", Num(2)), Comprehension(Name("a"), Name("coll"), List()))))
      parseTExpr("{a * 2 for a in coll if a > 0}") ==>
        Call(Name("set"), List(GeneratorExp(BinOp(Name("a"), "*", Num(2)), Comprehension(Name("a"), Name("coll"), List(Compare(Name("a"), List(">"), List(Num(0))))))))
    }
    test("dict comp") {
      parseTExpr("{a : a * 2 for a in coll}") ==>
        Call(Name("dict"), List(GeneratorExp(PyTuple(List(Name("a"), BinOp(Name("a"), "*", Num(2)))), Comprehension(Name("a"), Name("coll"), List()))))
      parseTExpr("{a : a * 2 for a in coll if a > 0}") ==>
        Call(Name("dict"), List(GeneratorExp(PyTuple(List(Name("a"), BinOp(Name("a"), "*", Num(2)))), Comprehension(Name("a"), Name("coll"), List(Compare(Name("a"), List(">"), List(Num(0))))))))
    }
    test("call") {
      parseTExpr("f()") ==> Call(Name("f"), List())
      parseTExpr("f(a)") ==> Call(Name("f"), List(Name("a")))
      parseTExpr("f(a, b)") ==> Call(Name("f"), List(Name("a"), Name("b")))
      parseTExpr("f(k=1)") ==> Call(Name("f"), List(), List("k" -> Num(1)))
      parseTExpr("f(k,k=1)") ==> Call(Name("f"), List(Name("k")), List("k" -> Num(1)))
    }
  }
}
