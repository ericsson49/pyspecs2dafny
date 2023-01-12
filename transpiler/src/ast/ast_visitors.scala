package ast

import python3_grammar.*
import ast.TExpr
import ast.Trailer.{AttributeTrailer, CallTrailer, SubscriptTrailer}

import scala.jdk.CollectionConverters.*

def toList[T](l: java.util.List[T]): List[T] = l.asScala.toList

def getElems(ctx: Python3Parser.Test_listContext): List[Python3Parser.TestContext] =
  if ctx == null then List.empty else toList(ctx.elems)

object TExprVisitor extends Python3BaseVisitor[TExpr] {
  override def visitIfExp(ctx: Python3Parser.IfExpContext): TExpr =
    TExpr.IfExp(visit(ctx.tst), visit(ctx.body), visit(ctx.orelse))

  override def visitLambda(ctx: Python3Parser.LambdaContext): TExpr =
    ???

  override def visitTExpr(ctx: Python3Parser.TExprContext): TExpr =
    visit(ctx.expr())

  override def visitOrExpr(ctx: Python3Parser.OrExprContext): TExpr =
    TExpr.BoolOp("or", toList(ctx.exprs).map(visit(_)))

  override def visitAndExpr(ctx: Python3Parser.AndExprContext): TExpr =
    TExpr.BoolOp("and", toList(ctx.exprs).map(visit(_)))

  override def visitNotExpr(ctx: Python3Parser.NotExprContext): TExpr =
    TExpr.UnaryOp("not", visitChildren(ctx.operand))

  override def visitCompare(ctx: Python3Parser.CompareContext): TExpr =
    TExpr.Compare(visit(ctx.left), toList(ctx.ops).map(_.getText), toList(ctx.comparators).map(visit(_)))

  override def visitStarExpr(ctx: Python3Parser.StarExprContext): TExpr = ???

  override def visitBitOr(ctx: Python3Parser.BitOrContext): TExpr =
    TExpr.BinOp(visit(ctx.left), ctx.op.getText, visit(ctx.right))

  override def visitBitXor(ctx: Python3Parser.BitXorContext): TExpr =
    TExpr.BinOp(visit(ctx.left), ctx.op.getText, visit(ctx.right))

  override def visitBitAnd(ctx: Python3Parser.BitAndContext): TExpr =
    TExpr.BinOp(visit(ctx.left), ctx.op.getText, visit(ctx.right))

  override def visitShiftExpr(ctx: Python3Parser.ShiftExprContext): TExpr =
    TExpr.BinOp(visit(ctx.left), ctx.op.getText, visit(ctx.right))

  override def visitPlusMinus(ctx: Python3Parser.PlusMinusContext): TExpr =
    TExpr.BinOp(visit(ctx.left), ctx.op.getText, visit(ctx.right))

  override def visitTerm(ctx: Python3Parser.TermContext): TExpr =
    TExpr.BinOp(visit(ctx.left), ctx.op.getText, visit(ctx.right))

  override def visitFactor(ctx: Python3Parser.FactorContext): TExpr =
    TExpr.UnaryOp(ctx.op.getText, visit(ctx.operand))

  override def visitPower(ctx: Python3Parser.PowerContext): TExpr =
    TExpr.BinOp(visitAtom_expr(ctx.left), ctx.op.getText, visit(ctx.right))

  override def visitAtom_expr(ctx: Python3Parser.Atom_exprContext): TExpr =
    val trailers = toList(ctx.trailer()).map(TrailerVisitor.visit(_))
    val atom = visit(ctx.atom())
    trailers.foldLeft(atom)((a, t) => t match
      case CallTrailer(args) => TExpr.Call(a, args)
      case AttributeTrailer(attr) => TExpr.Attribute(a, attr)
      case SubscriptTrailer(index) => TExpr.Subscript(a, index)
    )

  override def visitParensExpr(ctx: Python3Parser.ParensExprContext): TExpr =
    visit(ctx.test())

  override def visitTuple(ctx: Python3Parser.TupleContext): TExpr =
    TExpr.Tuple(getElems(ctx.test_list()).map(visit(_)))

