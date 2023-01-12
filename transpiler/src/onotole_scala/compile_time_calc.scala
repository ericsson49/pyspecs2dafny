package onotole_scala

import ast.{CTVal, TExpr}

type RWStrategy = TExpr => Option[TExpr]
def repeatRewrites(s: RWStrategy): RWStrategy =
  s(_).flatMap(repeatRewrites(s)(_))

def choose(cs: List[RWStrategy]): RWStrategy =
  e => cs.foldLeft[Option[TExpr]](None)((el, s) => el.orElse(s(e)))
trait NameCtx:
  def resolveCTVal(n: String): Option[CTVal]
  def newLocal(n: String): NameCtx

case class SimpleNameCtx(names: Map[String,CTVal], locals: Set[String] = Set.empty) extends NameCtx:
  def resolveCTVal(n: String): Option[CTVal] =
    if (locals.contains(n))
      None
    else
      Some(names(n))
  def newLocal(n: String) = copy(locals = locals + n)


def destruct[T <: TExpr](e: T): (List[TExpr], List[TExpr] => T) = e match
  case TExpr.Attribute(value, attr) =>
    (List(value), elems => TExpr.Attribute(elems(0), attr).asInstanceOf[T])
  case TExpr.Subscript(value, indices) =>
    (List(value) ++ indices, elems => TExpr.Subscript(elems(0), elems.slice(1, elems.size)).asInstanceOf[T])
  case TExpr.Call(func, args, kwds) =>
    (List(func) ++ args ++ kwds.map(_._2), elems => {
      val funcR = elems.head
      val (argsR, kwdsR) = elems.tail.splitAt(args.length)
      TExpr.Call(funcR, argsR, kwds.map(_._1).zip(kwdsR)).asInstanceOf[T]
    })

class CompileTimeCalc(ctx: NameCtx) {
  def eval(e: TExpr): Option[CTVal] = evalPF.applyOrElse(e, _ => None)
  val evalPF: PartialFunction[TExpr, Option[CTVal]] = check_ctv_name orElse check_ctv_attr
  val check_ctv_name: PartialFunction[TExpr, Option[CTVal]] = {
    case TExpr.Name(n) => ctx.resolveCTVal(n)
  }
  val check_ctv_attr: PartialFunction[TExpr, Option[CTVal]] = {
    case TExpr.Attribute(value, attr) =>
      eval(value) map(ctv => ctv match
        case CTVal.PkgVal(p) => eval(TExpr.Name(s"$p.$attr")).get
        case CTVal.ClassVal(c) => CTVal.FuncInst(s"$c::$attr")
        case _ => throw IllegalArgumentException()
        )
  }
}
def check_ctv(e: TExpr)(using ctx: NameCtx): Option[CTVal] = e match
  case TExpr.Name(n) => ctx.resolveCTVal(n)
  case TExpr.Attribute(value, attr) =>
    check_ctv(value) map(ctv => ctv match
      case CTVal.PkgVal(p) => check_ctv(TExpr.Name(s"$p.$attr")).get
      case CTVal.ClassVal(c) => CTVal.FuncInst(s"$c::$attr")
      case _ => throw IllegalArgumentException()
      )
  case TExpr.Subscript(value, idx) =>
    check_ctv(value) map(ctv => ctv match
      case CTVal.ClassVal(c) => CTVal.ClassVal("c"); throw IllegalArgumentException("todo")
      case _ => throw IllegalArgumentException()
      )
  case _ => None