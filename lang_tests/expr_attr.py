class Test():
    a: int

def test() -> None:
    #@@ensures ret_.Result?
    t = Test()
    t.a = 10
    assert t.a == 10