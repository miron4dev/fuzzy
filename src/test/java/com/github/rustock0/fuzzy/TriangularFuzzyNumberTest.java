package com.github.rustock0.fuzzy;

import org.junit.Test;

import static java.lang.Double.POSITIVE_INFINITY;
import static org.junit.Assert.*;

public class TriangularFuzzyNumberTest {

    @Test
    public void testAdd() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(5, 3, 3);
        assertEquals(expected, number.add(another));
    }

    @Test
    public void testSubtract() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(1, 2, 4);
        assertEquals(expected, number.subtract(another));
    }

    @Test
    public void testMultiply_positive_positive() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(6, 8, 7);
        assertEquals(expected, number.multiply(another));
    }

    @Test
    public void testMultiply_positive_negative() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(-2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-6, 7, 8);
        assertEquals(expected, number.multiply(another));
    }

    @Test
    public void testMultiply_negative_positive() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-6, 5, 10);
        assertEquals(expected, number.multiply(another));
    }

    @Test
    public void testMultiply_negative_negative() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(-2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(6, 7, 8);
        assertEquals(expected, number.multiply(another));
    }

    @Test
    public void testDivide_positive_positive() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(1.5, 1.25, 2.5);
        assertEquals(expected, number.divide(another));
    }

    @Test
    public void testDivide_positive_negative() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(-2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-1.5, -1.25, -2.5);
        assertEquals(expected, number.divide(another));
    }

    @Test
    public void testDivide_negative_positive() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-1.5, -1.25, -2.5);
        assertEquals(expected, number.divide(another));
    }

    @Test
    public void testDivide_negative_negative() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(-2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(1.5, 1.25, 2.5);
        assertEquals(expected, number.divide(another));
    }

    @Test
    public void testDivide_zero() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(0, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(POSITIVE_INFINITY, POSITIVE_INFINITY, POSITIVE_INFINITY);
        assertEquals(expected, number.divide(another));
    }

    @Test
    public void testDivide_multiplication() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(2, 2, 1);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(1.5, 1.25, 2.5);
        assertEquals(expected, number.divide(another));
        assertEquals(number, another.multiply(expected));
    }
}