class Test():
    a: int


def test_assign_var() -> None:
    a = 10
    assert a == 10


def test_assign_attr() -> None:
    t = Test()
    t.a = 10
    assert t.a == 10


def test_assign_index() -> None:
    a = [0]
    a[0] = 10
    assert a[0] == 10


def test_ann_assign() -> None:
    a: int = 10
    assert a == 10


def test_var_decl() -> None:
    a: int
    a = 10
    assert a == 10
