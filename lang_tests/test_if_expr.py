def test() -> None:
    a = 1 if True else 0
    assert a == 1
    b = 1 if False else 0
    assert b == 0