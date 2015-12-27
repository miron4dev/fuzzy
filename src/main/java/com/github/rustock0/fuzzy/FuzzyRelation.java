package com.github.rustock0.fuzzy;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

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
        return checkAllElements(Objects::equals);
    }

    /**
     * Returns true if the current fuzzy relation is asymmetric.
     *
     * @return see description.
     */
    public boolean isAsymmetric() {
        return checkAllElements((x, y) -> Math.min(x, y) == 0);
    }

    /**
     * Returns true if the specified function is applicable for all elements in the current fuzzy relation.
     *
     * @param function some boolean function.
     * @return see description.
     */
    private boolean checkAllElements(BiFunction<Double, Double, Boolean> function) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (!function.apply(matrix[i][j], matrix[j][i])) {
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

    /**
     * Returns an addition of the current and specified fuzzy relations.
     *
     * @param anotherRelation an another fuzzy relation.
     * @return see description.
     */
    public FuzzyRelation addition(FuzzyRelation anotherRelation) {
        return action(anotherRelation, Math::max);
    }

    /**
     * Returns an intersection of the current and specified fuzzy relations.
     *
     * @param anotherRelation an another fuzzy relation.
     * @return see description.
     */
    public FuzzyRelation intersection(FuzzyRelation anotherRelation) {
        return action(anotherRelation, Math::min);
    }

    /**
     * Returns an union of the current and specified fuzzy relations.
     *
     * @param anotherRelation an another fuzzy relation.
     * @return see description.
     */
    public FuzzyRelation union(FuzzyRelation anotherRelation) {
        return action(anotherRelation, (x, y) -> Math.min(x, 1 - y));
    }

    /**
     * Deducts specified fuzzy relation from the current fuzzy relation and returns the result.
     *
     * @param anotherRelation an another fuzzy relation.
     * @return see description.
     */
    public FuzzyRelation deduct(FuzzyRelation anotherRelation) {
        return action(anotherRelation, (x, y) -> Math.max(Math.min(x, 1 - y), Math.min(1 - x, y)));
    }


    /**
     * Returns a result of symmetric deduction for the current fuzzy relation.
     *
     * @return see description.
     */
    public FuzzyRelation deductSymmetric() {
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = 1 - matrix[i][j];
            }
        }
        return new FuzzyRelation(result);
    }

    /**
     * Returns a result of applying the specified function for every element of the current and specified fuzzy relations.
     *
     * @param anotherRelation an another fuzzy relation.
     * @param function        some boolean function.
     * @return see description.
     */
    private FuzzyRelation action(FuzzyRelation anotherRelation, BiFunction<Double, Double, Double> function) {
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = function.apply(matrix[i][j], anotherRelation.matrix[i][j]);
            }
        }
        return new FuzzyRelation(result);
    }

    @Override
    public String toString() {
        return "FuzzyRelation{" +
            "matrix=" + Arrays.deepToString(matrix) +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FuzzyRelation)) return false;

        FuzzyRelation that = (FuzzyRelation) o;

        return almostEquals(matrix, that.matrix);
    }

    /**
     * Returns true if the specified arrays are almost equal.
     *
     * @param d1 array 1.
     * @param d2 array 2.
     * @return see description.
     */
    private boolean almostEquals(double[][] d1, double[][] d2) {
        for (int i = 0; i < d1.length; i++) {
            for (int j = 0; j < d1.length; j++) {
                double v1 = d1[i][j];
                double v2 = d2[i][j];
                if (Math.abs(v1 - v2) > 0.1) {
                    return false;
                }
            }
        }
        return true;
    }
}
