package com.panickapps.ray.exceptions;

import org.antlr.v4.runtime.Token;

public class FunctionAlreadyDefinedException extends CompileException {

    private final String functionName;

    public FunctionAlreadyDefinedException(Token token) {
        super(token);
        this.functionName = token.getText();
    }

    @Override
    public String getMessage() {
        return line + ":" + column + " function already defined: \"" + functionName + "\".";
    }

}