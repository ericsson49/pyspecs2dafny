package ast

import ast.Stmt.*
import ast.TExpr.{Name, *}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream, Token}
import python3_grammar.Python3Lexer
import utest.*
import util.{parseCompoundStmt, parseSmallStmt, parseStmt, parseSuite, parser}

object StmtVisitorTest extends TestSuite {
  override def tests: Tests = Tests {
    test("expr stmt") {
      parseSmallStmt("a") ==> Expr(Name("a"))
    }
    test("assign") {
      parseSmallStmt("a = b") ==> Assign(Name("a"), Name("b"))
    }
    test("ann assign") {
      parseSmallStmt("a: int") ==> AnnAssign(Name("a"), Name("int"), None)
      parseSmallStmt("a: int = b") ==> AnnAssign(Name("a"), Name("int"), Some(Name("b")))
      parseSmallStmt("a: PyList[int]") ==> AnnAssign(Name("a"), Subscript(Name("PyList"),List(Name("int"))), None)
    }
    test("assert") {
      parseSmallStmt("assert a == 1") ==> Assert(Compare(Name("a"), List("=="), List(Num(1))))
      parseSmallStmt("assert a, b") ==> Assert(Name("a"), Some(Name("b")))
      parseSmallStmt("assert not c") ==> Assert(UnaryOp("not", Name("c")))
    }
    test("simple stmt") {
      parseStmt("a\n") ==> List(Expr(Name("a")))
      parseStmt("assert b\n") ==> List(Assert(Name("b")))
      parseStmt("assert b;\n") ==> List(Assert(Name("b")))
      parseStmt("assert a; assert b\n") ==> List(Assert(Name("a")),Assert(Name("b")))
      parseSuite("assert a; assert b;\n") ==> List(Assert(Name("a")),Assert(Name("b")))
    }
    test("block stmts") {
      parseSuite("""
                |  assert b
                |""".stripMargin) ==> List(Assert((Name("b"))))
      parseSuite(
        """
          |    assert b
          |    a
          |""".stripMargin) ==> List(Assert((Name("b"))), Expr(Name("a")))
      parseSuite(
        """
          |    assert b
          |    f()
          |""".stripMargin) ==> List(Assert((Name("b"))), Expr(Call(Name("f"), List())))
      parseSuite("""
                |  a = 1
                |  assert a == 1
                |
                |""".stripMargin) ==> List(Assign(Name("a"), Num(1)), Assert(Compare(Name("a"), List("=="), List(Num(1)))))
    }
    // compound stmts
    test("if then else") {
      parseCompoundStmt(
        """if a:
          |  assert a != 0
          |""".stripMargin) ==> If(Name("a"), List(Assert(Compare(Name("a"), "!=" :: Nil, Num(0) :: Nil))), List())
      parseCompoundStmt(
        """if a:
          |  assert a != 0
          |else:
          |  assert a == 0
          |""".stripMargin) ==>
              If(Name("a"),
                List(Assert(Compare(Name("a"), List("!="), List(Num(0))))),
                List(Assert(Compare(Name("a"), List("=="), List(Num(0))))))
      parseCompoundStmt(
        """if a:
          |  assert a != 0
          |elif b:
          |  assert b != 0
          |else:
          |  assert a == 0
          |""".stripMargin
      ) ==>
        If(Name("a"),
          List(Assert(Compare(Name("a"), List("!="), List(Num(0))))),
          List(
            If(Name("b"),
              List(Assert(Compare(Name("b"), List("!="), List(Num(0))))),
              List(Assert(Compare(Name("a"), List("=="), List(Num(0)))))
            )
          )
        )
      parseCompoundStmt(
        """if a:
          |  assert a != 0
          |elif b:
          |  assert b != 0
          |elif c:
          |  assert c != 0
          |else:
          |  assert a == 0
          |""".stripMargin
      ) ==>
        If(Name("a"),
          List(Assert(Compare(Name("a"), List("!="), List(Num(0))))),
          List(
            If(Name("b"),
              List(Assert(Compare(Name("b"), List("!="), List(Num(0))))),
              List(If(Name("c"),
                List(Assert(Compare(Name("c"), List("!="), List(Num(0))))),
                List(Assert(Compare(Name("a"), List("=="), List(Num(0))))))
              )
            )
          )
        )
    }
    test("while") {
      val expected = While(Name("a"), List(Assign(Name("a"), BinOp(Name("a"), "-", Num(1)))))
      parseCompoundStmt(
        """while a:
          |  a = a - 1
          |""".stripMargin
      ) ==> expected
      parseCompoundStmt("while a: a = a - 1\n") ==> expected
      parseCompoundStmt("while a: a = a - 1;\n") ==> expected
      parseCompoundStmt(
        """while a:
          |  #@ttt
          |  a = a - 1
          |""".stripMargin) ==> While(Name("a"),List(Annotation("@ttt"), Assign(Name("a"),BinOp(Name("a"),"-",Num(1)))))
    }
    test("for") {
      val expected = For(Name("a"), Name("coll"), List(Assert(Name("a"))))
      parseCompoundStmt(
        """for a in coll:
          |  assert a
          |""".stripMargin
      ) ==> expected
      parseCompoundStmt("for a in coll: assert a\n") ==> expected
      parseCompoundStmt("for a in coll: assert a;\n") ==> expected
    }
    test("return") {
      parseSmallStmt("return") ==> Return(None)
      parseSmallStmt("return 1") ==> Return(Some(Num(1)))
      parseSmallStmt("return a") ==> Return(Some(Name("a")))
    }
    test("function def") {
      val p0 =
        """def test() -> None:
          |  assert a
          |""".stripMargin
      val expected0 = FunctionDef("test", List(), List(Assert(Name("a"))), None)
      parseCompoundStmt(p0) ==> expected0
      parseStmt(p0) ==> List(expected0)
      val p1 =
        """
          |def test() -> int:
          |  return 1
          |""".stripMargin
      val expected1 = FunctionDef("test", List(), List(Return(Some(Num(1)))), None)
      parseCompoundStmt(p1) ==> expected1
    }
    test("class def") {
      parseCompoundStmt(
        """class Test():
          |  pass
          |""".stripMargin) ==> ClassDef("Test", Name("object"), List())
      parseCompoundStmt(
        """class Test():
          |  a: int
          |  b: List[int] = []
          |""".stripMargin) ==>
        ClassDef("Test", Name("object"), List(
          ("a", Name("int"), None),
          ("b", Subscript(Name("List"), List(Name("int"))), Some(PyList(List())))
        ))
    }
    test("annotation") {
      StmtVisitor.visitAnnotation(parser("#@anno\n").annotation()) ==> Annotation("@anno")
      parseSuite(
        """
          |  #@anno
          |""".stripMargin) ==> List(Annotation("@anno"))
      parseCompoundStmt(
        """while a:
          |  #@rrr
          |  a = a - 1
          |""".stripMargin).asInstanceOf[While].body ==> List(Annotation("@rrr"),Assign(Name("a"),BinOp(Name("a"),"-",Num(1))))
    }
  }
}
