def test_assert() -> None:
    a = 1
    assert a == 1
    b = True
    assert b
    assert b == True
    c = False
    assert not c
    assert c == False


def test_assert_failure() -> None:
    a = 1
    assert a == 1 # ok
    assert 1 == 0 # failure