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
}