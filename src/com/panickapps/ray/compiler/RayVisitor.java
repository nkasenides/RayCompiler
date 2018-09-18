package com.panickapps.ray.compiler;

import com.panickapps.ray.exceptions.UndeclaredVariableException;
import com.panickapps.ray.exceptions.UndefinedFunctionException;
import com.panickapps.ray.exceptions.VariableAlreadyDefinedException;
import com.panickapps.ray.parser.RayBaseVisitor;
import com.panickapps.ray.parser.RayParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Map;

public class RayVisitor extends RayBaseVisitor<String> {

    public static int LOCALS_LIMIT = 100;
    public static int STACK_LIMIT = 100;

    public static String INSTRUCTION_LOADCONSTANT = "ldc ";
    public static String INSTRUCTION_ADDITION = "iadd ";
    public static String INSTRUCTION_SUBTRACTION = "isub ";
    public static String INSTRUCTION_MULTIPLICATION = "imul ";
    public static String INSTRUCTION_DIVISION = "idiv ";
    public static String INSTRUCTION_MODULO = "irem ";
    public static String INSTRUCTION_STORE = "istore ";
    public static String INSTRUCTION_LOAD = "iload ";
    public static String INSTRUCTION_INVOKE_FUNCTION_STATIC = "invokestatic ";
    public static String INSTRUCTION_METHOD_CALL_PRENAME = ".method public static ";
    public static String INSTRUCTION_METHOD_LOCALS_LIMIT = ".limit locals " + Integer.toString(LOCALS_LIMIT) + " ";
    public static String INSTRUCTION_METHOD_STACK_LIMIT = ".limit stack " + Integer.toString(STACK_LIMIT) + " ";
    public static String INSTRUCTION_RETURN = "ireturn ";
    public static String INSTRUCTION_METHOD_END = ".end method ";
    public static String DATATYPE_INT = "I";
    public static String DATATYPE_VOID = "V";
    public static String DATATYPE_STRING = "Ljava/lang/String;";
    public static String INSTRUCTION_IF_NOT_EQUAL = "ifne";
    public static String INSTRUCTION_GOTO = "goto";
    public static String LABEL_JUMP_IFTRUE = "ifTrue";
    public static String LABEL_JUMP_ENDIF = "endIf";
    public static String INSTRUCTION_JUMPIF_GREATER = "if_icmpgt";
    public static String INSTRUCTION_JUMPIF_LESS = "if_icmplt";
    public static String INSTRUCTION_JUMPIF_GREATEROREQUAL = "if_icmpge";
    public static String INSTRUCTION_JUMPIF_LESSOREQUAL = "if_icmple";

