def test_add() -> None:
    assert 2+3 == 5


def test_sub() -> None:
    assert 10 - 5 == 5


def test_mul() -> None:
    assert 6*5 == 30


def test_floordiv() -> None:
    assert 5 // 2 == 2


def test_mod() -> None:
    assert 10 % 4 == 2


def test_rshift() -> None:
    assert 10 >> 1 == 5


def test_lshift() -> None:
    assert 5 << 1 == 10


def test_bitor() -> None:
    assert 5 | 8 == 13


def test_bitand() -> None:
    assert 15 & 10 == 10


def test_bitxor() -> None:
    assert 15 ^ 1 == 14
