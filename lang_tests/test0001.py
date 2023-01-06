# `if` tests
def test() -> None:
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