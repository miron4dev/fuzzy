package com.github.rustock0.fuzzy;

import java.util.Arrays;
import java.util.Collections;

/**
 * Implementation of methods to work with Triangular Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class TriangularFuzzyNumber {

    private double a;
    private double b;
    private double c;
    private String interval;

    public TriangularFuzzyNumber(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.interval = "(" + a + ";" + c + ")";
        if (a > b || b > c) {
            throw new IllegalArgumentException("Invalid triangular number: " + this);
        }
    }

    public TriangularFuzzyNumber add(TriangularFuzzyNumber number) {
        return new TriangularFuzzyNumber(a + number.a, b + number.b, c + number.c);
    }

    public TriangularFuzzyNumber subtract(TriangularFuzzyNumber number) {
        return new TriangularFuzzyNumber(a - number.c, b - number.b, c - number.a);
    }

    public TriangularFuzzyNumber multiply(TriangularFuzzyNumber number) {
        double a1a2 = a * number.a;
        double a1c2 = a * number.c;
        double c1a2 = c * number.a;
        double c1c2 = c * number.c;
        double a = Collections.min(Arrays.asList(a1a2, a1c2, c1a2, c1c2));
        double c = Collections.max(Arrays.asList(a1a2, a1c2, c1a2, c1c2));
        double b = this.b * number.b;
        return new TriangularFuzzyNumber(a, b, c);
    }

    public TriangularFuzzyNumber divide(TriangularFuzzyNumber number) {
        return multiply(number.invert());
    }

    private TriangularFuzzyNumber invert() {
        return new TriangularFuzzyNumber(1.0 / c, 1.0 / b, 1.0 / a);
    }

    public String toCortege() {
        return "FuzzyNumber(modalA, alpha, beta) = (" + b + ", " + (b - a) + " ," + (c - b) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TriangularFuzzyNumber)) return false;

        TriangularFuzzyNumber that = (TriangularFuzzyNumber) o;

        return almostEquals(a, that.a) && almostEquals(b, that.b) && almostEquals(c, that.c);
    }

    private boolean almostEquals(double a, double b){
        return a == b || Math.abs(a - b) < 0.01;
    }

    @Override
    public String toString() {
        return "TriangularFuzzyNumber{" +
            "a=" + a +
            ", b=" + b +
            ", c=" + c +
            ", interval='" + interval + '\'' +
            '}';
    }
}
