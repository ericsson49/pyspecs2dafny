package rewriting


trait Rewriting[T] {
  type Strategy = T => Option[T]

  def destruct(t: T): (List[T], (List[T] => T))

  val id: Strategy = t => Some(t)
  def term(t: T): Strategy = _ => Some(t)

  def seq(s1: => Strategy, s2: => Strategy): Strategy =
    t =>
      s1.apply(t).flatMap(s2.apply(_))

  def choice(s1: => Strategy, s2: => Strategy): Strategy =
    t =>
      s1.apply(t).orElse(s2.apply(t))

  def `try`(s: => Strategy): Strategy =
    choice(s, id)

  def repeat(s: => Strategy): Strategy =
    `try`(seq(s, repeat(s)))

  def congr(s: => Strategy, merge: Iterable[(T,Boolean)] => Option[List[T]]): Strategy =
    t =>
      val (children, assembler) = destruct(t)
      val iter =
        for ch <- children
          yield s.apply(ch).map((_, true)).getOrElse((ch, false))
      merge(iter).map(assembler.apply(_))

  def all(s: => Strategy): Strategy =
    def merge(ch: Iterable[(T,Boolean)]): Option[List[T]] =
      val (results, flags) = ch.toList.unzip
      if flags.forall(s => s) then Some(results) else None
    congr(s, merge)

  def some(s: => Strategy): Strategy =
    def merge(ch: Iterable[(T,Boolean)]): Option[List[T]] =
      val (results, flags) = ch.toList.unzip
      if flags.exists(s => s) then Some(results) else None
    congr(s, merge)

  def first(s: => Strategy): Strategy =
    def merge(ch: Iterable[(T,Boolean)]): Option[List[T]] =
      if ch.isEmpty then None
      else
        val (res, flag) = ch.head
        if flag then Some(res :: ch.unzip._1.toList)
        else merge(ch.tail).map(res :: _)
    congr(s, merge)

  def reduce(s: => Strategy): Strategy =
    def x: Strategy = choice(s, some(x))
    repeat(x)

  def rulef(f: T => T): Strategy = f.andThen(Some(_))
  def rule(f: PartialFunction[T,T]): Strategy = f.lift

  def rewrite(s: => Strategy)(t: T): T = s.apply(t).getOrElse(t)
}