// Generated from src\com\panickapps\ray\parser\Ray.g4 by ANTLR 4.7
package com.panickapps.ray.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RayParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RayVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RayParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(RayParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MainStatement}
	 * labeled alternative in {@link RayParser#programPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainStatement(RayParser.MainStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ProgramPartFunctionDefinition}
	 * labeled alternative in {@link RayParser#programPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgramPartFunctionDefinition(RayParser.ProgramPartFunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(RayParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#branch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranch(RayParser.BranchContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(RayParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RelationalOperator}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalOperator(RayParser.RelationalOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Multiplication}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplication(RayParser.MultiplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(RayParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Modulo}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModulo(RayParser.ModuloContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BracketedExpression}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketedExpression(RayParser.BracketedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code String}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(RayParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalOr}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOr(RayParser.LogicalOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCallExpression}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCallExpression(RayParser.FuncCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(RayParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalAnd}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAnd(RayParser.LogicalAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Division}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivision(RayParser.DivisionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Plus}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlus(RayParser.PlusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Minus}
	 * labeled alternative in {@link RayParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinus(RayParser.MinusContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(RayParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#varInitialization}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarInitialization(RayParser.VarInitializationContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(RayParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#println}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintln(RayParser.PrintlnContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(RayParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(RayParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(RayParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#statementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementList(RayParser.StatementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(RayParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(RayParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link RayParser#tierDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTierDefinition(RayParser.TierDefinitionContext ctx);
}