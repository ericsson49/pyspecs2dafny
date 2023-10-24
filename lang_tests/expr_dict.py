def test() -> None:
    #@@ensures ret_.Result?
    a = {}
    assert len(a) == 0
    b = {1: 2}
    assert len(b) == 1
    assert b[1] == 2
    c = {1: 2, 3: 4}
    assert len(c) == 2
    assert c[1] == 2
    assert c[3] == 4
    #d = {1: 2, 3: 4, 5: 6}
    #assert len(d) == 3
    #assert d[1] == 2
    #assert d[3] == 4
    #assert d[5] == 6

