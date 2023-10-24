def test() -> None:
    #@@ensures ret_.Result?
    l: PyList[int] = [0]
    assert l[0] == 0
    l[0] = 1
    assert l[0] == 1

def test_log() -> None:
    #@@ensures ret_.Result?
    log = testStepper()
    l: PyList[int] = [0]
    assert testStep(log, 0, l)[testStep(log, 1, 0)] == 0
    l[0] = 1
    assert testStep(log, 2, l)[testStep(log, 3, 0)] == 1
    
