package com.github.rustock0.fuzzy;

/**
 * Implementation of methods to work with Trapezoidal Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class TrapezoidalFuzzyNumber extends TriangularFuzzyNumber {

    public TrapezoidalFuzzyNumber(double lowerModal, double upperModal, double alpha, double beta) {
        super(lowerModal, upperModal, alpha, beta);
    }

    /**
     * @see TriangularFuzzyNumber#add(TriangularFuzzyNumber)
     */
    public TrapezoidalFuzzyNumber add(TrapezoidalFuzzyNumber anotherNumber) {
        TriangularFuzzyNumber result = super.add(anotherNumber);
        return new TrapezoidalFuzzyNumber(result.lowerModal, result.upperModal, result.alpha, result.beta);
    }

    /**
     * @see TriangularFuzzyNumber#subtract(TriangularFuzzyNumber)
     */
    public TrapezoidalFuzzyNumber subtract(TrapezoidalFuzzyNumber anotherNumber) {
        TriangularFuzzyNumber result = super.subtract(anotherNumber);
        return new TrapezoidalFuzzyNumber(result.lowerModal, result.upperModal, result.alpha, result.beta);
    }

    /**
     * @see TriangularFuzzyNumber#multiply(TriangularFuzzyNumber)
     */
    public TrapezoidalFuzzyNumber multiply(TrapezoidalFuzzyNumber anotherNumber) {
        TriangularFuzzyNumber result = super.multiply(anotherNumber);
        return new TrapezoidalFuzzyNumber(result.lowerModal, result.upperModal, result.alpha, result.beta);
    }

    /**
     * @see TriangularFuzzyNumber#divide(TriangularFuzzyNumber)
     */
    public TrapezoidalFuzzyNumber divide(TrapezoidalFuzzyNumber anotherNumber) {
        TriangularFuzzyNumber result = super.divide(anotherNumber);
        return new TrapezoidalFuzzyNumber(result.lowerModal, result.upperModal, result.alpha, result.beta);
    }

    @Override
    public String toString() {
        return "TrapezoidalFuzzyNumber{" +
            "lowerModal=" + lowerModal +
            ", upperModal=" + upperModal +
            ", alpha=" + alpha +
            ", beta=" + beta +
            '}';
    }
}
