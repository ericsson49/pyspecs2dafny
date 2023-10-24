package ast

import ast.Stmt.Annotation

sealed abstract class PyAstNode

enum CTVal:
  case PkgVal(p: String)
  case ClassVal(c: String)
  case FuncInst(n: String)

enum Stmt extends PyAstNode:
  case Annotation(s: String)
  case VarDecl(v: String, anno: Option[TExpr], value: Option[TExpr])

  case Expr(value: TExpr)
  case Assign(target: TExpr, value: TExpr)
  case AnnAssign(target: TExpr, anno: TExpr, value: Option[TExpr] = None)

  case Assert(test: TExpr, msg: Option[TExpr] = None)

  case If(test: TExpr, body: List[Stmt], orelse: List[Stmt])
  case While(test: TExpr, body: List[Stmt], anno: List[Annotation] = List.empty)
  case For(target: TExpr, iter: TExpr, body: List[Stmt])
  case Return(value: Option[TExpr])
  case Pass()
  case Break()
  case Continue()

  case FunctionDef(name: String, args: List[(String, TExpr)],
                   body: List[Stmt], returns: Option[TExpr], anno: List[Annotation] = List())
  case ClassDef(name: String, base: TExpr, fields: List[(String, TExpr, Option[TExpr])])

type TopLevelDef = Stmt.FunctionDef | Stmt.ClassDef

case class Comprehension(tgt: TExpr, iter: TExpr, ifs: List[TExpr])
enum TExpr extends PyAstNode:
  case NameConstant(c: Option[Boolean])
  case Num(n: Int)
  case Str(s: String)
  case Name(id: String)
  case Attribute(value: TExpr, attr: String)
  case Subscript(value: TExpr, indices: List[TExpr])
  case Call(func: TExpr, args: List[TExpr], kwds: List[(String, TExpr)] = List.empty)
  case PyTuple(elems: List[TExpr])
  case PyList(elems: List[TExpr])
  case PyDict(keys: List[TExpr], values: List[TExpr])
  case PySet(elems: List[TExpr])
  case BoolOp(op: String, values: List[TExpr])
  case BinOp(left: TExpr, op: String, right: TExpr)
  case UnaryOp(op: String, operand: TExpr)
  case Compare(left: TExpr, ops: List[String], comparators: List[TExpr])
  case IfExp(test: TExpr, body: TExpr, orelse: TExpr)
  case GeneratorExp(e: TExpr, gen: Comprehension)
  case Lambda(args: List[String], body: TExpr)
  case Let(binders: List[(String, TExpr)], value: TExpr)