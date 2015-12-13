package ru.spb.herzen.is;

/**
 * Implementation of methods to work with Triangular Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class TriangularFuzzyNumber {

    private double modalValue;
    private double alpha;
    private double beta;

    public TriangularFuzzyNumber(double modalValue, double alpha, double beta) {
        this.modalValue = modalValue;
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
        return new TriangularFuzzyNumber(modalValue + anotherNumber.modalValue, alpha + anotherNumber.alpha, beta + anotherNumber.beta);
    }

    /**
     * Returns a result of subtraction current and specified numbers.
     * At(a1, alpha1, beta1) - Bt(a2, alpha2, beta2) = Ct(a1-a2, alpha1+beta2, beta1+alpha2)
     *
     * @param anotherNumber an another triangular fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber subtract(TriangularFuzzyNumber anotherNumber) {
        return new TriangularFuzzyNumber(modalValue - anotherNumber.modalValue, alpha + anotherNumber.beta, beta + anotherNumber.alpha);
    }

    /**
     * Returns a result of multiplication current and specified numbers.
     * At(a1, alpha1, beta1) * Bt(a2, alpha2, beta2) = Ct(a1*a2, a1*alpha2 + a2*alpha1, a1*beta2 + a2*beta1)
     *
     * @param anotherNumber an another triangular fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber multiply(TriangularFuzzyNumber anotherNumber) {
        double newModalValue = modalValue * anotherNumber.modalValue;
        double newAlpha = modalValue * anotherNumber.alpha + anotherNumber.modalValue * alpha;
        double newBeta = modalValue * anotherNumber.beta + anotherNumber.modalValue * beta;
        return new TriangularFuzzyNumber(newModalValue, newAlpha, newBeta);
    }

    /**
     * Returns a result of division current and specified numbers.
     * At(a1, alpha1, beta1) + Bt(a2, alpha2, beta2)
     *
     * @param anotherNumber an another triangular fuzzy number.
     * @return see description.
     */
    public TriangularFuzzyNumber divide(TriangularFuzzyNumber anotherNumber) {
        double newModalValue = modalValue / anotherNumber.modalValue;
        double newAlpha = (modalValue * anotherNumber.beta + anotherNumber.modalValue * alpha) / (Math.pow(anotherNumber.alpha, 2));
        double newBeta = (modalValue * anotherNumber.alpha + anotherNumber.modalValue * beta) / (Math.pow(anotherNumber.alpha, 2));
        return new TriangularFuzzyNumber(newModalValue, newAlpha, newBeta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TriangularFuzzyNumber that = (TriangularFuzzyNumber) o;

        if (Double.compare(that.modalValue, modalValue) != 0) return false;
        if (Double.compare(that.alpha, alpha) != 0) return false;
        return Double.compare(that.beta, beta) == 0;
    }
}
