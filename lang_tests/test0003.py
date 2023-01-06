# `for` tests
def test() -> None:
  a: PyList[int] = []
  b: PyDict[int,int] = {}
  c = [1, 2, 3]
  d = {1: 2, 3:4}
  e = {1, 2, 3}
  for i in e:
    assert i > 0
