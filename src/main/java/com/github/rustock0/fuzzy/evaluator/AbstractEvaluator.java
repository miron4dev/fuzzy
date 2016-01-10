package com.github.rustock0.fuzzy.evaluator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Abstract implementation of Evaluator.
 *
 * @author Evgeny Mironenko.
 */
public abstract class AbstractEvaluator {

    private final Tokenizer tokenizer;
    private final Map<String, List<Operator>> operators;

    protected AbstractEvaluator(Parameters parameters) {
        final List<String> delimiters = new ArrayList<>();
        this.operators = new HashMap<>();
        for (Operator ope : parameters.getOperators()) {
            delimiters.add(ope.getSymbol());
            List<Operator> known = this.operators.get(ope.getSymbol());
            if (known == null) {
                known = new ArrayList<>();
                this.operators.put(ope.getSymbol(), known);
            }
            known.add(ope);
        }
        tokenizer = new Tokenizer(delimiters);
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
        final Deque<Boolean> values = new ArrayDeque<>(); // values stack
        final Deque<Token> stack = new ArrayDeque<>(); // operator stack
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

    private void output(Deque<Boolean> values, Token token) {
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

    private Iterator<Boolean> getArguments(Deque<Boolean> values, int nb) {
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
            List<Operator> list = operators.get(token);
            return Token.buildOperator(list.get(0));
        } else {
            return Token.buildLiteral(token);
        }
    }
}
