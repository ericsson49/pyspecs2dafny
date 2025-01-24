from typing import Collection, Mapping, Never

from .utils import CFG

L = object


class Labeler:

    def get_entry_label(self) -> L: ...

    def get_exit_label(self) -> L: ...

    def get_child_label(self, n: L, child: int | str) -> L: ...


class IndexingLabeler(Labeler):
    def get_entry_label(self) -> tuple:
        return ()

    def get_exit_label(self) -> tuple:
        return None

    def get_child_label(self, n: tuple, child: int | str):
        return n + (child,)


class SimpleLabeler(Labeler):
    def __init__(self):
        self.labels = set()

    def get_entry_label(self) -> str:
        return 'entry'

    def get_exit_label(self) -> str:
        return 'exit'

    def get_child_label(self, n: str, child: int | str) -> str:
        label = f'L_{len(self.labels)}'
        self.labels.add(label)
        return label


class InstructionRenamer[T]:
    @classmethod
    def get_defs_uses(cls, instr: T) -> tuple[Collection[str], Collection[str]]:
        ...

    @classmethod
    def rename_defs(cls, instr: T, var_map: Mapping[str, str]) -> T:
        ...

    @classmethod
    def rename_uses(cls, instr: T, var_map: Mapping[str, str]) -> T:
        ...


class BBlock[I]:
    def __init__(self, instr_renamer: type[InstructionRenamer[I]], instrs: list[I]):
        self.instr_renamer = instr_renamer
        self.instrs = instrs

    def get_instr_def_uses(self, instr) -> tuple[Collection[str], Collection[str]]:
        return self.instr_renamer.get_defs_uses(instr)

    def get_defs_uses(self) -> tuple[Collection[str],Collection[str]]:
        defs = set()
        for instr in self.instrs:
            i_defs, _ = self.get_instr_def_uses(instr)
            defs |= i_defs
        uses = set()
        for instr in reversed(self.instrs):
            kill, gen = self.get_instr_def_uses(instr)
            uses = (uses - set(kill)) | set(gen)
        return defs, uses


class BBlockFactory[AST]:
    def unapply_block(self, n: AST) -> None | list[AST]: ...
    def unapply_while(self, n: AST) -> None | tuple[BBlock[AST], list[AST]]: ...
    def unapply_for(self, n: AST) -> None | tuple[BBlock[AST], BBlock, list[AST]]: ...
    def unapply_if(self, n: AST) -> None | tuple[BBlock[AST], list[AST], list[AST]]: ...
    def unapply_break(self, n: AST) -> None | tuple[()]: ...
    def unapply_continue(self, n: AST) -> None | tuple[()]: ...
    def unapply_return(self, n: AST) -> None | tuple[()] | BBlock[AST]: ...
    def unapply_simple(self, n: AST) -> BBlock[AST]: ...


class BBlockCFG(CFG[L, L]):
    def __init__(self, entry: L, exit: L, nodes, edges):
        super().__init__(edges, nodes)
        self.entry = entry
        self.exit = exit


class _DummyRenamer(InstructionRenamer[Never]):
    pass


class DummyBlock(BBlock[Never]):
    def __init__(self, defs: Collection[str] = None, uses: Collection[str] = None):
        super().__init__(_DummyRenamer, [])
        self.defs = set() if defs is None else defs
        self.uses = set() if uses is None else uses

    def get_defs_uses(self) -> tuple[Collection[str],Collection[str]]:
        return self.defs, self.uses


class CFGBuilder[AST]:
    def __init__(self, labeler: Labeler, bblock_factory: BBlockFactory[AST]):
        self.labeler = labeler
        self.bblock_factory = bblock_factory
        self.loops: list[tuple[L, L]] = []
        self.nodes: dict[L, BBlock[AST]] = {}
        self.edges: list[tuple[L, L]] = []

    def get_entry(self): return self.labeler.get_entry_label()

    def get_exit(self): return self.labeler.get_exit_label()

    def get_label(self, parent: L, child: str|int) -> L:
        return self.labeler.get_child_label(parent, child)

    def add_node(self, block_id: L, block: BBlock) -> L:
        assert block is not None
        self.nodes[block_id] = block
        return block_id

    def add_edge(self, frm: object, to: object):
        self.edges.append((frm, to))

    def on_stmt(self, idx: L, s: AST, nxt: L) -> L:
        bbf = self.bblock_factory

        if (st := bbf.unapply_block(s)) is not None:
            stmts = st
            if len(stmts) == 0:
                return nxt
            else:
                indices = [self.get_label(idx, i) for i in range(len(stmts))]
                cont = nxt
                for idx, stmt in reversed(list(zip(indices, stmts))):
                    cont = self.on_stmt(idx, stmt, cont)
                return cont
        elif st := bbf.unapply_while(s):
            test, body = st
            loop_head = self.add_node(self.get_label(idx, 'while-test'), test)
            self.loops.append((loop_head, nxt))
            body_label = self.on_stmt(self.get_label(idx, 'while-body'), body, loop_head)
            del self.loops[-1]
            self.add_edge(loop_head, body_label)
            self.add_edge(loop_head, nxt)
            return loop_head
        elif st := bbf.unapply_for(s):
            pre_head, pre_body, body = st
            test = BBlock(set(), set())
            loop_head = self.add_node(self.get_label(idx, 'for-test'), test)
            self.loops.append((loop_head, nxt))
            body_label = self.on_stmt(self.get_label(idx, 'for-body'), body, loop_head)
            del self.loops[-1]
            pre_body_label = self.add_node(self.get_label(idx, 'for-pre-body'), pre_body)
            self.add_edge(pre_body_label, body_label)
            self.add_edge(loop_head, pre_body_label)
            self.add_edge(loop_head, nxt)
            pre_head_label = self.add_node(self.get_label(idx, 'for-pre-head'), pre_head)
            self.add_edge(pre_head_label, loop_head)
            return pre_head_label
        elif st := bbf.unapply_if(s):
            test, body, orelse = st
            if_head = self.add_node(self.get_label(idx, 'if-test'), test)
            body_entry = self.on_stmt(self.get_label(idx, 'if-then'), body, nxt)
            else_entry = self.on_stmt(self.get_label(idx, 'if-else'), orelse, nxt)
            self.add_edge(if_head, body_entry)
            self.add_edge(if_head, else_entry)
            return if_head
        elif (st := bbf.unapply_break(s)) is not None:
            return self.loops[-1][1]
        elif (st := bbf.unapply_continue(s)) is not None:
            return self.loops[-1][0]
        elif (st := bbf.unapply_return(s)) is not None:
            if st == ():
                return self.get_exit()
            else:
                assert isinstance(st, BBlock)
                return_label = self.add_node(idx, st)
                self.add_edge(return_label, self.get_exit())
                return return_label
        else:
            block = bbf.unapply_simple(s)
            node = self.add_node(idx, block)
            self.add_edge(node, nxt)
            return node

    def build(self):
        return BBlockCFG(self.get_entry(), self.get_exit(), self.nodes, self.edges)

    def make_cfg(self, body: AST, params: set[str]):
        entry = self.add_node(self.labeler.get_entry_label(), DummyBlock(defs=params))
        exit_ = self.add_node(self.labeler.get_exit_label(), DummyBlock())
        first_l = self.on_stmt(entry, body, exit_)
        self.add_edge(entry, first_l)
        return self.build()


