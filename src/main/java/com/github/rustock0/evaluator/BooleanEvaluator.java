package com.github.rustock0.evaluator;

import java.util.Iterator;

/**
 * Implementation of Boolean Evaluator.
 *
 * @author Evgeny Mironenko.
 */
public class BooleanEvaluator extends AbstractEvaluator {

    private static final Operator NEGATION = new Operator("!", 1, 5);
    private static final Operator AND = new Operator("&", 2, 4);
    private static final Operator OR = new Operator("|", 2, 3);
    private static final Operator IMPLICATION = new Operator("->", 2, 2);
    private static final Operator EQUIVALENCE = new Operator("<->", 2, 1);

    private static final Parameters PARAMETERS;

    static {
        PARAMETERS = new Parameters();
        PARAMETERS.add(NEGATION);
        PARAMETERS.add(AND);
        PARAMETERS.add(OR);
        PARAMETERS.add(IMPLICATION);
        PARAMETERS.add(EQUIVALENCE);
    }

    public BooleanEvaluator() {
        super(PARAMETERS);
    }

    @Override
    protected Boolean toValue(String literal) {
        return Boolean.valueOf(literal);
    }

    @Override
    public Boolean evaluate(Operator operator, Iterator<Boolean> operands) {
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
            throw new IllegalArgumentException("Expression is invalid! Unknown operator: " + operator);
        }
    }

}
