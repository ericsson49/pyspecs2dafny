package type_inference

import ast.{Stmt, TExpr}
import ast.TExpr.*

type EType = Cls|TypeVar
case class TypeVar(tv: String)
case class Cls(cls: String, params: List[EType] = List.empty)

trait TypeLattice:
  val bottom: EType
  def join(a: EType, b: EType): EType
  def join(els: List[EType]): EType = els.foldLeft(bottom)(join(_,_))

trait TypeSystem:
  val lat: TypeLattice
  val noneT: Cls
  val boolT: Cls
  val intT: Cls
  val strT: Cls
  def asClassOpt(t: Cls, clsName: String): Option[Cls]

case class VEnv(env: Map[String, VD])
case class VD(v: String, anno: Option[Cls])
def getVarDefs(cs: List[Stmt]): Set[(VD, Set[(TExpr, VEnv)])] = ???


def deriveConstraints(expected: EType, e: TExpr, env: Map[String, EType]): Set[(EType, EType)] = ???


def inferTypes(cs: List[Stmt]) =
  val varDefs = getVarDefs(cs)
  def mkNewTV(): String = ???
  val varTypes = varDefs.map(vd =>
    val vDef = vd._1
    val t = if vDef.anno.isDefined then
      vDef.anno.get
    else
      TypeVar(mkNewTV())
    vDef -> t.asInstanceOf[EType]
  ).toMap
  val newConstrs = varDefs.flatMap((vd, defs) =>
    val et = varTypes.get(vd).get
    defs.flatMap((e, env) => deriveConstraints(et, e, env.env.map((v, vd) => v -> varTypes.get(vd).get)))
  )


