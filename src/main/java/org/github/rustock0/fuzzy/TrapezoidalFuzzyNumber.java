package org.github.rustock0.fuzzy;

/**
 * Implementation of methods to work with Trapezoidal Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class TrapezoidalFuzzyNumber extends TriangularFuzzyNumber {

    private double upperModal;

    public TrapezoidalFuzzyNumber(double lowerModal, double upperModal, double alpha, double beta) {
        super(lowerModal, alpha, beta);
        this.upperModal = upperModal;
    }

    /**
     * Returns a result of addition current and specified numbers.
     * At(a1, b1, alpha1, beta1) + Bt(a2, b2, alpha2, beta2) = Ct(a1+a2, b1+b2, alpha1+alpha2, beta1+beta2)
     *
     * @param anotherNumber an another trapezoidal fuzzy number.
     * @return see description.
     */
    public TrapezoidalFuzzyNumber add(TrapezoidalFuzzyNumber anotherNumber) {
        return new TrapezoidalFuzzyNumber(lowerModal + anotherNumber.lowerModal, upperModal + anotherNumber.upperModal,
            alpha + anotherNumber.alpha, beta + anotherNumber.beta);
    }

    /**
     * Returns a result of subtraction current and specified numbers.
     * At(a1, b1, alpha1, beta1) - Bt(a2, b2, alpha2, beta2) = Ct(a1-a2, b1-b2, alpha1+beta2, beta1+alpha2)
     *
     * @param anotherNumber an another trapezoidal fuzzy number.
     * @return see description.
     */
    public TrapezoidalFuzzyNumber subtract(TrapezoidalFuzzyNumber anotherNumber) {
        return new TrapezoidalFuzzyNumber(lowerModal - anotherNumber.lowerModal, upperModal - anotherNumber.upperModal,
            alpha + anotherNumber.beta, beta + anotherNumber.alpha);
    }

    /**
     * Returns a result of multiplication current and specified numbers.
     * At(a1, b1, alpha1, beta1) * Bt(a2, b2, alpha2, beta2) = Ct(a1*a2, b1*b2, a1*alpha2+a2*alpha1, b1*beta2+b2*beta1)
     *
     * @param anotherNumber an another trapezoidal fuzzy number.
     * @return see description.
     */
    public TrapezoidalFuzzyNumber multiply(TrapezoidalFuzzyNumber anotherNumber) {
        double newLowerModal = lowerModal * anotherNumber.lowerModal;
        double newUpperModal = upperModal * anotherNumber.upperModal;
        double newAlpha = lowerModal * anotherNumber.alpha + anotherNumber.lowerModal * alpha;
        double newBeta = upperModal * anotherNumber.beta + anotherNumber.upperModal * beta;
        return new TrapezoidalFuzzyNumber(newLowerModal, newUpperModal, newAlpha, newBeta);
    }

    /**
     * Returns a result of division current and specified numbers.
     * At(a1, b1, alpha1, beta1) / Bt(a2, b2, alpha2, beta2) = Ct(a1/b2, b1/a2, (a1*beta2+b2*alpha1)/b2^2, (b1*alpha2 + a2*beta1)/a2^2)
     *
     * @param anotherNumber an another trapezoidal fuzzy number.
     * @return see description.
     */
    public TrapezoidalFuzzyNumber divide(TrapezoidalFuzzyNumber anotherNumber) {
        double newLowerModal = lowerModal / anotherNumber.upperModal;
        double newUpperModal = upperModal / anotherNumber.lowerModal;
        double newAlpha = (lowerModal * anotherNumber.beta + anotherNumber.upperModal * alpha) / Math.pow(anotherNumber.upperModal, 2);
        double newBeta = (upperModal * anotherNumber.alpha + anotherNumber.lowerModal * beta) / Math.pow(anotherNumber.lowerModal, 2);
        return new TrapezoidalFuzzyNumber(newLowerModal, newUpperModal, newAlpha, newBeta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrapezoidalFuzzyNumber)) return false;
        if (!super.equals(o)) return false;

        TrapezoidalFuzzyNumber that = (TrapezoidalFuzzyNumber) o;

        return Double.compare(that.upperModal, upperModal) == 0;
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
