package com.github.rustock0.evaluator;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Implementation of Fuzzy Evaluator.
 *
 * @author Evgeny Mironenko.
 */
public class FuzzyEvaluator extends AbstractEvaluator<Double> {

    private static final Operator NEGATION = new Operator("!", 1, Operator.Associativity.LEFT, 5);
    private static final Operator AND = new Operator("&", 2, Operator.Associativity.LEFT, 4);
    private static final Operator OR = new Operator("|", 2, Operator.Associativity.LEFT, 3);
    private static final Operator IMPLICATION = new Operator("->", 2, Operator.Associativity.RIGHT, 2);
    private static final Operator EQUIVALENCE = new Operator("<->", 2, Operator.Associativity.LEFT, 1);

    private static final Parameters PARAMETERS;

    static {
        PARAMETERS = new Parameters();
        PARAMETERS.add(NEGATION);
        PARAMETERS.add(AND);
        PARAMETERS.add(OR);
        PARAMETERS.add(IMPLICATION);
        PARAMETERS.add(EQUIVALENCE);
    }
    private final Map<String, Double> literalValues;

    public FuzzyEvaluator(Map<String, Double> literalValues) {
        super(PARAMETERS);
        validate(literalValues.values());
        this.literalValues = literalValues;
    }

    @Override
    protected Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) throws IllegalArgumentException {
        if (operator == NEGATION) {
            return negation(operands.next());
        } else if (operator == OR) {
            Double o1 = operands.next();
            Double o2 = operands.next();
            return Math.max(o1, o2);
        } else if (operator == AND) {
            Double o1 = operands.next();
            Double o2 = operands.next();
            return Math.min(o1, o2);
        } else if (operator == IMPLICATION) {
            Double o1 = operands.next();
            Double o2 = operands.next();
            return Math.max(Math.min(o1, o2), negation(o1));
        } else if (operator == EQUIVALENCE) {
            Double o1 = operands.next();
            Double o2 = operands.next();
            return Math.min(Math.max(negation(o1), o2), Math.max(negation(o1), negation(o2)));
        } else {
            throw new IllegalArgumentException("Expression is invalid! Unknown operator: " + operator);
        }
    }

    /**
     * Validates the specified degrees of truth.
     *
     * @param values degrees of truth.
     * @throws IllegalArgumentException if validation was failed.
     */
    private void validate(Collection<Double> values) throws IllegalArgumentException {
        for (Double value : values) {
            if (value > 1.0 || value < 0) {
                throw new IllegalArgumentException("Expression is invalid! Degree of truth: " + value);
            }
        }
    }

    /**
     * Returns a negation of the specified operand.
     *
     * @param operand operand.
     * @return see description.
     */
    private Double negation(Double operand) {
        return 1 - operand;
    }

    @Override
    protected Double toValue(String literal, Object evaluationContext) {
        if (!literalValues.containsKey(literal)) {
            throw new IllegalArgumentException("Expression is invalid! Unknown literal: " + literal);
        }
        return literalValues.get(literal);
    }
}
