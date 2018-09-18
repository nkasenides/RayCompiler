package com.panickapps.ray.compiler;

import java.util.Deque;
import java.util.LinkedList;

public class JvmStack {

    private Deque<DataType> typesOnStack = new LinkedList<>();

    public void push(DataType type) { typesOnStack.push(type); }

    public DataType pop() { return typesOnStack.pop(); }

}//end class JvmStack
