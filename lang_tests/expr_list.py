def test0() -> None:
    #@@ensures ret_.Result?
    a = [1]
    assert a[0] == 1

def test1() -> None:
    #@@ensures ret_.Result?
    a = [1,2]
    assert a[0] == 1
    assert a[1] == 2

def test2() -> None:
    #@@ensures ret_.Result?
    a = [1,2,3]
    assert a[0] == 1
    assert a[1] == 2
    assert a[2] == 3

def test0_log() -> None:
    #@@ensures ret_.Result?
    log = testStepper()
    a = [testStep(log, 0, 1)]
    assert testStep(log, 1, a)[testStep(log, 2, 0)] == 1

def test1_log() -> None:
    #@@ensures ret_.Result?
    log = testStepper()
    a = [testStep(log, 0, 1), testStep(log, 1, 2)]
    assert testStep(log, 2, a)[testStep(log, 3, 0)] == 1
    assert testStep(log, 4, a)[testStep(log, 5, 1)] == 2

def test2_log() -> None:
    #@@ensures ret_.Result?
    log = testStepper()
    a = [testStep(log, 0, 1), testStep(log, 1, 2), testStep(log, 2, 3)]
    assert testStep(log, 3, a)[testStep(log, 4, 0)] == 1
    assert testStep(log, 5, a)[testStep(log, 6, 1)] == 2
    assert testStep(log, 7, a)[testStep(log, 8, 2)] == 3