    public static String repeatString(String string, int repeatTimes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < repeatTimes; i++) {
            result.append(string);
        }
        return result.toString();
    }

    private Map<String, Integer> variables = new HashMap<>();
    private JvmStack jvmStack = new JvmStack();
    private final FunctionList definedFunctions;
    private int branchCounter = 0;
    private int compareCount = 0;
    private int logicalAndCounter = 0;
    private int logicalOrCounter = 0;

    public RayVisitor(FunctionList definedFunctions) {
        if (definedFunctions == null) throw new NullPointerException("definedFunctions");
        this.definedFunctions = definedFunctions;
    }

    @Override
    public String visitPrint(RayParser.PrintContext ctx) {
        String argumentInstructions = visit(ctx.argument);
        DataType type = jvmStack.pop();
        return "getstatic java/lang/System/out Ljava/io/PrintStream;\n" +
                argumentInstructions + "\n" +
                "invokevirtual java/io/PrintStream/print(" + type.getJvmType() + ")V\n";
    }

    @Override
    public String visitPrintln(RayParser.PrintlnContext ctx) {
        String argumentInstructions = visit(ctx.argument);
        DataType type = jvmStack.pop();
        return "getstatic java/lang/System/out Ljava/io/PrintStream;\n" +
                argumentInstructions + "\n" +
                "invokevirtual java/io/PrintStream/println(" + type.getJvmType() + ")V\n";
    }//end visitPrintln()

    @Override
    public String visitPlus(RayParser.PlusContext ctx) {
        String instructions = visitChildren(ctx) + "\n" + INSTRUCTION_ADDITION;
        jvmStack.pop();
        jvmStack.pop();
        jvmStack.push(DataType.INTEGER);
        return instructions;
    }

    @Override
    public String visitMinus(RayParser.MinusContext ctx) {
        String instructions = visitChildren(ctx) + "\n" + INSTRUCTION_SUBTRACTION;
        jvmStack.pop();
        jvmStack.pop();
        jvmStack.push(DataType.INTEGER);
        return instructions;
    }

    @Override
    public String visitMultiplication(RayParser.MultiplicationContext ctx) {
        String instructions = visitChildren(ctx) + "\n" + INSTRUCTION_MULTIPLICATION;
        jvmStack.pop();
        jvmStack.pop();
        jvmStack.push(DataType.INTEGER);
        return instructions;
    }

    @Override
    public String visitDivision(RayParser.DivisionContext ctx) {
        String instructions = visitChildren(ctx) + "\n" + INSTRUCTION_DIVISION;
        jvmStack.pop();
        jvmStack.pop();
        jvmStack.push(DataType.INTEGER);
        return instructions;
    }

    @Override
    public String visitModulo(RayParser.ModuloContext ctx) {
        String instructions = visitChildren(ctx) + "\n" + INSTRUCTION_MODULO;
        jvmStack.pop();
        jvmStack.pop();
        jvmStack.push(DataType.INTEGER);
        return instructions;
    }

    @Override
    public String visitRelationalOperator(RayParser.RelationalOperatorContext ctx) {
        int compareNum = compareCount;
        ++compareCount;
        String jumpInstruction;
        switch (ctx.operator.getText()) {
            case "<":
                jumpInstruction = INSTRUCTION_JUMPIF_LESS;
                break;
            case ">":
                jumpInstruction = INSTRUCTION_JUMPIF_GREATER;
                break;
            case "<=":
                jumpInstruction = INSTRUCTION_JUMPIF_LESSOREQUAL;
                break;
            case ">=":
                jumpInstruction = INSTRUCTION_JUMPIF_GREATEROREQUAL;
                break;
            default:
                throw new IllegalArgumentException("Unknown Operator: " + ctx.operator.getText());
        }
        String instructions = visitChildren(ctx) + "\n" +
                jumpInstruction + " onTrue" + compareNum + "\n" +
                "ldc 0\n" +
                "goto onFalse" + compareNum + "\n" +
                "onTrue" + compareNum + ":\n" +
                "ldc 1\n" +
                "onFalse" + compareNum + ":";
        jvmStack.pop();
        jvmStack.pop();
        jvmStack.push(DataType.INTEGER);
        return instructions;
    }

    @Override
    public String visitLogicalAnd(RayParser.LogicalAndContext ctx) {
        String left = visit(ctx.left);
        String right = visit(ctx.right);
        int andNum = logicalAndCounter;
        ++logicalAndCounter;

        jvmStack.pop();
        jvmStack.pop();
        jvmStack.push(DataType.INTEGER);

        return left + "\n" +
                "ifeq onAndFalse" + andNum + "\n" +
                right + "\n" +
                "ifeq onAndFalse" + andNum + "\n" +
                "ldc 1\n" +
                "goto andEnd" + andNum + "\n" +
                "onAndFalse" + andNum + ":\n" +
                "ldc 0\n" +
                "andEnd" + andNum + ":\n";
    }

    @Override
    public String visitLogicalOr(RayParser.LogicalOrContext ctx) {
        String left = visit(ctx.left);
        String right = visit(ctx.right);
        int orNum = logicalOrCounter;
        ++logicalOrCounter;

        jvmStack.pop();
        jvmStack.pop();
        jvmStack.push(DataType.INTEGER);

        return left + "\n" +
                "ifne onOrTrue" + orNum + "\n" +
                right + "\n" +
                "ifne onOrTrue" + orNum + "\n" +
                "ldc 0\n" +
                "goto orEnd" + orNum + "\n" +
                "onOrTrue" + orNum + ":\n" +
                "ldc 1\n" +
                "orEnd" + orNum + ":\n";
    }

    @Override
    public String visitNumber(RayParser.NumberContext ctx) {
        jvmStack.push(DataType.INTEGER);
        return INSTRUCTION_LOADCONSTANT + ctx.number.getText();
    }

    @Override
    public String visitString(RayParser.StringContext ctx) {
        jvmStack.push(DataType.STRING);
        return INSTRUCTION_LOADCONSTANT + ctx.txt.getText();
    }

    @Override
    public String visitVarDeclaration(RayParser.VarDeclarationContext ctx) {
        if (variables.containsKey(ctx.varName.getText())) {
            throw new VariableAlreadyDefinedException(ctx.varName);
        }
        variables.put(ctx.varName.getText(), variables.size());
        return "";
    }

    @Override
    public String visitVarInitialization(RayParser.VarInitializationContext ctx) {
        //Declaration:
        if (variables.containsKey(ctx.varName.getText())) {
            throw new VariableAlreadyDefinedException(ctx.varName);
        }
        variables.put(ctx.varName.getText(), variables.size());
        //Assignment:
        String instructions = visit(ctx.expr) + "\n" +
                INSTRUCTION_STORE + requireVariableIndex(ctx.varName);
        jvmStack.pop();
        return instructions;
    }

    @Override
    public String visitBranch(RayParser.BranchContext ctx) {
        String conditionInstructions = visit(ctx.condition);
        jvmStack.pop();
        String onTrueInstructions = visit(ctx.onTrue);
        String onFalseInstructions = visit(ctx.onFalse);
        int branchNumber = branchCounter;
        ++branchCounter;
        return conditionInstructions + "\n" +
                INSTRUCTION_IF_NOT_EQUAL + " " + LABEL_JUMP_IFTRUE + branchNumber + "\n" +
                onFalseInstructions + "\n" +
                INSTRUCTION_GOTO + " " + LABEL_JUMP_ENDIF + branchNumber + "\n" +
                LABEL_JUMP_IFTRUE + branchNumber + ":\n" +
                onTrueInstructions + "\n" +
                LABEL_JUMP_ENDIF + branchNumber + ":\n";
    }

    @Override
    public String visitAssignment(RayParser.AssignmentContext ctx) {
        String instructions = visit(ctx.expr) + "\n" +
                INSTRUCTION_STORE + requireVariableIndex(ctx.varName);
        jvmStack.pop();
        return instructions;
    }

    @Override
    public String visitVariable(RayParser.VariableContext ctx) {
        jvmStack.push(DataType.INTEGER);
        return INSTRUCTION_LOAD + requireVariableIndex(ctx.varName);
    }

    @Override
    public String visitFunctionCall(RayParser.FunctionCallContext ctx) {
        int numberOfParameters = ctx.arguments.expressions.size();
        if (!definedFunctions.contains(ctx.funcName.getText(), numberOfParameters)) {
            throw new UndefinedFunctionException(ctx.funcName);
        }
        String instructions = "";
        String argumentInstructions = visit(ctx.arguments);
        if (argumentInstructions != null) instructions += argumentInstructions + '\n';
        instructions += INSTRUCTION_INVOKE_FUNCTION_STATIC + "HelloWorld/" + ctx.funcName.getText() + "(";
        instructions += repeatString(DATATYPE_INT, numberOfParameters);
        instructions += ")" + DATATYPE_INT + "\n";
        for (int i = 0; i < numberOfParameters; i++) jvmStack.pop();
        jvmStack.push(DataType.INTEGER);
        return instructions;
    }

    @Override
    public String visitFunctionDefinition(RayParser.FunctionDefinitionContext ctx) {
        Map<String, Integer> oldVariables = variables;
        JvmStack oldJvmStack = jvmStack;
        variables = new HashMap<>();
        jvmStack = new JvmStack();
        visit(ctx.params);
        String statementInstructions = visit(ctx.statements);
        String result = INSTRUCTION_METHOD_CALL_PRENAME + ctx.funcName.getText() + "(";
        int numberOfParameters = ctx.params.declarations.size();
        result += repeatString(DATATYPE_INT, numberOfParameters);
        result += ")" + DATATYPE_INT + "\n" +
                INSTRUCTION_METHOD_LOCALS_LIMIT + "\n" +
                INSTRUCTION_METHOD_STACK_LIMIT + "\n" +
                (statementInstructions == null ? "" : statementInstructions + "\n") +
                visit(ctx.returnValue) + "\n" +
                INSTRUCTION_RETURN + "\n" +
                INSTRUCTION_METHOD_END;
        jvmStack.pop();
        variables = oldVariables;
        jvmStack = oldJvmStack;
        return result;
    }

    @Override
    public String visitProgram(RayParser.ProgramContext ctx) {
        String mainCode = "";
        String functionDefinitionsCode = "";
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            String instructions = visit(child);
            if (child instanceof RayParser.MainStatementContext) {
                mainCode += instructions + "\n";
            }
            else {
                functionDefinitionsCode += instructions + "\n";
            }
        }
        return functionDefinitionsCode +
                "\n.method public static main([Ljava/lang/String;)V\n" +
                "\t.limit stack 100\n" +
                "\t.limit locals 100\n" +
                "\t\n" +
                mainCode + "\n" +
                "\treturn\n" +
                "\t\n" +
                ".end method";
    }

    @Override
    public String visitTierDefinition(RayParser.TierDefinitionContext ctx) {
        System.out.println("TIER ...: " + ctx.tierID.getText());
        return "";
    }

    private int requireVariableIndex(Token varNameToken) {
        Integer varIndex = variables.get(varNameToken.getText());
        if (varIndex == null) throw new UndeclaredVariableException(varNameToken);
        return varIndex;
    }//end requireVariableIndex()

    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        if (aggregate == null) return nextResult;
        if (nextResult == null) return aggregate;
        return aggregate + "\n" + nextResult;
    }

}
