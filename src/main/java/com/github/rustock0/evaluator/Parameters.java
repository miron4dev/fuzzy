package com.github.rustock0.evaluator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The parameters of an evaluator.
 *
 * @author Evgeny Mironenko.
 */
public class Parameters {

    private final List<Operator> operators;

    public Parameters() {
        this.operators = new ArrayList<>();
    }

    /**
     * Gets the supported operators.
     *
     * @return a Collection of operators.
     */
    public Collection<Operator> getOperators() {
        return this.operators;
    }

    /**
     * Adds an operator to the supported ones.
     *
     * @param operator The added operator
     */
    public void add(Operator operator) {
        this.operators.add(operator);
    }
}