  override def visitListLit(ctx: Python3Parser.ListLitContext): TExpr =
    TExpr.PyList(getElems(ctx.test_list()).map(visit(_)))

  override def visitDictLit(ctx: Python3Parser.DictLitContext): TExpr =
    val keys = toList(ctx.keys).map(visit(_))
    val values = toList(ctx.values).map(visit(_))
    TExpr.PyDict(keys, values)

  override def visitSetLit(ctx: Python3Parser.SetLitContext): TExpr =
    TExpr.PySet(getElems(ctx.test_list()).map(visit(_)))

  override def visitName(ctx: Python3Parser.NameContext): TExpr =
    TExpr.Name(ctx.id.getText)

  override def visitNumber(ctx: Python3Parser.NumberContext): TExpr =
    TExpr.Num(ctx.n.getText.toInt)

  override def visitStr(ctx: Python3Parser.StrContext): TExpr = ???

  override def visitEllipsis(ctx: Python3Parser.EllipsisContext): TExpr = ???

  override def visitNone(ctx: Python3Parser.NoneContext): TExpr = TExpr.NameConstant(None)

  override def visitTrue(ctx: Python3Parser.TrueContext): TExpr = TExpr.NameConstant(Some(true))

  override def visitFalse(ctx: Python3Parser.FalseContext): TExpr = TExpr.NameConstant(Some(false))
}

enum Trailer:
  case CallTrailer(args: List[TExpr])
  case SubscriptTrailer(indices: List[TExpr])
  case AttributeTrailer(attr: String)

object TrailerVisitor extends Python3BaseVisitor[Trailer] {
  override def visitCallTrailer(ctx: Python3Parser.CallTrailerContext): Trailer =
    val args = Option(ctx.arglist()).map(args => toList(args.argument()).map(TExprVisitor.visit(_))).getOrElse(List.empty)
    CallTrailer(args)

  override def visitSubscrTrailer(ctx: Python3Parser.SubscrTrailerContext): Trailer =
    val subscripts = toList(ctx.subscriptlist().subscript())
    val indices = subscripts.map(ctx =>
      val exprs = ctx.expr()
      if exprs.size() != 1 then ???
      TExprVisitor.visit(exprs.get(0))
    )
    SubscriptTrailer(indices)

  override def visitAttrTrailer(ctx: Python3Parser.AttrTrailerContext): Trailer =
    AttributeTrailer(ctx.NAME().getText)
}

object StmtVisitor extends Python3BaseVisitor[Stmt] {
  override def visitPass(ctx: Python3Parser.PassContext): Stmt =
    Stmt.Pass()

  override def visitBreak(ctx: Python3Parser.BreakContext): Stmt =
    Stmt.Break()

  override def visitContinue(ctx: Python3Parser.ContinueContext): Stmt =
    Stmt.Continue()

  override def visitExprStmt(ctx: Python3Parser.ExprStmtContext): Stmt =
    val exprs = toList(ctx.test_list().elems).map(TExprVisitor.visit(_))
    if exprs.length != 1 then ???
    Stmt.Expr(exprs(0))

  override def visitAssign(ctx: Python3Parser.AssignContext): Stmt =
    val tgts = toList(ctx.target.elems).map(TExprVisitor.visit(_))
    val values = toList(ctx.value.elems).map(TExprVisitor.visit(_))
    if tgts.length != 1 || values.length != 1 then ???
    Stmt.Assign(tgts(0), values(0))

  override def visitAnnAssign(ctx: Python3Parser.AnnAssignContext): Stmt =
    val tgts = toList(ctx.target.elems).map(TExprVisitor.visit(_))
    val tgtType = TExprVisitor.visit(ctx.`type`)
    val value = Option(ctx.value).map(getElems(_).map(TExprVisitor.visit(_)))
    if tgts.length != 1 || value.isDefined && value.get.length != 1 then ???
    Stmt.AnnAssign(tgts(0), tgtType, value.map(vs => vs(0)))

