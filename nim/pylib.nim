
type
    pyint* = ref object of RootObj
        value: int
    pybool* = ref object of pyint

proc mk_pyint*(v: int): pyint = pyint(value: v)

proc mk_pybool*(v: bool): pybool = pybool(value: if v: 1 else: 0)

converter toBool*(b: pybool): bool = b.value != 0

proc pyand*(a: pybool, b: pybool): pybool = mk_pybool(a.value != 0 and b.value != 0)

proc pyor*(a: pybool, b: pybool): pybool = mk_pybool(a.value != 0 or b.value != 0)

proc pynot*(a: pybool): pybool = mk_pybool(not(a.value != 0))

proc pyeq*(a: pybool, b: pybool): pybool = mk_pybool((a.value != 0) == (b.value != 0))

proc pyneq*(a: pybool, b: pybool): pybool = pynot(mk_pybool((a.value != 0) == (b.value != 0)))

proc pyeq*(a: pyint, b: pyint): pybool = mk_pybool(a.value == b.value)

proc pyneq*(a: pyint, b: pyint): pybool = pynot(mk_pybool(a.value == b.value))

proc pyadd*(a: pyint, b: pyint): pyint = mk_pyint(a.value + b.value)

proc pysub*(a: pyint, b: pyint): pyint = mk_pyint(a.value - b.value)

proc pymul*(a: pyint, b: pyint): pyint = mk_pyint(a.value * b.value)

proc pydiv*(a: pyint, b: pyint): pyint = mk_pyint(a.value div b.value)

proc pymod*(a: pyint, b: pyint): pyint = mk_pyint(a.value mod b.value)

proc pylshift*(a: pyint, b: pyint): pyint = mk_pyint(a.value shl b.value)

proc pyrshift*(a: pyint, b: pyint): pyint = mk_pyint(a.value shr b.value)

proc pyband*(a: pyint, b: pyint): pyint = mk_pyint(a.value and b.value)

proc pybor*(a: pyint, b: pyint): pyint = mk_pyint(a.value or b.value)

proc pybxor*(a: pyint, b: pyint): pyint = mk_pyint(a.value xor b.value)

proc pylt*(a: pyint, b: pyint): pybool = mk_pybool(a.value < b.value)

proc pylte*(a: pyint, b: pyint): pybool = mk_pybool(a.value <= b.value)

proc pygt*(a: pyint, b: pyint): pybool = mk_pybool(a.value > b.value)

proc pygte*(a: pyint, b: pyint): pybool = mk_pybool(a.value >= b.value)

proc pyuadd*(a: pyint): pyint = a

proc pyusub*(a: pyint): pyint = mk_pyint(-a.value)

proc pyinvert*(a: pyint): pyint = mk_pyint(not a.value)
