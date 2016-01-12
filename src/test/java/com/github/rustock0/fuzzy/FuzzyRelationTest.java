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

    @Test
    public void testIsTransitive() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1, 0, 1, 1}, {1, 0, 1, 1}, {0, 0, 0, 0}, {0, 0, 1, 1}});
        FuzzyRelation fuzzyRelation2 = new FuzzyRelation(new double[][]{{0, 1, 1, 1}, {0, 0, 1, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}});
        FuzzyRelation fuzzyRelation3 = new FuzzyRelation(new double[][]{{0, 1}, {0, 0}});

        assertTrue(fuzzyRelation.isTransitive());
        assertTrue(fuzzyRelation2.isTransitive());
        assertTrue(fuzzyRelation3.isTransitive());
    }

    @Test
    public void testGetComposition() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{0.2, 0.5}, {0.6, 1.0}});
        FuzzyRelation anotherRelation = new FuzzyRelation(new double[][]{{0.3, 0.6, 0.8}, {0.7, 0.9, 0.4}});

        FuzzyRelation fuzzyRelation2 = new FuzzyRelation(new double[][]{{0.4, 0.1}, {0.2, 1.0}});
        FuzzyRelation anotherRelation2 = new FuzzyRelation(new double[][]{{0.3, 0.3, 0.8}, {0.7, 0.6, 0.9}});

        FuzzyRelation fuzzyRelation3 = new FuzzyRelation(new double[][]{{1.0, 1}, {0.5, 0.9}});
        FuzzyRelation anotherRelation3 = new FuzzyRelation(new double[][]{{0.3, 0.3, 0.8}, {0.7, 0.6, 0.9}});

        FuzzyRelation expected = new FuzzyRelation(new double[][]{{0.5, 0.5, 0.4}, {0.7, 0.9, 0.6}});
        assertEquals(expected, fuzzyRelation.getComposition(anotherRelation));

        FuzzyRelation expected2 = new FuzzyRelation(new double[][]{{0.3, 0.3, 0.4}, {0.7, 0.6, 0.9}});
        assertEquals(expected2, fuzzyRelation2.getComposition(anotherRelation2));

        FuzzyRelation expected3 = new FuzzyRelation(new double[][]{{0.7, 0.6, 0.9}, {0.7, 0.6, 0.9}});
        assertEquals(expected3, fuzzyRelation3.getComposition(anotherRelation3));
    }

    @Test
    public void testGetComposition_set() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{0.5, 0.7}, {0.2, 1.0}, {0.9, 0.3}});
        double[] muSet = new double[]{0.4, 1.0, 0.6};

        FuzzyRelation fuzzyRelation2 = new FuzzyRelation(new double[][]{{0.5, 0.6}, {0.2, 0.0}, {0.2, 0.3}});
        double[] muSet2 = new double[]{0.2, 0.0, 0.1};

        FuzzyRelation fuzzyRelation3 = new FuzzyRelation(new double[][]{{0.2, 0.7}, {0.1, 1.0}, {0.5, 0.4}});
        double[] muSet3 = new double[]{0.2, 1.0, 0.1};

        double[] expected = new double[]{0.6, 1.0};
        double[] expected2 = new double[]{0.2, 0.2};
        double[] expected3 = new double[]{0.2, 1.0};
        assertArrayEquals(expected, fuzzyRelation.getComposition(muSet), 0.0);
        assertArrayEquals(expected2, fuzzyRelation2.getComposition(muSet2), 0.0);
        assertArrayEquals(expected3, fuzzyRelation3.getComposition(muSet3), 0.0);
    }

    @Test
    public void testIsTransitiveClosure() throws Exception {
        FuzzyRelation fuzzyRelation = new FuzzyRelation(new double[][]{{1, 0, 1, 1}, {1, 0, 1, 1}, {0, 0, 0, 0}, {0, 0, 1, 1}});
        FuzzyRelation fuzzyRelation2 = new FuzzyRelation(new double[][]{{0, 1, 1, 1}, {0, 0, 1, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}});
        FuzzyRelation fuzzyRelation3 = new FuzzyRelation(new double[][]{{0, 1}, {0, 0}});
        FuzzyRelation fuzzyRelation4 = new FuzzyRelation(new double[][]{{1, 0.8, 0.4, 0.2, 0}, {0.8, 1, 0.1, 0.7, 0.2}, {0.4, 0.1, 1, 0.6, 0.5}, {0.2, 0.7, 0.6, 1, 0}, {0, 0.2, 0.5, 0, 1}});

        assertTrue(fuzzyRelation.isTransitiveClosure());
        assertTrue(fuzzyRelation2.isTransitiveClosure());
        assertTrue(fuzzyRelation3.isTransitiveClosure());
        assertTrue(fuzzyRelation4.isTransitiveClosure());
    }
}