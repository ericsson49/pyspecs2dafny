def test1() -> None:
    #@@ensures ret_.Result?
    a: PyDict[int, int] = {0: 0}
    assert a[0] == 0
    a[0] = 1
    assert a[0] == 1