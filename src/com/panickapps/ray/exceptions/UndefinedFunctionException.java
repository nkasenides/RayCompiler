package com.panickapps.ray.exceptions;

import org.antlr.v4.runtime.Token;

public class UndefinedFunctionException extends CompileException {

    private final String functionName;

    public UndefinedFunctionException(Token token) {
        super(token);
        functionName = token.getText();
    }

    @Override
    public String getMessage() {
        return line + ":" + column + " Call to undefined function: \"" + functionName + "\".";
    }

}
