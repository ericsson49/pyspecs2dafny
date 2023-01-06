import ast.{StmtVisitor, StmtsVisitor}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import python3_grammar.{Python3Lexer, Python3Parser}

import java.nio.file.Paths

@main
def main(): Unit = {
  val p = Paths.get("lang_tests/test0000.py").toAbsolutePath
  val l = Python3Lexer(CharStreams.fromPath(p))
  val tokens = CommonTokenStream(l)
  val parser = Python3Parser(tokens)
  val r= StmtsVisitor.visitStmt(parser.stmt())
  println()
}