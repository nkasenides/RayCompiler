package com.panickapps.ray.compiler;

import com.panickapps.ray.parser.RayLexer;
import com.panickapps.ray.parser.RayParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class Main {

    public static final String FILENAME = "code.ray";

    public static void main(String[] args) {
        try {
            ANTLRInputStream input = new ANTLRFileStream(FILENAME);
            System.out.println(compile(input));
        } catch (IOException e) { e.printStackTrace(); }

    }

    public static String compile(ANTLRInputStream input) {
        RayLexer lexer = new RayLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RayParser parser = new RayParser(tokens);
        ParseTree parseTree = parser.program();
        FunctionList definedFunctions = FunctionDefinitionFinder.findFunctions(parseTree);
        return createJasminFile(new RayVisitor(definedFunctions).visit(parseTree));
    }//end compile()

    private static String createJasminFile(String instructions) {
        return ".class public HelloWorld\n" +
                ".super java/lang/Object\n" +
                "\n" +
                instructions;
    }//end createJasminFile()

}
