package com.panickapps.ray.compiler;

public enum DataType {

    INTEGER(RayVisitor.DATATYPE_INT),
    STRING(RayVisitor.DATATYPE_STRING)
    ;

    private final String jvmType;

    DataType(String jvmType) {
        this.jvmType = jvmType;
    }

    public String getJvmType() { return jvmType; }

}//end enum DataType