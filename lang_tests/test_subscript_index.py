def test0() -> None:
    a: PyList[int] = [0]
    assert a[0] == 0
    a[0] = 1
    assert a[0] == 1

def test1() -> None:
    a: PyDict[int, int] = {0: 0}
    assert a[0] == 0
    a[0] = 1
    assert a[0] == 1