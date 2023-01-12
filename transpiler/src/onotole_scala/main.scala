package onotole_scala

import ast.{Stmt, TExpr, TopLevelVisitor}
import ast.Stmt.*
import ast.TExpr.*
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import python3_grammar.{Python3Lexer, Python3Parser}
import util.*

import java.nio.file.Paths

@main def testtt() =
  val p1 = parser(CharStreams.fromPath(Paths.get("lang_tests", "test_if_expr.py")))
  val tlDefs = TopLevelVisitor.visitFile_input(p1.file_input())
  val annotated = tlDefs.map(annotateTopLevel(_))
  var num = 0
  val freshV = () =>
    val vn = "tmp_" + num
    num += 1
    vn
  val tr = annotated.map(semTL(_)(using freshV))
  val vd = tr.map(inferVarDeclsTL(_))
  val gen = CodeGen("")
  vd.foreach(genDafnyTL(_)(using gen))

