package onotole_scala

import ast.{Stmt, TExpr}
import ast.Stmt._
import ast.TExpr._


def convertOp(op: String): TExpr = Name(s"<$op>")

val simplifyStep: PartialFunction[TExpr,TExpr] =
  case UnaryOp(op, operand) => Call(convertOp(op), List(operand))
  case BinOp(left, op, right) => Call(convertOp(op), List(left, right))
  case Compare(left, op :: Nil, right :: Nil) => Call(convertOp(op), List(left, right))
  case _: Compare => ??? // todo

def simplify(e: TExpr): TExpr =
  val e2 = simplifyStep.applyOrElse(e, _ => e)
  val (se, assembler) = destr(e2)
  assembler(se.map(simplify(_)))

def rewriteInStmts(s: Stmt)(using transform: TExpr => TExpr): Stmt = s match
  case vd: VarDecl => vd.copy(value = vd.value.map(transform))
  case Expr(e) => Expr(transform(e))
  case Assign(tgt, value) => Assign(transform(tgt), transform(value))
  case Assert(test, msg) => Assert(transform(test), msg.map(transform))
  case If(test, body, orelse) =>
    If(transform(test), body.map(rewriteInStmts(_)), orelse.map(rewriteInStmts(_)))
  case While(test, body, anno) =>
    While(transform(test), body.map(rewriteInStmts(_)), anno)
  case Return(value) => Return(value.map(transform))
  case _: (Pass|Break|Continue) => s
