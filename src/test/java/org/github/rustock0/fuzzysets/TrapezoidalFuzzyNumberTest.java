package org.github.rustock0.fuzzysets;

import org.junit.Test;

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
    public void testMultiply() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(3, 10, 7, 9);
        assertEquals(expected, number.multiply(anotherNumber));
    }

    @Test
    public void testDivide() throws Exception {
        TrapezoidalFuzzyNumber number = new TrapezoidalFuzzyNumber(3, 5, 1, 2);
        TrapezoidalFuzzyNumber anotherNumber = new TrapezoidalFuzzyNumber(1, 2, 2, 1);

        TrapezoidalFuzzyNumber expected = new TrapezoidalFuzzyNumber(1.5, 5, 1.25, 12);
        assertEquals(expected, number.divide(anotherNumber));
    }
}