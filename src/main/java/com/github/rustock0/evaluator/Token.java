package com.github.rustock0.evaluator;

/**
 * A token of expression.
 *
 * @author Evgeny Mironenko.
 */
class Token {

    private enum Kind {
        OPEN_BRACKET,
        CLOSE_BRACKET,
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

    static Token buildOpenToken(BracketPair pair) {
        return new Token(Kind.OPEN_BRACKET, pair);
    }

    static Token buildCloseToken(BracketPair pair) {
        return new Token(Kind.CLOSE_BRACKET, pair);
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


    BracketPair getBrackets() {
        return (BracketPair) this.content;
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

    /** Tests whether the token is an open bracket.
     * @return true if the token is an open bracket
     */
    public boolean isOpenBracket() {
        return kind.equals(Kind.OPEN_BRACKET);
    }

    /** Tests whether the token is a close bracket.
     * @return true if the token is a close bracket
     */
    public boolean isCloseBracket() {
        return kind.equals(Kind.CLOSE_BRACKET);
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
