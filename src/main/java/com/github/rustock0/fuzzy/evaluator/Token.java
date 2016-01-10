package com.github.rustock0.fuzzy.evaluator;

/**
 * A token of expression.
 *
 * @author Evgeny Mironenko.
 */
class Token {

    private enum Kind {
        OPERATOR,
        LITERAL
    }

    private Kind kind;
    private Object content;

    static Token buildLiteral(String literal) {
        return new Token(Kind.LITERAL, literal);
    }

    static Token buildOperator(Operator ope) {
        return new Token(Kind.OPERATOR, ope);
    }

    private Token(Kind kind, Object content) {
        if ((kind == Kind.OPERATOR && !(content instanceof Operator)) || (kind == Kind.LITERAL && !(content instanceof String))) {
            throw new IllegalArgumentException();
        }
        this.kind = kind;
        this.content = content;
    }

    Operator getOperator() {
        return (Operator) this.content;
    }

    /**
     * Tests whether the token is an operator.
     *
     * @return see description.
     */
    public boolean isOperator() {
        return kind == Kind.OPERATOR;
    }

    /**
     * Tests whether the token is a literal.
     *
     * @return see description.
     */
    public boolean isLiteral() {
        return kind == Kind.LITERAL;
    }

    int getPrecedence() {
        return getOperator().getPrecedence();
    }

    String getLiteral() {
        if (!this.kind.equals(Kind.LITERAL)) {
            throw new IllegalArgumentException();
        }
        return (String) this.content;
    }
}
