package com.panickapps.ray.compiler;

import com.panickapps.ray.exceptions.FunctionAlreadyDefinedException;
import com.panickapps.ray.parser.RayBaseVisitor;
import com.panickapps.ray.parser.RayParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Set;

/**
 *      Searches the code for any function definitions. Executes before main visitor to allow
 *      for function declarations to be found after the current line of code being compiled.
 */

public class FunctionDefinitionFinder extends RayBaseVisitor<Set<String>> {


    public static FunctionList findFunctions(ParseTree tree) {
        FunctionList definedFunctions = new FunctionList();
        new RayBaseVisitor<Void>() {
            @Override
            public Void visitFunctionDefinition(RayParser.FunctionDefinitionContext ctx) {
                String functionName = ctx.funcName.getText();
                int parameterCount = ctx.params.declarations.size();
                if (definedFunctions.contains(functionName, parameterCount)) {
                    throw new FunctionAlreadyDefinedException(ctx.funcName);
                }
                definedFunctions.add(functionName, parameterCount);
                return null;
            }
        }.visit(tree);
        return definedFunctions;
    }

}
