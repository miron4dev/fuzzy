package com.github.rustock0.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Abstract implementation of Evaluator.
 *
 * @author Evgeny Mironenko.
 */
public abstract class AbstractEvaluator {

    private final Tokenizer tokenizer;
    private final Map<String, Operator> operators;
    protected final Map<String, Boolean> literalValues;

    protected AbstractEvaluator(List<Operator> operators, Map<String, Boolean> literalValues) {
        final List<String> delimiters = new ArrayList<>();
        this.operators = new HashMap<>();
        for (Operator operator : operators) {
            delimiters.add(operator.getSymbol());
            this.operators.put(operator.getSymbol(), operator);
        }
        tokenizer = new Tokenizer(delimiters);
        this.literalValues = literalValues;
    }

    /**
     * Evaluates an operation.
     *
     * @param operator The operator
     * @param operands The operands
     * @return The result of the operation
     */
    protected abstract Boolean evaluate(Operator operator, Iterator<Boolean> operands);

    /**
     * Evaluates a literal (Converts it to a value).
     *
     * @param literal The literal to evaluate.
     * @return an instance of T.
     * @throws IllegalArgumentException if the literal can't be converted to a value.
     */
    protected abstract Boolean toValue(String literal);

    /**
     * Evaluates an expression.
     *
     * @param expression The expression to evaluate.
     * @return the result of the evaluation.
     * @throws IllegalArgumentException if the expression is not correct.
     */
    public Boolean evaluate(String expression) {
        final Stack<Boolean> values = new Stack<>(); // values stack
        final Stack<Token> stack = new Stack<>(); // operator stack
        final Iterator<String> tokens = tokenizer.tokenize(expression);
        Token previous = null;
        while (tokens.hasNext()) {
            String strToken = tokens.next();
            final Token token = toToken(strToken);
            if (token.isOperator()) {
                while (!stack.isEmpty()) {
                    Token sc = stack.peek();
                    if (sc.isOperator() && ((token.getPrecedence() <= sc.getPrecedence()) || (token.getPrecedence() < sc.getPrecedence()))) {
                        output(values, stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(token);
            } else {
                if ((previous != null) && previous.isLiteral()) {
                    throw new IllegalArgumentException("A literal can't follow another literal");
                }
                output(values, token);
            }
            previous = token;
        }
        while (!stack.isEmpty()) {
            output(values, stack.pop());
        }
        if (values.size() != 1) {
            throw new IllegalArgumentException("Expression is invalid!");
        }
        return values.pop();
    }

    private void output(Stack<Boolean> values, Token token) {
        if (token.isLiteral()) {
            String literal = token.getLiteral();
            values.push(toValue(literal));
        } else if (token.isOperator()) {
            Operator operator = token.getOperator();
            values.push(evaluate(operator, getArguments(values, operator.getOperandCount())));
        } else {
            throw new IllegalArgumentException("Expression is invalid!");
        }
    }

    private Iterator<Boolean> getArguments(Stack<Boolean> values, int nb) {
        if (values.size() < nb) {
            throw new IllegalArgumentException("Expression is invalid!");
        }
        LinkedList<Boolean> result = new LinkedList<>();
        for (int i = 0; i < nb; i++) {
            result.addFirst(values.pop());
        }
        return result.iterator();
    }

    private Token toToken(String token) {
        if (operators.containsKey(token)) {
            Operator operator = operators.get(token);
            return Token.buildOperator(operator);
        } else {
            return Token.buildLiteral(token);
        }
    }
}