  override def visitAssert(ctx: Python3Parser.AssertContext): Stmt =
    val test = TExprVisitor.visit(ctx.value)
    val msg = Option(ctx.msg).map(TExprVisitor.visit(_))
    Stmt.Assert(test, msg)


  override def visitIf(ctx: Python3Parser.IfContext): Stmt =
    val test = TExprVisitor.visit(ctx.tst)
    val body = StmtsVisitor.visit(ctx.body)
    val orelse = Option(ctx.orelse).map(StmtsVisitor.visit(_)).getOrElse(List.empty)
    Stmt.If(test, body, orelse)

  override def visitWhile(ctx: Python3Parser.WhileContext): Stmt =
    val test = TExprVisitor.visit(ctx.tst)
    val body = StmtsVisitor.visit(ctx.body)
    Stmt.While(test, body)

  override def visitFor(ctx: Python3Parser.ForContext): Stmt =
    val target = TExprVisitor.visit(ctx.tgt)
    val iter = TExprVisitor.visit(ctx.iter)
    val body = StmtsVisitor.visit(ctx.body)
    Stmt.For(target, iter, body)

  override def visitFunc_def(ctx: Python3Parser.Func_defContext): Stmt =
    val name = ctx.name.getText
    val body = StmtsVisitor.visit(ctx.suite())
    Stmt.FunctionDef(name, body)

  override def visitClass_def(ctx: Python3Parser.Class_defContext): Stmt =
    val name = ctx.name.getText
    val bases = Option(ctx.type_expr_list()).map(bs =>
      toList(bs.type_expr()).map(TExprVisitor.visit(_))).getOrElse(List.empty)
    if bases.length > 1 then ???
    val base = if bases.length == 1 then bases(0) else TExpr.Name("object")
    val fields = ClassFields.visit(ctx.class_suite().class_fields())
    Stmt.ClassDef(name, base, fields)

  override def visitAnnotation(ctx: Python3Parser.AnnotationContext): Stmt =
    Stmt.Annotation(ctx.ANNO_COMMENT().getText.substring(1))
}

object ClassFields extends Python3BaseVisitor[List[(String, TExpr, Option[TExpr])]] {
  override def visitEmptyClass(ctx: Python3Parser.EmptyClassContext): List[(String, TExpr, Option[TExpr])] =
    List()

  override def visitFields(ctx: Python3Parser.FieldsContext): List[(String, TExpr, Option[TExpr])] =
    toList(ctx.field_def()).map(fCtx =>
      val init = Option(fCtx.test()).map(TExprVisitor.visit(_))
      (fCtx.NAME().getText, TExprVisitor.visitType_expr(fCtx.type_expr()), init)
    )
}

object StmtsVisitor extends Python3BaseVisitor[List[Stmt]] {
  override def visitSimple_stmt(ctx: Python3Parser.Simple_stmtContext): List[Stmt] =
    toList(ctx.small_stmt()).map(StmtVisitor.visit(_))

  override def visitStmt(ctx: Python3Parser.StmtContext): List[Stmt] =
    if ctx.simple_stmt() != null then
      visitSimple_stmt(ctx.simple_stmt())
    else if ctx.compound_stmt() != null then
      List(StmtVisitor.visit(ctx.compound_stmt()))
    else if ctx.annotation() != null then
      List(StmtVisitor.visitAnnotation(ctx.annotation()))
    else ???

  override def visitBlock(ctx: Python3Parser.BlockContext): List[Stmt] =
    toList(ctx.body).flatMap(visit(_))
}

object TopLevelVisitor extends Python3BaseVisitor[List[ast.TopLevelDef]] {
  override def visitFile_input(ctx: Python3Parser.File_inputContext): List[TopLevelDef] =
    val tlDefs = toList(ctx.top_level_stmt()).map(StmtVisitor.visit(_))
    tlDefs.map(_ match
      case f: Stmt.FunctionDef => f.asInstanceOf[TopLevelDef]
      case c: Stmt.ClassDef => c.asInstanceOf[TopLevelDef]
    )
}