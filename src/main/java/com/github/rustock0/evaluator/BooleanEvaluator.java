package com.github.rustock0.evaluator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Boolean Evaluator.
 *
 * @author Evgeny Mironenko.
 */
public class BooleanEvaluator extends AbstractEvaluator<Boolean> {

    private static final Operator NEGATION = new Operator("!", 1, 5);
    private static final Operator AND = new Operator("&", 2, 4);
    private static final Operator OR = new Operator("|", 2, 3);
    private static final Operator IMPLICATION = new Operator("->", 2, 2);
    private static final Operator EQUIVALENCE = new Operator("<->", 2, 1);

    private static final List<Operator> PARAMETERS = Arrays.asList(NEGATION, AND, OR, IMPLICATION, EQUIVALENCE);

    public BooleanEvaluator() {
        super(PARAMETERS);
    }

    public BooleanEvaluator(Map<String, Boolean> literalValues) {
        super(PARAMETERS, literalValues);
    }

    @Override
    public Boolean evaluate(Operator operator, Iterator<Boolean> operands) throws IllegalArgumentException {
        if (operator == NEGATION) {
            return !operands.next();
        } else if (operator == OR) {
            Boolean o1 = operands.next();
            Boolean o2 = operands.next();
            return o1 || o2;
        } else if (operator == AND) {
            Boolean o1 = operands.next();
            Boolean o2 = operands.next();
            return o1 && o2;
        } else if (operator == IMPLICATION) {
            Boolean o1 = operands.next();
            Boolean o2 = operands.next();
            return !(o1 && !o2);
        } else if (operator == EQUIVALENCE) {
            Boolean o1 = operands.next();
            Boolean o2 = operands.next();
            return o1 == o2;
        } else {
            throw new IllegalArgumentException("Operation is invalid! Unknown operator: " + operator);
        }
    }

    @Override
    protected Boolean toValue(String literal) throws IllegalArgumentException {
        if ("true".equals(literal) || "false".equals(literal)) {
            return Boolean.valueOf(literal);
        }
        if (!literalValues.containsKey(literal)) {
            throw new IllegalArgumentException("Expression is invalid! Unknown literal: " + literal);
        }
        return literalValues.get(literal);
    }

}
