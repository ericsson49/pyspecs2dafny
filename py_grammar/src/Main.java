import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import python3_grammar.Python3Lexer;
import python3_grammar.Python3Parser;
import python3_grammar.Python3Parser.AugAssignContext;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        var lexer = new Python3Lexer( new ANTLRFileStream("lang_tests/test0000.py"));
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        new Python3Parser(tokens);
        var parser = new Python3Parser( tokens );
    }
}