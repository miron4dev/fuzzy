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
 * @param <T> evaluator type.
 * @author Evgeny Mironenko.
 */
public abstract class AbstractEvaluator<T> {

    private final Tokenizer tokenizer;
    private final Map<String, Operator> operators;
    protected final Map<String, T> literalValues;

    public AbstractEvaluator(List<Operator> operators) {
        this(operators, new HashMap<>());
    }

    protected AbstractEvaluator(List<Operator> operators, Map<String, T> literalValues) {
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
     * @throws IllegalArgumentException if the operation could not been evaluated.
     */
    protected abstract T evaluate(Operator operator, Iterator<T> operands) throws IllegalArgumentException;

    /**
     * Evaluates a literal (Converts it to a value).
     *
     * @param literal The literal to evaluate.
     * @return an instance of T.
     * @throws IllegalArgumentException if the literal can't be converted to a value.
     */
    protected abstract T toValue(String literal) throws IllegalArgumentException;

    /**
     * Evaluates an expression.
     *
     * @param expression The expression to evaluate.
     * @return the result of the evaluation.
     * @throws IllegalArgumentException if the expression is not correct.
     */
    public T evaluate(String expression) throws IllegalArgumentException {
        final Stack<T> values = new Stack<>(); // values stack
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

    /**
     * Evaluates the specified token and pushes it to the stack.
     *
     * @param values stack of values.
     * @param token  token for evaluation.
     */
    private void output(Stack<T> values, Token token) {
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

    /**
     * Returns an iterator of arguments based on the specified stack of values and amount of operands.
     *
     * @param values       stack of values.
     * @param operandCount amount of operands.
     * @return see description.
     */
    private Iterator<T> getArguments(Stack<T> values, int operandCount) {
        if (values.size() < operandCount) {
            throw new IllegalArgumentException("Expression is invalid!");
        }
        LinkedList<T> result = new LinkedList<>();
        for (int i = 0; i < operandCount; i++) {
            result.addFirst(values.pop());
        }
        return result.iterator();
    }

    /**
     * Transforms specified string to the instance of {@link Token}.
     *
     * @param token string token.
     * @return see description.
     */
    private Token toToken(String token) {
        if (operators.containsKey(token)) {
            Operator operator = operators.get(token);
            return Token.buildOperator(operator);
        } else {
            return Token.buildLiteral(token);
        }
    }
}
