def test_and() -> None:
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


def test_or() -> None:
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


def test_not() -> None:
    assert (not True) == False
    assert not (not True)
    assert (not False) == True
    assert (not False)