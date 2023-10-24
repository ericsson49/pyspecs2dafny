def test() -> None:
    #@@ensures ret_.Result?
    assert (not True) == False
    assert not (not True)
    assert (not False) == True
    assert (not False)