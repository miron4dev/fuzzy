package com.github.rustock0.fuzzy;

import org.junit.Test;

import static java.lang.Double.POSITIVE_INFINITY;
import static org.junit.Assert.*;

public class TrapezoidalFuzzyNumberTest {

    @Test
    public void testAdd() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(4, 7, 3, 3);
        assertEquals(expected, number.add(anotherNumber));
    }

    @Test
    public void testSubtract() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(2, 3, 2, 4);
        assertEquals(expected, number.subtract(anotherNumber));
    }

    @Test
    public void testMultiply_positive_positive() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(3, 10, 7, 9);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testMultiply_positive_negative() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(-1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-3, 10, 5, -6);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testMultiply_negative_positive() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(-3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-3, 10, 4, -6);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testMultiply_negative_negative() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(-3, -5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(-1, -2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(3, 10, 5, 12);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testDivide_positive_positive() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(1.5, 5, 1.25, 12);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test
    public void testDivide_positive_negative() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, -2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-1.5, 5, 1.25, -8);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test
    public void testDivide_negative_positive() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(-3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-1.5, 5, -1.25, 8);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test
    public void testDivide_negative_negative() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(-3, -5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(-1, -2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(1.5, 5, 1.25, 12);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test
    public void testDivide_zero_a() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(0, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(1.5, POSITIVE_INFINITY, 0.25, POSITIVE_INFINITY);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test
    public void testDivide_zero_b() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 0, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(POSITIVE_INFINITY, 5, POSITIVE_INFINITY, 8);
        assertEquals(expected, number.divide(anotherNumber));
    }
}