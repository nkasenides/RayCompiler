package com.panickapps.ray.exceptions;

import org.antlr.v4.runtime.Token;

public class VariableAlreadyDefinedException extends CompileException {

    String varName;

    public VariableAlreadyDefinedException(Token token) {
        super(token);
        varName = token.getText();
    }//end VariableAlreadyDefinedException()

    @Override
    public String getMessage() {
        return line + ":" + column + " Variable already defined: \"" + varName + "\".";
    }
}//end class VariableAlreadyDefinedException
