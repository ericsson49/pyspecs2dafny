package rewriting

import ast.{Stmt, TExpr}
import ast.Stmt.*
import ast.TExpr.*
import onotole_scala.destr

object StmtRewriting extends Rewriting[Stmt] {
  override def destruct(t: Stmt): (List[Stmt], List[Stmt] => Stmt) = t match
    case If(test, body, orelse) =>
      body ++ orelse -> (stmts => If(test, stmts.slice(0, body.size), stmts.slice(body.size, stmts.size)))
    case While(test, body, anno) =>
      body -> (stmts => While(test, stmts, anno))
    case For(tgt, iter, body) =>
      body -> (stmts => For(tgt, iter, stmts))
    case _ => List() -> (_ => t)
}

object ExprRewriting extends Rewriting[TExpr] {
  override def destruct(t: TExpr): (List[TExpr], List[TExpr] => TExpr) =
    destr(t)
}

object StmtsRewriting extends Rewriting[List[Stmt]] {
  override def destruct(t: List[Stmt]): (List[List[Stmt]], List[List[Stmt]] => List[Stmt]) =
    t.map(List(_)) -> (t => t.flatten)

  def rulee(f: PartialFunction[Stmt, List[Stmt]]): Strategy =
    def tr(cs: List[Stmt]): Option[List[Stmt]] = cs match
      case s :: Nil =>
        if f.isDefinedAt(s) then Some(f.apply(s))
        else None
    some(tr(_))
}