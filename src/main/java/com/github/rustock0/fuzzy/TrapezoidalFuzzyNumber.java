package com.github.rustock0.fuzzy;

import java.util.Arrays;
import java.util.Collections;

/**
 * Implementation of methods to work with Trapezoidal Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class TrapezoidalFuzzyNumber {

    private double a;
    private double b;
    private double c;
    private double d;
    private String interval;

    public TrapezoidalFuzzyNumber(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.interval = "(" + a + ";" + d + ")";

        if (a > b || b > c || c > d) {
            throw new IllegalArgumentException("Invalid trapezoidal number: " + this);
        }
    }

    /**
     * Returns a result of addition current and specified numbers.
     * According the Zadeh extension principe:
     * A(a1,b1,c1,d1)+B(a2,b2,c2,d2)=C(a1+a2, b1+b2, c1+c2, d1+d2)
     *
     * @param number an another number.
     * @return see description.
     */
    public TrapezoidalFuzzyNumber add(TrapezoidalFuzzyNumber number) {
        return new TrapezoidalFuzzyNumber(a + number.a, b + number.b, c + number.c, d + number.d);
    }

    /**
     * Returns a result of subtraction current and specified numbers.
     * According the Zadeh extension principe:
     * A(a1,b1,c1,d1)-B(a2,b2,c2,d2)=C(a1-d2, b1-c2, c1-b2, d1-a2)
     *
     * @param number an another number.
     * @return see description.
     */
    public TrapezoidalFuzzyNumber subtract(TrapezoidalFuzzyNumber number) {
        return new TrapezoidalFuzzyNumber(a - number.d, b - number.c, c - number.b, d - number.a);
    }

    /**
     * Returns a result of multiplication current and specified numbers.
     * According the Zadeh extension principe:
     * A(a1,b1,c1,d1)*B(a2,b2,c2,d2)=C(min{ad}, min{bc}, max{bc}, max{ad})
     *
     * @param number an another number.
     * @return see description.
     */
    public TrapezoidalFuzzyNumber multiply(TrapezoidalFuzzyNumber number) {
        double a1a2 = a * number.a;
        double a1d2 = a * number.d;
        double d1a2 = d * number.a;
        double d1d2 = d * number.d;

        double b1b2 = b * number.b;
        double b1c2 = b * number.c;
        double c1b2 = c * number.b;
        double c1c2 = c * number.c;
        double a = Collections.min(Arrays.asList(a1a2, a1d2, d1a2, d1d2));
        double d = Collections.max(Arrays.asList(a1a2, a1d2, d1a2, d1d2));
        double b = Collections.min(Arrays.asList(b1b2, b1c2, c1b2, c1c2));
        double c = Collections.max(Arrays.asList(b1b2, b1c2, c1b2, c1c2));
        return new TrapezoidalFuzzyNumber(a, b, c, d);
    }

    /**
     * Returns a result of addition current and specified numbers.
     * According the Zadeh extension principe:
     * A(a1,b1,c1,d1)/B(a2,b2,c2,d2)=A(a1,b1,c1,d1)*B(d2,c2,b2,a2)
     *
     * @param number an another number.
     * @return see description.
     */
    public TrapezoidalFuzzyNumber divide(TrapezoidalFuzzyNumber number) {
        return multiply(number.invert());
    }

    /**
     * Inverts the current fuzzy number.
     *
     * @return inverted number.
     */
    private TrapezoidalFuzzyNumber invert() {
        return new TrapezoidalFuzzyNumber(1.0 / d, 1.0 / c, 1.0 / b, 1.0 / a);
    }

    public String toCortege() {
        return "FuzzyCortege(modalA, modalB, alpha, beta) = (" + b + ", " + c + ", " + (b - a) + " ," + (d - c) + ")";
    }

    @Override
    public String toString() {
        return "TrapezoidalFuzzyNumber{" +
            "a=" + a +
            ", b=" + b +
            ", c=" + c +
            ", d=" + d +
            ", interval='" + interval + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrapezoidalFuzzyNumber)) return false;

        TrapezoidalFuzzyNumber that = (TrapezoidalFuzzyNumber) o;

        return almostEquals(a, that.a) && almostEquals(b, that.b) && almostEquals(c, that.c) && almostEquals(d, that.d);
    }

    private boolean almostEquals(double a, double b) {
        return a == b || Math.abs(a - b) < 0.01;
    }
}
