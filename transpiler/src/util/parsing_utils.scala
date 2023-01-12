package util

import ast.{Stmt, StmtVisitor, StmtsVisitor, TExpr, TExprVisitor}
import org.antlr.v4.runtime.{CharStreams, CharStream, CommonTokenStream}
import python3_grammar.{Python3Lexer, Python3Parser}


def parser(stream: CharStream) = {
  val lexer = Python3Lexer(stream)
  val tokens = CommonTokenStream(lexer)
  Python3Parser(tokens)
}
def parser(s: String): Python3Parser =
  parser(CharStreams.fromString(s))

def parseTExpr(s: String): TExpr =
  TExprVisitor.visit(parser(s).test())

def parseSmallStmt(s: String): Stmt =
  StmtVisitor.visit(parser(s).small_stmt())

def parseCompoundStmt(s: String): Stmt =
  StmtVisitor.visit(parser(s).compound_stmt())

def parseStmt(p: Python3Parser) = {
  StmtsVisitor.visitStmt(p.stmt())
}
def parseStmt(s: String): List[Stmt] = {
  parseStmt(parser(s))
}
def parseSuite(p: Python3Parser) = {
  StmtsVisitor.visit(p.suite())
}
def parseSuite(s: String): List[Stmt] = {
  parseSuite(parser(s))
}