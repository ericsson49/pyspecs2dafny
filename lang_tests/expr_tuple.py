def test() -> None:
    #@@ensures ret_.Result?
    a = (1,)
    assert a[0] == 1
    b = (1,2)
    assert b[0] == 1
    assert b[1] == 2
    c = (1,2,3)
    assert c[0] == 1
    assert c[1] == 2
    assert c[2] == 3

def test_log() -> None:
    #@@ensures ret_.Result?
    log = testStepper()
    a = (testStep(log, 0, 1),)
    assert testStep(log, 1, a)[testStep(log, 2, 0)] == 1
    b = (testStep(log, 3, 1), testStep(log, 4, 2))
    assert testStep(log, 5, b)[testStep(log, 6, 0)] == 1
    assert testStep(log, 7, b)[testStep(log, 8, 1)] == 2
    c = (testStep(log, 9, 1), testStep(log, 10, 2), testStep(log, 11, 3))
    assert testStep(log, 12, c)[testStep(log, 13, 0)] == 1
    assert testStep(log, 14, c)[testStep(log, 15, 1)] == 2
    assert testStep(log, 16, c)[testStep(log, 17, 2)] == 3
    checkLog(log, 17)
