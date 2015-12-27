package com.github.rustock0.fuzzy;

import org.junit.Test;

import static org.junit.Assert.*;

public class FuzzyRelationTest {

    @Test
    public void testIsReflexive_true() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.2, 0.3}, {0.2, 1.0, 0.3}, {0.3, 0.2, 1.0}});
        assertTrue(fuzzyRelation.isReflexive());
    }

    @Test
    public void testIsReflexive_false() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.2, 0.3}, {0.2, 0.0, 0.3}, {0.3, 0.2, 1.0}});
        assertFalse(fuzzyRelation.isReflexive());
    }

    @Test
    public void testIsAntiReflexive_true() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{0.0, 0.2, 0.3}, {0.2, 0.0, 0.3}, {0.3, 0.2, 0.0}});
        assertTrue(fuzzyRelation.isAntiReflexive());
    }

    @Test
    public void testIsAntiReflexive_false() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.2, 0.3}, {0.2, 0.0, 0.3}, {0.3, 0.2, 1.0}});
        assertFalse(fuzzyRelation.isAntiReflexive());
    }

    @Test
    public void testIsSymmetric_true() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.3, 0.0}, {0.3, 0.2, 0.6}, {0.0, 0.6, 0.5}});

        assertTrue(fuzzyRelation.isSymmetric());
    }

    @Test
    public void testIsSymmetric_false() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.2, 0.3}, {0.2, 0.0, 0.3}, {0.3, 0.2, 1.0}});

        assertFalse(fuzzyRelation.isSymmetric());
    }

    @Test
    public void testIsAsymmetric_true() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{0.0, 0.3, 0.0}, {0.0, 0.0, 0.0}, {0.0, 0.6, 0.0}});

        assertTrue(fuzzyRelation.isAsymmetric());
    }

    @Test
    public void testIsAsymmetric_false() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.3}, {0.0, 0.0, 0.3}, {0.3, 0.2, 1.0}});

        assertFalse(fuzzyRelation.isAsymmetric());
    }

    @Test
    public void testIsAntiSymmetric_true() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.0}, {0.3, 0.2, 0.6}, {0.0, 0.0, 0.5}});

        assertTrue(fuzzyRelation.isAntiSymmetric());
    }

    @Test
    public void testIsAntiSymmetric_false() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.3}, {0.0, 0.0, 0.3}, {0.3, 0.2, 1.0}});

        assertFalse(fuzzyRelation.isAntiSymmetric());
    }

    @Test
    public void testAddition() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.3}, {0.0, 0.0, 0.3}, {0.3, 0.2, 1.0}});
        FuzzyRelation anotherRelation = new FuzzyRelation(new double[][]{{0.0, 0.5, 0.2}, {0.1, 0.6, 0.8}, {0.9, 0.5, 0.0}});

        FuzzyRelation expected = new FuzzyRelation(new double[][]{{1.0, 0.5, 0.3}, {0.1, 0.6, 0.8}, {0.9, 0.5, 1.0}});
        assertEquals(expected, fuzzyRelation.addition(anotherRelation));
    }

    @Test
    public void testIntersection() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.3}, {0.0, 0.0, 0.3}, {0.3, 0.2, 1.0}});
        FuzzyRelation anotherRelation = new FuzzyRelation(new double[][]{{0.0, 0.5, 0.2}, {0.1, 0.6, 0.8}, {0.9, 0.5, 0.0}});

        FuzzyRelation expected = new FuzzyRelation(new double[][]{{0.0, 0.0, 0.2}, {0.0, 0.0, 0.3}, {0.3, 0.2, 0.0}});
        assertEquals(expected, fuzzyRelation.intersection(anotherRelation));
    }

    @Test
    public void testUnion() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.3}, {0.0, 0.0, 0.3}, {0.3, 0.2, 1.0}});
        FuzzyRelation anotherRelation = new FuzzyRelation(new double[][]{{0.0, 0.5, 0.2}, {0.1, 0.6, 0.8}, {0.9, 0.5, 0.0}});

        FuzzyRelation expected = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.3}, {0.0, 0.0, 0.2}, {0.01, 0.2, 1.0}});
        assertEquals(expected, fuzzyRelation.union(anotherRelation));
    }

    @Test
    public void testDeduct() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.3}, {0.0, 0.0, 0.3}, {0.3, 0.2, 1.0}});
        FuzzyRelation anotherRelation = new FuzzyRelation(new double[][]{{0.0, 0.5, 0.2}, {0.1, 0.6, 0.8}, {0.9, 0.5, 0.0}});

        FuzzyRelation expected = new FuzzyRelation(new double[][]{{1.0, 0.5, 0.3}, {0.1, 0.6, 0.7}, {0.7, 0.5, 1.0}});
        assertEquals(expected, fuzzyRelation.deduct(anotherRelation));
    }

    @Test
    public void testDeductSymmetric() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1.0, 0.0, 0.3}, {0.0, 0.0, 0.3}, {0.3, 0.2, 1.0}});

        FuzzyRelation expected = new FuzzyRelation(new double[][]{{0.0, 1.0, 0.7}, {1.0, 1.0, 0.7}, {0.7, 0.8, 0.0}});
        assertEquals(expected, fuzzyRelation.deductSymmetric());
    }
}