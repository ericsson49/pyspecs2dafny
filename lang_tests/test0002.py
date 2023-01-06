# `while` tests
def test() -> None:
  # @@ensures ret_.Result?
  a = 5
  while a >= 0:
    #@@decreases a.value
    assert a >= 0
    a = a - 1
  assert a < 0