def test() -> None:
    #@@ensures ret_.Result?
    lst = [1, 2, 3]
    #invariant tmp_5.nextIndex <= |tmp_5.content|
    #invariant |tmp_4.content| == tmp_5.nextIndex
    #invariant forall i | 0 <= i < |tmp_4.content| :: tmp_4.content[i].value == lst.content[i].value + 2
    #modifies tmp_4, tmp_5
    lst2: PyList[int] = [e + 2 for e in lst]
    assert len(lst2) == len(lst)
    assert lst2[0] == lst[0] + 2
    assert lst2[1] == lst[1] + 2
    assert lst2[2] == lst[2] + 2