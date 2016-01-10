package com.github.rustock0.fuzzy.evaluator;

/**
 * An operator.
 *
 * @author Evgeny Mironenko.
 */
public class Operator {

    private String symbol;
    private int precedence;
    private int operandCount;

    /**
     * Constructor.
     *
     * @param symbol        The operator name.
     * @param operandCount  The number of operands of the operator (must be 1 or 2).
     * @param precedence    The precedence of the operator.
     * @throws IllegalArgumentException if operandCount if not 1 or 2 or if associativity is none
     * @throws NullPointerException     if symbol or associativity are null
     */
    public Operator(String symbol, int operandCount, int precedence) {
        if (symbol == null) {
            throw new NullPointerException();
        }
        if (symbol.length() == 0) {
            throw new IllegalArgumentException("Operator symbol can't be null");
        }
        if ((operandCount < 1) || (operandCount > 2)) {
            throw new IllegalArgumentException("Only unary and binary operators are supported");
        }
        this.symbol = symbol;
        this.operandCount = operandCount;
        this.precedence = precedence;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getOperandCount() {
        return this.operandCount;
    }

    public int getPrecedence() {
        return this.precedence;
    }
}