# `class` test
class Test():
    a: int

class Test2(Test):
    pass

def test() -> None:
    tst = Test()
    assert tst.a == 0
    tst.a = 1
    assert tst.a == 1