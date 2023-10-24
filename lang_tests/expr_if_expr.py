def test() -> None:
    #@@ensures ret_.Result?
    a = 1 if True else 0
    assert a == 1
    b = 1 if False else 0
    assert b == 0

def test_log() -> None:
    #@@ensures ret_.Result?
    log = testStepper()
    a = testStep(log, 1, 1) if testStep(log, 0, True) else testStep(log, 9999, 0)
    assert a == 1
    b = testStep(log, 9999, 1) if testStep(log, 2, False) else testStep(log, 3, 0)
    assert b == 0
