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
      parseTExpr("()") ==> Tuple(List.empty)
      parseTExpr("(a,)") ==> Tuple(List(Name("a")))
      parseTExpr("(a,b,)") ==> Tuple(List(Name("a"), Name("b")))
      parseTExpr("(a,b)") ==> Tuple(List(Name("a"), Name("b")))
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
  }
}
