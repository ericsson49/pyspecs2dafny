def test() -> None:
    #@@ensures ret_.Result?
    a: PyList[int] = [0]
    assert a[0] == 0
    a[0] = 1
    assert a[0] == 1

def test_log() -> None:
    #@@ensures ret_.Result?
    log = testStepper()
    a: PyList[int] = [0]
    testStep(log, 1, a)[testStep(log, 2, 0)] = testStep(log, 0, 1)
    assert testStep(log, 3, a)[testStep(log, 4, 0)] == 1
