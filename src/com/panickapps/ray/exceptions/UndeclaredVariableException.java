package com.panickapps.ray.exceptions;

import org.antlr.v4.runtime.Token;

public class UndeclaredVariableException extends CompileException {

    private String varName;

    public UndeclaredVariableException(Token varNameToken) {
        super(varNameToken);
        varName = varNameToken.getText();
    }//end UndeclaredVariableException()

    @Override
    public String getMessage() {
        return line + ":" + column + " Undeclared variable \"" + varName + "\".";
    }//end getMessage()

}//end class UndeclaredVariableException
