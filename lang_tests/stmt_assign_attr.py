# `class` test
class Test():
    a: int

def test() -> None:
    #@@ensures ret_.Result?
    tst = Test()
    tst.a = 1
    assert tst.a == 1

def test_log() -> None:
    #@@ensures ret_.Result?
    log = testStepper()
    tst = Test()
    testStep(log, 1, tst).a = testStep(log, 0, 1)
    assert testStep(log, 2, tst).a == 1
