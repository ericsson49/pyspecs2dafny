def test() -> None:
    #@@ensures ret_.Result?
    assert True or True
    assert True or False
    assert False or True
    assert not (False or False)
    assert True or True or True
    assert True or True or False
    assert True or False or True
    assert True or False or False
    assert False or True or True
    assert False or True or False
    assert False or False or True
    assert not (False or False or False)
