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

def getSubExpressions(s: Stmt): (List[TExpr], List[TExpr] => Stmt) =
  s match
    case Assert(test, None) =>
      List(test) -> (elems => Assert(elems(0)))
    case Assign(tgt@Name(_), value) =>
      List(value) -> (elems => Assign(tgt, elems(0)))
    case Assign(Subscript(tgtVal, idx), value) =>
      List(tgtVal) ++ idx ++ List(value) -> (elems => Assign(Subscript(elems(0), elems.slice(1, elems.size-1)), elems(elems.size-1)))
    case AnnAssign(tgt@Name(_), anno, Some(value)) =>
      List(value) -> (elems => AnnAssign(tgt, anno, Some(elems(0))))
    case Expr(expr) =>
      List(expr) -> (elems => Expr(elems(0)))
    case If(test, body, orelse) =>
      List(test) -> (elems => If(elems(0), body, orelse))
    case While(test, body, anno) =>
      List(test) -> (elems => While(elems(0), body, anno))
    case _ => List() -> (_ => s)
def rewriteInStmts2(transform: TExpr => Option[TExpr])(s: Stmt): Stmt =
  import rewriting.StmtRewriting
  val (stmts, stmtAssembler) = StmtRewriting.destruct(s)
  val s2 = stmtAssembler.apply(stmts.map(rewriteInStmts2(transform)))
  val (subExprs, assembler) = getSubExpressions(s2)
  assembler.apply(subExprs.map(e => transform.apply(e).getOrElse(e)))

