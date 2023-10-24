package type_inference

type Rel[A,B] = Set[(A,B)]
def findSCCs[T](cs: Rel[T,T]): Set[Iterable[T]] = ???

def chooseSCCRepr(scc: Iterable[EType]): EType =
  scc.find(_.isInstanceOf[Cls]).getOrElse(scc.head)

def simplifyEq(c: (EType, EType)): Set[(TypeVar, EType)] =
  c match
    case (a, b) if a == b => Set.empty
    case (atv@TypeVar(_), bt) => Set(atv -> bt)
    case (at, btv@TypeVar(_)) => Set(btv -> at)
    case (Cls(aC, aPs), Cls(bC, bPs)) if aC == bC && aPs.size == bPs.size =>
      aPs.zip(bPs).flatMap((a, b) => simplifyEq(a, b)).toSet
    case _ => ???

def applyEqs(t: EType, eqs: Map[TypeVar, EType]): EType = t match
  case v: TypeVar => eqs.getOrElse(v, v)
  case c: Cls => c.copy(params = c.params.map(applyEqs(_, eqs)))

def applyEqs(c: (EType, EType), eqs: Map[TypeVar, EType]): (EType, EType) =
  (applyEqs(c._1, eqs), applyEqs(c._2, eqs))

def relJoin[A,B,C](ab: Rel[A,B], bc: Map[B,Set[C]]): Rel[A,C] =
  ab.flatMap((a,b) => bc.getOrElse(b, Set.empty).map(c => a -> c))

def relJoin[A,B,C](ab: Rel[A,B], bc: Rel[B,C]): Rel[A,C] =
  relJoin(ab, bc.groupMap(_._1)(_._2))

def transClosure[T](cs: Rel[T,T]): Rel[T, T] =
  val step = cs.groupMap(_._1)(_._2)
  def tc(curr: Rel[T, T]): Rel[T, T] =
    val res = relJoin(curr, step)
    if res == curr then res
    else tc(res)
  tc(cs)

def checkGroundSTC(a: Cls, b: Cls): Option[Set[(EType, EType)]] = ???
def tryJoin(a: Cls, b: Cls): Option[((Cls, Cls), Rel[EType, EType])] = ???
def tryJoin(cs: Iterable[Cls]): Option[(Set[Cls], Rel[EType, EType])] = ???
def tryMeet(cs: Iterable[Cls]): Option[(Set[Cls], Rel[EType, EType])] = ???

extension [T](s: Set[T])
  def filterIsInstance[U <: T](): Set[U] =
    s.filter(_.isInstanceOf[U]).map(_.asInstanceOf[U])

def solveConstraints(cs: Rel[EType, EType]): Rel[EType, EType] =
  val newCs = solveConstraintStep(cs)
  if newCs != cs then solveConstraints(newCs)
  else newCs

def solveConstraintStep(cs: Rel[EType, EType]): Rel[EType, EType] =
  val loops = findSCCs(cs).filter(_.size > 1)
  if !loops.isEmpty then
    val eqs = loops.flatMap(scc =>
      val repr = chooseSCCRepr(scc)
      scc.filter(_ != repr).toSet.flatMap(a => simplifyEq((a, repr)))
    ).toMap
    cs.map(c => applyEqs(c, eqs))
  else
    // DAG
    val tcCs = transClosure(cs)
    val groundCs = tcCs.filterIsInstance[(Cls, Cls)]()
    val lowerBounds = tcCs.filterIsInstance[(Cls, TypeVar)]().groupMap(_._2)(_._1)
    val upperBounds = tcCs.filterIsInstance[(TypeVar, Cls)]().groupMap(_._1)(_._2)
    val varsToVars = cs.filterIsInstance[(TypeVar, TypeVar)]().asInstanceOf[Rel[EType, EType]]
    val newGroundCs = groundCs.flatMap(c =>
      checkGroundSTC.tupled(c).get
    )
    val newUBs = upperBounds.toSet.flatMap((v, ubs) =>
      val (newUBs, newCs) = tryJoin(ubs).get
      newUBs.map(v -> _).union(newCs)
    )
    val newLBs = lowerBounds.toSet.flatMap((v, lbs) =>
      val (newLbs, newCs) = tryMeet(lbs).get
      newLbs.map(_ -> v).union(newCs)
    )
    newGroundCs.union(newUBs).union(newLBs).union(varsToVars)
