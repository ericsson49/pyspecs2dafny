def test_uadd() -> None:
    assert +10 == 10


def test_usub() -> None:
    assert -10 == 0 - 10


def test_invert() -> None:
    assert ~1 == -2


def test_not() -> None:
    assert not False
    assert not (not True)