package ru.spb.herzen.is;

/**
 * Implementation of methods to work with Triangular Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class TriangularFuzzyNumber {

    protected double lowerModal;
    protected double alpha;
    protected double beta;

    public TriangularFuzzyNumber(double lowerModal, double alpha, double beta) {
        this.lowerModal = lowerModal;
        this.alpha = alpha;
        this.beta = beta;
    }

    /**
     * Returns a result of addition current and specified numbers.
     * At(a1, alpha1, beta1) + Bt(a2, alpha2, beta2) = Ct(a1+a2, alpha1+alpha2, beta1+beta2)
     *
     * @param anotherNumber an another triangular fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber add(TriangularFuzzyNumber anotherNumber) {
        return new TriangularFuzzyNumber(lowerModal + anotherNumber.lowerModal, alpha + anotherNumber.alpha, beta + anotherNumber.beta);
    }

    /**
     * Returns a result of subtraction current and specified numbers.
     * At(a1, alpha1, beta1) - Bt(a2, alpha2, beta2) = Ct(a1-a2, alpha1+beta2, beta1+alpha2)
     *
     * @param anotherNumber an another triangular fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber subtract(TriangularFuzzyNumber anotherNumber) {
        return new TriangularFuzzyNumber(lowerModal - anotherNumber.lowerModal, alpha + anotherNumber.beta, beta + anotherNumber.alpha);
    }

    /**
     * Returns a result of multiplication current and specified numbers.
     * At(a1, alpha1, beta1) * Bt(a2, alpha2, beta2) = Ct(a1*a2, a1*alpha2 + a2*alpha1, a1*beta2 + a2*beta1)
     *
     * @param anotherNumber an another triangular fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber multiply(TriangularFuzzyNumber anotherNumber) {
        double newModalValue = lowerModal * anotherNumber.lowerModal;
        double newAlpha = lowerModal * anotherNumber.alpha + anotherNumber.lowerModal * alpha;
        double newBeta = lowerModal * anotherNumber.beta + anotherNumber.lowerModal * beta;
        return new TriangularFuzzyNumber(newModalValue, newAlpha, newBeta);
    }

    /**
     * Returns a result of division current and specified numbers.
     * At(a1, alpha1, beta1) + Bt(a2, alpha2, beta2) = Ct(a1/a2, (a1*beta2+a2*alpha1)/a2^2, (a1*alpha2+a2*beta1)/a2^2)
     *
     * @param anotherNumber an another triangular fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber divide(TriangularFuzzyNumber anotherNumber) {
        double newModalValue = lowerModal / anotherNumber.lowerModal;
        double newAlpha = (lowerModal * anotherNumber.beta + anotherNumber.lowerModal * alpha) / (Math.pow(anotherNumber.alpha, 2));
        double newBeta = (lowerModal * anotherNumber.alpha + anotherNumber.lowerModal * beta) / (Math.pow(anotherNumber.alpha, 2));
        return new TriangularFuzzyNumber(newModalValue, newAlpha, newBeta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TriangularFuzzyNumber that = (TriangularFuzzyNumber) o;

        if (Double.compare(that.lowerModal, lowerModal) != 0) return false;
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
