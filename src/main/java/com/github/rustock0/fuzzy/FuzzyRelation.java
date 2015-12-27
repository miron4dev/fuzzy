package com.github.rustock0.fuzzy;

/**
 * Implementation of methods to work with Fuzzy Relations.
 *
 * @author Evgeny Mironenko
 */
public class FuzzyRelation {

    private final double[][] matrix;

    public FuzzyRelation(double[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Returns true if the current fuzzy relation is reflexive.
     *
     * @return see description.
     */
    public boolean isReflexive() {
        return compareMainDiagonal(1.0);
    }

    /**
     * Returns true if the current fuzzy relation is anti-reflexive.
     *
     * @return see description.
     */
    public boolean isAntiReflexive() {
        return compareMainDiagonal(0.0);
    }

    /**
     * Returns true if all elements of main diagonal are equal specified digits.
     *
     * @param digit some digit.
     * @return see description.
     */
    private boolean compareMainDiagonal(double digit) {
        for (int i = 0, j = 0; i < matrix.length; i++, j++) {
            if (matrix[i][j] != digit) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the current fuzzy relation is symmetric.
     *
     * @return see description.
     */
    public boolean isSymmetric() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the current fuzzy relation is asymmetric.
     *
     * @return see description.
     */
    public boolean isAsymmetric() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (Math.min(matrix[i][j], matrix[j][i]) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the current fuzzy relation is anti-symmetric.
     *
     * @return see description.
     */
    public boolean isAntiSymmetric() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i != j && Math.min(matrix[i][j], matrix[j][i]) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
