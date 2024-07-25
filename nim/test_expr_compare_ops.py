def test_eq() -> None:
    assert 1 == 1


def test_not_eq() -> None:
    assert 1 != 0


def test_lt() -> None:
    assert 1 < 2


def test_leq() -> None:
    assert 1 <= 2
    assert 1 <= 1


def test_gt() -> None:
    assert 1 > 0


def test_geq() -> None:
    assert 1 >= 1
    assert 1 >= 0
