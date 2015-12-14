package org.github.rustock0.fuzzysets;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class FuzzyNumberTest {

    @Test
    public void testAdd() {
        FuzzyNumber fuzzyNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(2.0, 0.7);
            put(3.0, 1.0);
            put(4.0, 0.6);
        }});
        FuzzyNumber anotherNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(3.0, 0.8);
            put(4.0, 1.0);
            put(6.0, 0.5);
        }});

        Map<Double, Double> expected = new TreeMap<Double, Double>() {{
            put(5.0, 0.7);
            put(6.0, 0.8);
            put(7.0, 1.0);
            put(8.0, 0.6);
            put(9.0, 0.5);
            put(10.0, 0.5);
        }};
        assertEquals(expected, fuzzyNumber.add(anotherNumber));
        assertTrue(fuzzyNumber.isConvex());
        assertTrue(fuzzyNumber.getHeight() == 1.0);
    }

    @Test
    public void testSubtract() {
        FuzzyNumber fuzzyNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(2.0, 0.7);
            put(3.0, 1.0);
            put(4.0, 0.6);
        }});
        FuzzyNumber anotherNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(3.0, 0.8);
            put(4.0, 1.0);
            put(6.0, 0.5);
        }});

        Map<Double, Double> expected = new TreeMap<Double, Double>() {{
            put(-4.0, 0.5);
            put(-3.0, 0.5);
            put(-2.0, 0.7);
            put(-1.0, 1.0);
            put(0.0, 0.8);
            put(1.0, 0.6);
        }};
        assertEquals(expected, fuzzyNumber.subtract(anotherNumber));
        assertTrue(fuzzyNumber.isConvex());
        assertTrue(fuzzyNumber.getHeight() == 1.0);
    }

    @Test
    public void testMultiply() {
        FuzzyNumber fuzzyNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(2.0, 0.7);
            put(3.0, 1.0);
            put(4.0, 0.6);
        }});
        FuzzyNumber anotherNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(3.0, 0.8);
            put(4.0, 1.0);
            put(6.0, 0.5);
        }});

        Map<Double, Double> expected = new TreeMap<Double, Double>() {{
            put(6.0, 0.7);
            put(8.0, 0.7);
            put(9.0, 0.8);
            put(12.0, 1.0);
            put(16.0, 0.6);
            put(18.0, 0.5);
            put(24.0, 0.5);
        }};
        assertEquals(expected, fuzzyNumber.multiply(anotherNumber));
        assertTrue(fuzzyNumber.isConvex());
        assertTrue(fuzzyNumber.getHeight() == 1.0);
    }

    @Test
    public void testDivide() {
        FuzzyNumber fuzzyNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(2.0, 0.7);
            put(3.0, 1.0);
            put(4.0, 0.6);
        }});
        FuzzyNumber anotherNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(3.0, 0.8);
            put(4.0, 1.0);
            put(6.0, 0.5);
        }});

        Map<Double, Double> expected = new TreeMap<Double, Double>() {{
            put(0.3333333333333333, 0.5);
            put(0.5, 0.7);
            put(0.6666666666666666, 0.7);
            put(0.75, 1.0);
            put(1.0, 0.8);
            put(1.3333333333333333, 0.6);
        }};
        assertEquals(expected, fuzzyNumber.divide(anotherNumber));
        assertTrue(fuzzyNumber.isConvex());
        assertTrue(fuzzyNumber.getHeight() == 1.0);
    }

    @Test
    public void testExtraMaximum() {
        FuzzyNumber fuzzyNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(0.0, 0.2);
            put(1.0, 1.0);
            put(2.0, 0.2);
        }});
        FuzzyNumber anotherNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(-1.0, 0.1);
            put(0.0, 1.0);
            put(1.0, 0.1);
        }});
        Map<Double, Double> expected = new TreeMap<Double, Double>() {{
            put(0.0, 0.2);
            put(1.0, 1.0);
            put(2.0, 0.2);
        }};
        assertEquals(expected, fuzzyNumber.extraMaximum(anotherNumber));
    }

    @Test
    public void testExtraMinimum() {
        FuzzyNumber fuzzyNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(0.0, 0.2);
            put(1.0, 1.0);
            put(2.0, 0.2);
        }});
        FuzzyNumber anotherNumber = new FuzzyNumber(new TreeMap<Double, Double>() {{
            put(-1.0, 0.1);
            put(0.0, 1.0);
            put(1.0, 0.1);
        }});
        Map<Double, Double> expected = new TreeMap<Double, Double>() {{
            put(-1.0, 0.1);
            put(0.0, 1.0);
            put(1.0, 0.1);
        }};
        assertEquals(expected, fuzzyNumber.extraMinimum(anotherNumber));
    }

}