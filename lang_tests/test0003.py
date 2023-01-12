# `for` tests
def test() -> None:
  a: PyList[int] = []
  a[0] = 1
  assert a[0] == 1
  b: PyDict[int,int] = {}
  c = [1, 2, 3]
  assert c[0] == 1
  assert c[1] == 2
  assert c[2] == 3
  d = {1: 2, 3:4}
  e = {1, 2, 3}
  for i in e:
    assert i > 0
