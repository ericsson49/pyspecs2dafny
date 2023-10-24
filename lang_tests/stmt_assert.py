def test0() -> None:
    #@@ensures ret_.Result?
    a = 1
    assert a == 1
    b = True
    assert b
    assert b == True
    c = False
    assert not c
    assert c == False

def test1_neg() -> None:
    #@@ensures ret_.Failure?
    a = 1
    assert a == 1 # ok
    assert 1 == 0 # failure