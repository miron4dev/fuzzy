package com.github.rustock0.fuzzy;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrapezoidalFuzzyNumberTest {

    @Test
    public void testAdd() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(3, 8, 11, 17);
        assertEquals(expected, number.add(anotherNumber));
    }

    @Test
    public void testSubtract() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-7, 0, 3, 7);
        assertEquals(expected, number.subtract(anotherNumber));
    }

    @Test
    public void testMultiply_positive_positive() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(2, 15, 30, 72);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testMultiply_positive_negative() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(-2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-18, 15, 30, 72);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testMultiply_negative_positive() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(-1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-8, 15, 30, 72);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testMultiply_negative_negative() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(-1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(-2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-18, 15, 30, 72);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testDivide_positive_positive() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(0.125, 1, 2, 4.5);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivide_positive_negative() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(-2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(0.125, 1, 2, 4.5);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test
    public void testDivide_negative_positive() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(-1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(-0.5, 1, 2, 4.5);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivide_negative_negative() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(-1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(-2, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(0.125, 1, 2, 4.5);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test
    public void testDivide_zero_a() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(0, 3, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(0.125, 1, 2, Double.POSITIVE_INFINITY);
        assertEquals(expected, number.divide(anotherNumber));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivide_zero_b() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(1, 5, 6, 9);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(2, 0, 5, 8);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(0.125, 1, 2, 4.5);
        assertEquals(expected, number.divide(anotherNumber));
    }
}