# `if` tests
def test() -> None:
  #@@ensures ret_.Result?
  a = 5
  b = 6
  if a < b:
    assert True
  else:
    assert False
  if a >= b:
    assert False
  else:
    assert True
  if a < b:
    assert True