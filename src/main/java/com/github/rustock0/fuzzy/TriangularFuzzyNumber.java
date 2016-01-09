package com.github.rustock0.fuzzy;

import java.util.function.Function;

/**
 * Implementation of methods to work with Triangular Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class TriangularFuzzyNumber {

    protected double lowerModal;
    protected double upperModal;
    protected double alpha;
    protected double beta;

    public TriangularFuzzyNumber(double lowerModal, double alpha, double beta) {
        this.lowerModal = lowerModal;
        this.upperModal = lowerModal;
        this.alpha = alpha;
        this.beta = beta;
    }

    public TriangularFuzzyNumber(double lowerModal, double upperModal, double alpha, double beta) {
        this.lowerModal = lowerModal;
        this.upperModal = upperModal;
        this.alpha = alpha;
        this.beta = beta;
    }

    /**
     * Returns a result of addition current and specified numbers.
     * At(a1, b1, alpha1, beta1) + Bt(a2, b2, alpha2, beta2) = Ct(a1+a2, b1+b2, alpha1+alpha2, beta1+beta2)
     *
     * @param anotherNumber an another trapezoidal fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber add(TriangularFuzzyNumber anotherNumber) {
        return new TriangularFuzzyNumber(lowerModal + anotherNumber.lowerModal, upperModal + anotherNumber.upperModal,
            alpha + anotherNumber.alpha, beta + anotherNumber.beta);
    }

    /**
     * Returns a result of subtraction current and specified numbers.
     * At(a1, b1, alpha1, beta1) - Bt(a2, b2, alpha2, beta2) = Ct(a1-a2, b1-b2, alpha1+beta2, beta1+alpha2)
     *
     * @param anotherNumber an another trapezoidal fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber subtract(TriangularFuzzyNumber anotherNumber) {
        return new TriangularFuzzyNumber(lowerModal - anotherNumber.lowerModal, upperModal - anotherNumber.upperModal,
            alpha + anotherNumber.beta, beta + anotherNumber.alpha);
    }

    /**
     * Returns a result of multiplication current and specified numbers.
     * 1) if all modal values are positive
     * At(a1, b1, alpha1, beta1) * Bt(a2, b2, alpha2, beta2) = Ct(a1*a2, b1*b2, a1*alpha2+a2*alpha1, b1*beta2+b2*beta1)
     * 2) if all modal values are negative
     * At(a1, alpha1, beta1) * Bt(a2, alpha2, beta2) = Ct(a1*a2, b1*b2, -a2*beta1-a1*beta2, -b2*alpha1 - b1*alpha2)
     * 3) else
     * At(a1, alpha1, beta1) * Bt(a2, alpha2, beta2) = Ct(a1*a2, b1*b2, (+-)(a2*alpha1 - a1*beta2), (+-)(b2*beta1 - b1*alpha2))
     *
     * @param anotherNumber an another trapezoidal fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber multiply(TriangularFuzzyNumber anotherNumber) {
        double newLowerModal;
        double newUpperModal;
        double newAlpha;
        double newBeta;
        newLowerModal = lowerModal * anotherNumber.lowerModal;
        newUpperModal = upperModal * anotherNumber.upperModal;
        if (isPositive(lowerModal, anotherNumber.lowerModal, upperModal, anotherNumber.upperModal)) {
            newAlpha = lowerModal * anotherNumber.alpha + anotherNumber.lowerModal * alpha;
            newBeta = upperModal * anotherNumber.beta + anotherNumber.upperModal * beta;
        } else if (isNegative(lowerModal, anotherNumber.lowerModal, upperModal, anotherNumber.upperModal)) {
            newAlpha = (-1) * anotherNumber.lowerModal * beta - lowerModal * anotherNumber.beta;
            newBeta = (-1) * anotherNumber.upperModal * alpha - upperModal * anotherNumber.alpha;
        } else {
            if (lowerModal > 0 && anotherNumber.lowerModal < 0) {
                newAlpha = -(anotherNumber.lowerModal * beta - lowerModal * anotherNumber.beta);
            } else {
                newAlpha = anotherNumber.lowerModal * alpha - lowerModal * anotherNumber.beta;
            }
            if (upperModal > 0 && anotherNumber.upperModal < 0) {
                newBeta = -(anotherNumber.upperModal * alpha - upperModal * anotherNumber.alpha);
            } else {
                newBeta = anotherNumber.upperModal * beta - upperModal * anotherNumber.alpha;
            }
        }
        return new TriangularFuzzyNumber(newLowerModal, newUpperModal, newAlpha, newBeta);
    }

    /**
     * Returns a result of division current and specified numbers.
     * 1) if all modal values are positive
     * At(a1, b1, alpha1, beta1) / Bt(a2, b2, alpha2, beta2) = Ct(a1/b2, b1/a2, (a1*beta2+b2*alpha1)/b2^2, (b1*alpha2 + a2*beta1)/a2^2)
     * 2) if all modal values are negative
     * At(a1, alpha1, beta1) / Bt(a2, alpha2, beta2) = Ct(a1*a2, b1*b2, -a2*beta1-a1*beta2, -b2*alpha1 - b1*alpha2)/a2^2)
     * 3) else
     * At(a1, alpha1, beta1) / Bt(a2, alpha2, beta2) = Ct(a1*a2, b1*b2, (+-)(a2*alpha1 - a1*beta2), (+-)(b2*beta1 - b1*alpha2)/a2^2))
     *
     * @param anotherNumber an another trapezoidal fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber divide(TriangularFuzzyNumber anotherNumber) {
        double newLowerModal = lowerModal / anotherNumber.upperModal;
        double newUpperModal = upperModal / anotherNumber.lowerModal;
        double newAlpha;
        double newBeta;
        if (isPositive(lowerModal, anotherNumber.lowerModal, upperModal, anotherNumber.upperModal)) {
            newAlpha = lowerModal * anotherNumber.beta + anotherNumber.upperModal * alpha;
            newBeta = upperModal * anotherNumber.alpha + anotherNumber.lowerModal * beta;
        } else if (isNegative(lowerModal, anotherNumber.lowerModal, upperModal, anotherNumber.upperModal)) {
            newAlpha = (-1) * lowerModal * anotherNumber.beta - anotherNumber.upperModal * alpha;
            newBeta = (-1) * upperModal * anotherNumber.alpha - anotherNumber.lowerModal * beta;
        } else {
            if (lowerModal > 0 && anotherNumber.lowerModal < 0) {
                newAlpha = -(lowerModal * anotherNumber.beta - anotherNumber.upperModal * alpha);
            } else {
                newAlpha = lowerModal * anotherNumber.beta - anotherNumber.upperModal * alpha;
            }
            if (upperModal > 0 && anotherNumber.upperModal < 0) {
                newBeta = -(upperModal * anotherNumber.alpha - anotherNumber.lowerModal * beta);
            } else {
                newBeta = upperModal * anotherNumber.alpha - anotherNumber.lowerModal * beta;
            }
        }
        return new TriangularFuzzyNumber(newLowerModal, newUpperModal, newAlpha / Math.pow(anotherNumber.upperModal, 2),
            newBeta / Math.pow(anotherNumber.lowerModal, 2));
    }

    /**
     * Returns true if the all specified set contains only positive digits.
     *
     * @param digits set of digits.
     * @return see description.
     */
    private boolean isPositive(Double... digits) {
        return compare(x -> x > 0, digits);
    }

    /**
     * Returns true if the all specified set contains only negative digits.
     *
     * @param digits set of digits.
     * @return see description.
     */
    private boolean isNegative(Double... digits) {
        return compare(x -> x < 0, digits);
    }

    private boolean compare(Function<Double, Boolean> function, Double... digits) {
        for (Double digit : digits) {
            if (!function.apply(digit)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TriangularFuzzyNumber that = (TriangularFuzzyNumber) o;

        if (Double.compare(that.lowerModal, lowerModal) != 0) return false;
        if (Double.compare(that.upperModal, upperModal) != 0) return false;
        if (Double.compare(that.alpha, alpha) != 0) return false;
        return Double.compare(that.beta, beta) == 0;
    }

    @Override
    public String toString() {
        return "TriangularFuzzyNumber{" +
            "lowerModal=" + lowerModal +
            ", alpha=" + alpha +
            ", beta=" + beta +
            '}';
    }
}
