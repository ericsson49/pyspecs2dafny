package onotole_scala

import ast.{Comprehension, Stmt, TExpr}
import ast.Stmt.{Expr, *}
import ast.TExpr.*

import scala.collection.immutable.{List, Nil}


val annoRule: PartialFunction[Stmt, List[Stmt]] = {
  case While(tst, (anno@Annotation(_)) :: body, _anno) =>
    List(While(tst, body, anno = _anno ++ List(anno)))
}

val whileRule: PartialFunction[Stmt, List[Stmt]] = {
  case While(test, body, anno) if test != NameConstant(Some(true)) =>
    List(While(NameConstant(Some(true)), List(
      If(test, body, List(Break()))
    ), anno))
}

def forRule(using freeVar: (String) => String): PartialFunction[Stmt, List[Stmt]] = {
  case For(tgt, iter, body) =>
    val iterV: Name = Name(freeVar("iter"))
    List(
      Assign(iterV, Call(Name("iter"), List(iter))),
      While(Call(Attribute(iterV, "has_next"), List()),
        Assign(tgt, Call(Attribute(iterV, "next"), List())) :: body,
        List(Annotation(s"@decreases ${iterV.id}.decreases_()"))
      )
    )
}

val assertRule: PartialFunction[Stmt, List[Stmt]] = {
  case Assert(test, None) =>
    List(Expr(Call(Name("<assert>"), List(test))))
}

val assignAttrRule: PartialFunction[Stmt, List[Stmt]] = {
  case Assign(Attribute(tgtVal, attr), value) =>
    List(Expr(Call(Name(s"<set:$attr>"), List(tgtVal, value))))
}

val assignSubscrRule: PartialFunction[Stmt, List[Stmt]] = {
  case Assign(Subscript(tgtVal, indices), value) =>
    List(Expr(Call(Name(s"<set_slice>"), List(tgtVal) ++ indices ++ List(value))))
}

val simplifyExprRule: PartialFunction[TExpr, TExpr] = {
  case BoolOp("or", Nil) => NameConstant(Some(false))
  case BoolOp("or", e :: Nil) => e
  case BoolOp("or", e :: rest) => IfExp(e, NameConstant(Some(true)), BoolOp("or", rest))
  case BoolOp("and", Nil) => NameConstant(Some(true))
  case BoolOp("and", e :: Nil) => e
  case BoolOp("and", e :: rest) => IfExp(e, BoolOp("and", rest), NameConstant(Some(false)))
  case Compare(left, Nil, Nil) => left
  case Compare(_, _ :: _, Nil) => ???
  case Compare(_, Nil, _ :: _) => ???
  case Compare(left, op :: Nil, comp :: Nil) =>
    BinOp(left, op, comp)

}
def flattenStmtRule(using fv: (String) => String): Stmt => List[Stmt] =
  def evalSubExprs(e: TExpr): (List[Stmt], TExpr) =
    val (assgns, res) = flattenExprsStep(e)
    assgns.map((v, e) => Assign(Name(v), e)) -> res
  s => s match
    case AnnAssign(tgt, typ, Some(value)) =>
      List(
        AnnAssign(tgt, typ, None),
        Assign(tgt, value)
      )
    case expr@Expr(IfExp(test, body, orelse)) =>
      List(If(test, List(expr.copy(value = body)), List(expr.copy(value = orelse))))
    case assgn@Assign(_, IfExp(test, body, orelse)) =>
      List(If(test, List(assgn.copy(value = body)), List(assgn.copy(value = orelse))))
    //case AnnAssign(tgt, typ, Some(IfExp(test, body, orelse))) =>
    //  List(
    //    AnnAssign(tgt, typ, None),
    //    If(test, List(Assign(tgt, body)), List(Assign(tgt, orelse))))
    case Expr(e) =>
      val (assgns, res) = evalSubExprs(e)
      assgns ++ List(Expr(res))
    case Assign(Name(v), e) =>
      val (assgns, res) = evalSubExprs(e)
      assgns ++ List(Assign(Name(v), res))
    //case AnnAssign(Name(v), anno, Some(e)) =>
    //  val (assgns, res) = evalSubExprs(e)
    //  assgns ++ List(AnnAssign(Name(v), anno, Some(res)))
    case s@While(NameConstant(Some(true)), _, _) => List(s)
    case While(test, body, anno) =>
      List(While(NameConstant(Some(true)), List(
        If(test, body, List(Break()))
      ), anno))
    case ret@Return(Some(e)) if !e.isInstanceOf[Name] =>
      val v = Name(fv("tmp"))
      List(Assign(v, e), ret.copy(value = Some(v)))
    case stmt =>
      val (exprs, assembler) = getSubExpressions(stmt)
      val (assgns, newExprs) = exprs.map(e => introNewVar(e)).unzip
      assgns.flatten.map((v,e) => Assign(Name(v), e)) ++ List(assembler.apply(newExprs))

val generatorRule: PartialFunction[TExpr, TExpr] = {
  case GeneratorExp(e, Comprehension(Name(v), coll, Nil)) =>
    Call(Name("map"), List(Lambda(List(v), e), coll))
  case GeneratorExp(e, Comprehension(Name(v), coll, ifS :: rest)) =>
    GeneratorExp(e, Comprehension(Name(v), Call(Name("filter"), List(Lambda(List(v), ifS), coll)), rest))
}

def inlineFuncs(using fv: (String) => String): PartialFunction[TExpr, (List[Stmt], TExpr)] = {
  case Call(Name("map"), List(Lambda(List(v), e), coll), List()) =>
    val accV = Name(fv("acc"))
    List(
      Assign(accV, PyList(elems = List())),
      For(Name(v), coll, List(
        Expr(Call(Attribute(accV, "append"), List(e)))
      ))
    ) -> accV
  case Call(Name("map"), _, _) => ???
  case Call(Name("filter"), List(Lambda(List(v), e), coll), List()) =>
    val accV = Name(fv("acc"))
    List(
      Assign(accV, PyList(elems = List())),
      For(Name(v), coll, List(
        If(e, List(
          Expr(Call(Attribute(accV, "append"), List(Name(v))))
        ), List())
      ))
    ) -> accV
  case Call(Name("filter"), _, _) => ???
  case Call(Name("max"), List(coll), List("key" -> Lambda(List(v), body))) =>
    val resV = Name(fv("max_res"))
    List(
      Assign(resV, NameConstant(None)),
      For(Name(v), coll, List(
        If(Compare(resV, List("is"), List(NameConstant(None))),
          List(Assign(resV, Name(v))),
          List(
            If(Compare(Call(body, List(resV)), List(">"), List(Name(v))),
              List(Assign(resV, Name(v))),
              List()
            )
          )
        )
      ))
    ) -> resV
}
