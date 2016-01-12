package com.github.rustock0.evaluator;

import java.util.*;

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
    private final Map<String, BracketPair> expressionBrackets = new LinkedHashMap<String, BracketPair>() {{
        put("(", BracketPair.PARENTHESES);
        put(")", BracketPair.PARENTHESES);
    }};

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
        for (final BracketPair pair : expressionBrackets.values()) {
            delimiters.add(pair.getOpen());
            delimiters.add(pair.getClose());
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
    public T evaluate(String expression) {
        final Stack<T> values = new Stack<T>(); // values stack
        final Stack<Token> operators = new Stack<>(); // operator stack
        final Iterator<String> tokens = tokenizer.tokenize(expression);
        Token previous = null;
        while (tokens.hasNext()) {
            // read one token from the input stream
            String strToken = tokens.next();
            final Token token = toToken(strToken);
            if (token.isOpenBracket()) {
                // If the token is a left parenthesis, then push it onto the stack.
                operators.push(token);
                if (!expressionBrackets.containsKey(token.getBrackets().getOpen())) {
                    throw new IllegalArgumentException("Invalid bracket in expression: "+strToken);
                }
            } else if (token.isCloseBracket()) {
                if (previous==null) {
                    throw new IllegalArgumentException("expression can't start with a close bracket");
                }
                BracketPair brackets = token.getBrackets();
                boolean openBracketFound = false;
                while (!operators.isEmpty()) {
                    Token sc = operators.pop();
                    if (sc.isOpenBracket()) {
                        if (sc.getBrackets().equals(brackets)) {
                            openBracketFound = true;
                            break;
                        } else {
                            throw new IllegalArgumentException("Invalid parenthesis match "+sc.getBrackets().getOpen()+brackets.getClose());
                        }
                    } else {
                        output(values, sc);
                    }
                }
                if (!openBracketFound) {
                    throw new IllegalArgumentException("Parentheses mismatched");
                }
            } else if (token.isOperator()) {
                while (!operators.isEmpty()) {
                    Token sc = operators.peek();
                    if (sc.isOperator() && (((token.getPrecedence() <= sc.getPrecedence())) || (token.getPrecedence() < sc.getPrecedence()))) {
                        output(values, operators.pop());
                    } else {
                        break;
                    }
                }
                // push op1 onto the stack.
                operators.push(token);
            } else {
                if ((previous!=null) && previous.isLiteral()) {
                    throw new IllegalArgumentException("A literal can't follow another literal");
                }
                output(values, token);
            }
            previous = token;
        }
        while (!operators.isEmpty()) {
            Token sc = operators.pop();
            if (sc.isOpenBracket() || sc.isCloseBracket()) {
                throw new IllegalArgumentException("Parentheses mismatched");
            }
            output(values, sc);
        }
        if (values.size()!=1) {
            throw new IllegalArgumentException();
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
            final BracketPair brackets = getBracketPair(token);
            if (brackets!=null) {
                if (brackets.getOpen().equals(token)) {
                    return Token.buildOpenToken(brackets);
                } else {
                    return Token.buildCloseToken(brackets);
                }
            } else {
                return Token.buildLiteral(token);
            }
        }
    }

    private BracketPair getBracketPair(String token) {
        return expressionBrackets.get(token);
    }
}
