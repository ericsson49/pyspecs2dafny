def test_if() -> None:
    a = 5
    b = 6
    if a < b:
        assert True
    else:
        assert False
    if a >= b:
        assert False
    elif a == 0:
        assert False
    else:
        assert True
    if a < b:
        assert True

def test_if_var_decl() -> None:
    a = 5
    b = 6
    c: int
    if a < b:
        c = 1
    else:
        c = 2
    assert c == 1


def test_while() -> None:
    a = 5
    while a >= 0:
        assert a >= 0
        a = a - 1
    assert a < 0


def test_while_break() -> None:
    a = 5
    while True:
        if a < 0:
            break
        assert a >= 0
        a = a - 1
    assert a < 0


def test_while_continue() -> None:
    a = 5
    while True:
        if a >= 0:
            assert a >= 0
            a = a - 1
            continue
        else:
            break
    assert a < 0
