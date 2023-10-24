package onotole_scala

import ast.Stmt
import ast.Stmt.*
import ast.TExpr
import ast.TExpr.*
import utest.*

object AnnotateTests extends TestSuite {
  override def tests = Tests {
    def annotate2(stmt: Stmt): List[Stmt] =
      eval(stmt, mkRule(annoRule))
    test("annotate while") {
      annotate2(While(Name("a"),List())) ==>
        List(While(Name("a"),List()))
      annotate2(While(Name("a"),List(Annotation("@anno")))) ==>
        List(While(Name("a"),List(),List(Annotation("@anno"))))
      annotate2(While(Name("a"),List(Annotation("@anno"), Annotation("@anno2")))) ==>
        List(While(Name("a"),List(),List(Annotation("@anno"), Annotation("@anno2"))))
      annotate2(While(Name("a"), List(Assign(Name("b"),Num(1))))) ==>
        List(While(Name("a"), List(Assign(Name("b"),Num(1)))))
      annotate2(While(Name("a"), List(Annotation("@anno"),Assign(Name("b"),Num(1))))) ==>
        List(While(Name("a"), List(Assign(Name("b"),Num(1))), List(Annotation("@anno"))))
    }
  }
}
