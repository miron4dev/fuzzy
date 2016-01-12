package com.github.rustock0.fuzzy;

import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class TriangularFuzzyNumberTest {

    @Test
    public void testAdd() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(1, 2, 3);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(3, 6, 9);
        assertEquals(expected, number.add(another));
    }

    @Test
    public void testSubtract() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(1, 2, 3);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-1, 2, 5);
        assertEquals(expected, number.subtract(another));
    }

    @Test
    public void testMultiply_positive_positive() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(1, 2, 3);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(2, 8, 18);
        assertEquals(expected, number.multiply(another));
    }

    @Test
    public void testMultiply_positive_negative() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(-1, 2, 3);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-6, 8, 18);
        assertEquals(expected, number.multiply(another));
    }

    @Test
    public void testMultiply_negative_positive() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(1, 2, 3);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-6, 8, 18);
        assertEquals(expected, number.multiply(another));
    }

    @Test
    public void testMultiply_negative_negative() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(-1, 2, 3);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-6, 8, 18);
        assertEquals(expected, number.multiply(another));
    }

    @Test
    public void testDivide_positive_positive() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(1, 2, 3);

        System.out.println(number);
        System.out.println(another);
        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(0.66, 2, 6);
        System.out.println(expected);
        assertEquals(expected, number.divide(another));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivide_positive_negative() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(-1, 2, 3);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(0.33, 0.5, -1.0);
        assertEquals(expected, number.divide(another));
    }

    @Test
    public void testDivide_negative_positive() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(1, 2, 3);

        System.out.println(number);
        System.out.println(another);
        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-2, 2, 6);
        System.out.println(expected);
        assertEquals(expected, number.divide(another));
    }

    @Test
    public void testDivide_negative_positive1() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-8, -6, -4);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(1, 2, 3);

        System.out.println(number);
        System.out.println(another);
        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(-8, -3, -1.33);
        System.out.println(expected);
        assertEquals(expected, number.divide(another));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivide_negative_negative() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(-2, 4, 6);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(-1, 2, 3);

        TriangularFuzzyNumber expected = new TriangularFuzzyNumber(0.66, 2, 6);
        assertEquals(expected, number.divide(another));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivide_zero() throws Exception {
        TriangularFuzzyNumber number = new TriangularFuzzyNumber(3, 1, 2);
        TriangularFuzzyNumber another = new TriangularFuzzyNumber(0, 2, 1);

        number.divide(another);
    }

    @Test
    public void testDivide_ZadehGeneralizationPrinciple() {
        TriangularFuzzyNumber triangularNumber = new TriangularFuzzyNumber(2, 4, 6);
        TriangularFuzzyNumber anotherTriangularNumber = new TriangularFuzzyNumber(1, 2, 3);

        FuzzyNumber number = new FuzzyNumber(new LinkedHashMap<Double, Double>() {{
            put(2.0, 0.0);
            put(4.0, 1.0);
            put(6.0, 0.1);
        }});

        FuzzyNumber anotherNumber = new FuzzyNumber(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(2.0, 1.0);
            put(3.0, 0.1);
        }});

        assertTrue(number.divide(anotherNumber).getSet().keySet().containsAll(triangularNumber.divide(anotherTriangularNumber).getValues()));
    }
}