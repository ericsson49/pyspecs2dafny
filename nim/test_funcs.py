def func_return_none() -> None:
    return


def test_func_with_return_none() -> None:
    func_return_none()


def func_return_int() -> int:
    return 10


def test_func_returning_int() -> None:
    assert func_return_int() == 10