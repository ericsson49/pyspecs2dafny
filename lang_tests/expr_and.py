def test() -> None:
    #@@ensures ret_.Result?
    assert True and True
    assert not (True and False)
    assert not (False and True)
    assert not (False and False)
    assert True and True and True
    assert not (True and True and False)
    assert not (True and False and True)
    assert not (True and False and False)
    assert not (False and True and True)
    assert not (False and True and False)
    assert not (False and False and True)
    assert not (False and False and False)
