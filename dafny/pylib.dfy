datatype Outcome<T> =
| Result(value: T)
| Failure
{
  predicate IsFailure() {
    this.Failure?
  }
  function PropagateFailure<U>(): Outcome<U>
    requires IsFailure()
  {
    Failure
  }
  function Extract(): T
    requires !IsFailure()
  {
    this.value
  }
}

datatype Status =
| Success
| Exception
{
  predicate IsFailure() { this.Exception?  }
  function PropagateFailure(): Status
    requires IsFailure()
  {
    Exception
  }
}

trait PyValue<T> {
  const value: T;
}
trait pyobject {}
trait pyint extends pyobject, PyValue<int> {
}
trait pybool extends pyint {}

trait pybytes extends pyobject, pyint {}

trait PySequence<T> {
  var content: seq<T>;
}
trait PyList<T> extends PySequence<T> {
  function get(i: pyint): Outcome<T>
  reads this`content
  {
    var i := i.value;
    if (0 <= i < |content|) then
      Result(content[i])
    else
      Failure
  }
  method update(i: pyint, v: T) returns (ret_: Outcome<()>)
  modifies this`content
  ensures (0 <= i.value < old(|content|)) <==> ret_.Result?
  ensures (0 <= i.value < old(|content|)) ==> (content == old(content[i.value := v]))
  {
    if (0 <= i.value < |content|) {
      content := content[i.value := v];
      ret_ := Result(());
    } else {
      ret_ := Failure;
    }
  }
  method append(v: T) returns (ret_: Outcome<()>)
  modifies this`content
  ensures content == old(content) + [v]
  ensures ret_.Result?
  {
    ret_ := Result(());
    content := content + [v];
  }
}
trait PySet<T(==)> extends PySequence<T> {
  var repr: set<T>;
}
trait PyDict<K(==),V> extends PySequence<(K,V)> {
    function find(k: K, c: seq<(K,V)>): seq<V> {
        if |c| == 0 then []
        else if c[0].0 == k then [c[0].1] + find(k, c[1..])
        else find(k, c[1..])
    }
  function get(k: K): Outcome<V>
  reads this`content
  {
    var results := find(k, content);
    if |results| == 0 then Failure
    else Result(results[|results| - 1])
  }
  //method update(k: PyValue<Kv, K>, v: V) returns (ret_: Outcome<V>)
  //modifies this`repr
  //ensures (k.value in old(repr)) <==> ret_.Result?
  //ensures (k.value in old(repr)) ==> (repr == old(repr[k.value := v]))
}

function is_true(b: pybool): bool
ensures (b.value != 0) <==> is_true(b)

function new_pyint_f(n: int): pyint
method new_pyint(n: int) returns (ret_: pyint)
ensures ret_.value == n
function pybool_repr(b: bool): int
ensures b <==> pybool_repr(b) != 0
method new_pybool(b: bool) returns (ret_: pybool)
ensures fresh(ret_)
ensures ret_.value == pybool_repr(b)

method pyplus(a: pyint, b: pyint) returns (ret_: pyint)
ensures a.value+b.value == ret_.value
method pyminus(a: pyint, b: pyint) returns (ret_: pyint)
ensures a.value-b.value == ret_.value
method pyeq(a: pyint, b: pyint) returns (ret_: pybool)
ensures a.value == b.value <==> is_true(ret_)
method pyne(a: pyint, b: pyint) returns (ret_: pybool)
ensures a.value != b.value <==> is_true(ret_)
method pyle(a: pyint, b: pyint) returns (ret_: pybool)
ensures is_true(ret_) <==> a.value <= b.value
method pylt(a: pyint, b: pyint) returns (ret_: pybool)
ensures is_true(ret_) <==> a.value < b.value
method pyge(a: pyint, b: pyint) returns (ret_: pybool)
ensures is_true(ret_) <==> a.value >= b.value
method pygt(a: pyint, b: pyint) returns (ret_: pybool)
ensures is_true(ret_) <==> a.value > b.value
method pynot(b: pybool) returns (ret_: pybool)
ensures is_true(b) <==> !is_true(ret_)

method new_pylist<T>(elems: seq<T>) returns (ret_: PyList<T>)
ensures fresh(ret_)
ensures ret_.content == elems
method new_pyset<T>(elems: set<T>) returns (ret_: PySet<T>)
method new_pydict<K,V>(elems: seq<(K,V)>) returns (ret_: PyDict<K,V>)
ensures fresh(ret_)
ensures ret_.content == elems

trait PyIterator<T> {
  const content: seq<T>;
  var nextIndex: nat;
  function has_next_f(): bool
  reads this
  {
    nextIndex < |content|
  }
  method has_next() returns (ret_: pybool)
  ensures fresh(ret_) && is_true(ret_) == has_next_f()
  {
    ret_ := new_pybool(has_next_f());
  }
  method next() returns (ret_: T)
  requires has_next_f()
  modifies this`nextIndex
  ensures this.nextIndex == old(this.nextIndex) + 1
  ensures ret_ == content[old(this.nextIndex)]
  function decreases_(): int
  reads this`nextIndex
  {
    |content| - nextIndex
  }
}
method iter<T>(coll: PySequence<T>) returns (ret_: PyIterator<T>)
ensures coll is PyList<T> ==> ret_.content == (coll as PyList<T>).content
ensures ret_.nextIndex == 0
ensures fresh(ret_)

method pyassert(t: pybool) returns (ret_: Outcome<()>)
ensures is_true(t) <==> ret_.Result?

method logStep<T>(log: PyList<pyint>, step: pyint, v: T) returns (ret_: T)
modifies log`content
ensures log.content == old(log.content) + [step]
ensures ret_ == v
{
  var _ := log.append(step);
  ret_ := v;
}

class TestStepper {
  var step: nat;
  constructor()
  ensures step == 0
  {
    this.step := 0;
  }
}

method testStep<T>(log: TestStepper, num: pyint, v: T) returns (ret_: Outcome<T>)
  modifies log`step
  ensures num.value == old(log.step) <==> ret_.Result?
  ensures ret_.Result? ==> ret_.Extract() == v
  ensures ret_.Result? ==> log.step == old(log.step) + 1
{
    if log.step == num.value {
      log.step := log.step + 1;
      ret_ := Result(v);
    } else {
      ret_ := Failure;
    }
}

method testStepper() returns (ret_: TestStepper)
ensures fresh(ret_)
ensures ret_.step == 0
{
  ret_ := new TestStepper();
}

method checkLog(log: PyList<pyint>, len: pyint) returns (ret_: Outcome<()>)
ensures ret_.Result? <==> (|log.content| == len.value)
//ensures ret_.Result? ==> (forall i | 0 <= i < |log.repr| :: log.repr[i].value == i)
{
  if |log.content| != len.value {
    return Failure;
  }
  return Result(());
}

method len<T>(s: PySequence<T>) returns (ret_: pyint)
ensures fresh(ret_)
ensures ret_.value == |s.content|