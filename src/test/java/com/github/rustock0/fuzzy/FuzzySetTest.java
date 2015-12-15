package com.github.rustock0.fuzzy;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <code>@FixMethodOrder</code> is required the order to avoid collision data after <code>normalize</code> method execution.
 * JIT optimization.
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class FuzzySetTest {

    private static final FuzzySet FUZZY_SET1 = new FuzzySet(new LinkedHashMap<Double, Double>() {{
        put(1.0, 0.6);
        put(2.0, 0.5);
        put(9.0, 0.3);
        put(8.0, 0.8);
        put(4.0, 0.0);
    }});
    private static final FuzzySet FUZZY_SET2 = new FuzzySet(new LinkedHashMap<Double, Double>() {{
        put(3.0, 0.5);
        put(1.0, 1.0);
        put(5.0, 0.0);
        put(8.0, 1.0);
    }});

    @Test
    public void testGetCore() {
        List<Double> core = FUZZY_SET1.getCore();
        assertEquals(0, core.size());
    }

    @Test
    public void testGetCore2() {
        List<Double> core = FUZZY_SET2.getCore();
        assertEquals(2, core.size());
        assertEquals(1.0, core.get(0));
        assertEquals(8.0, core.get(1));
    }

    @Test
    public void testGetTransitionPoint() {
        List<Double> transitionPoint = FUZZY_SET1.getTransitionPoint();
        assertEquals(1, transitionPoint.size());
        assertEquals(2.0, transitionPoint.get(0));
    }

    @Test
    public void testGetTransitionPoint2() {
        List<Double> transitionPoint = FUZZY_SET2.getTransitionPoint();
        assertEquals(1, transitionPoint.size());
        assertEquals(3.0, transitionPoint.get(0));
    }

    @Test
    public void testGetHeight() {
        double height = FUZZY_SET1.getHeight();
        assertEquals(0.8, height);
    }

    @Test
    public void testGetHeight2() {
        double height = FUZZY_SET2.getHeight();
        assertEquals(1.0, height);
    }

    @Test
    public void testGetSupport() {
        List<Double> support = FUZZY_SET1.getSupport();
        assertEquals(4, support.size());
        assertEquals(1.0, support.get(0));
        assertEquals(2.0, support.get(1));
        assertEquals(9.0, support.get(2));
        assertEquals(8.0, support.get(3));
    }

    @Test
    public void testGetSupport2() {
        List<Double> support = FUZZY_SET2.getSupport();
        assertEquals(3, support.size());
        assertEquals(3.0, support.get(0));
        assertEquals(1.0, support.get(1));
        assertEquals(8.0, support.get(2));
    }

    @Test
    public void testNormalize() {
        assertEquals(0.8, FUZZY_SET1.getHeight());
        FuzzySet normalizedSet = FUZZY_SET1.normalize();
        System.out.println("NORMALIZED FUZZY SET:");
        System.out.println(normalizedSet);
        assertEquals(1.0, FUZZY_SET1.getHeight());
        assertEquals(0.75, normalizedSet.set.get(1.0));
        assertEquals(0.625, normalizedSet.set.get(2.0));
        assertEquals(0.375, normalizedSet.set.get(9.0));
        assertEquals(1.0, normalizedSet.set.get(8.0));
        assertEquals(0.0, normalizedSet.set.get(4.0));
    }

    @Test
    public void testNormalize2() {
        assertEquals(1.0, FUZZY_SET2.getHeight());
        FuzzySet normalizedSet = FUZZY_SET2.normalize();
        System.out.println("NORMALIZED FUZZY SET:");
        System.out.println(normalizedSet);
        assertEquals(1.0, FUZZY_SET2.getHeight());
        Assert.assertEquals(FUZZY_SET2, normalizedSet);
    }

    @Test
    public void testGetHammingDistance() {
        double hammingDistance = FUZZY_SET1.getHammingDistance(FUZZY_SET2);
        assertEquals(1.1, hammingDistance);
    }

    @Test
    public void testGetHammingDistance2() {
        FuzzySet calculator = new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.1);
            put(2.0, 0.5);
            put(9.0, 1.0);
            put(8.0, 0.0);
        }});
        double hammingDistance = calculator.getHammingDistance(new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(2.0, 0.2);
            put(9.0, 1.0);
            put(8.0, 0.7);
        }}));
        assertEquals(1.1, hammingDistance);
    }

    @Test
    public void testGetEuclideanDistance() {
        double euclideanDistance = FUZZY_SET1.getEuclideanDistance(FUZZY_SET2);
        assertEquals(0.624, euclideanDistance);
    }

    @Test
    public void testGetEuclideanDistance2() {
        FuzzySet calculator = new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(2.0, 0.8);
            put(9.0, 0.3);
            put(3.0, 0.6);
            put(8.0, 0.9);
            put(14.0, 1.0);
        }});
        double euclideanDistance = calculator.getEuclideanDistance(new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(2.0, 0.2);
            put(9.0, 0.6);
            put(3.0, 0.1);
            put(8.0, 1.0);
        }}));
        System.out.println(euclideanDistance);
        assertEquals(1.307, euclideanDistance);
    }

    @Test
    public void testGetNearestSet() {
        List<Integer> nearestSet = FUZZY_SET1.getNearestClearSet();
        List<Integer> expected = Arrays.asList(1, 0, 0, 1, 0);
        Assert.assertEquals(expected, nearestSet);
    }

    @Test
    public void testGetNearestSet2() {
        List<Integer> nearestSet = FUZZY_SET2.getNearestClearSet();
        List<Integer> expected = Arrays.asList(0, 1, 0, 1);
        Assert.assertEquals(expected, nearestSet);
    }

    @Test
    public void testGetEntropy() {
        double entropy = FUZZY_SET1.getEntropy();
        assertEquals(0.74, entropy);
    }

    @Test
    public void testGetEntropy2() {
        double entropy = FUZZY_SET2.getEntropy();
        assertEquals(0.58, entropy);
    }

    @Test
    public void testGetEntropy3() {
        FuzzySet calculator = new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.7);
            put(2.0, 0.9);
            put(3.0, 0.0);
            put(4.0, 0.6);
            put(5.0, 0.5);
            put(6.0, 1.0);
        }});
        double entropy = calculator.getEntropy();
        assertEquals(0.89, entropy);
    }

    @Test
    public void testIsConvex() {
        assertFalse(FUZZY_SET1.isConvex());
    }

    @Test
    public void testIsConvex2() {
        assertFalse(FUZZY_SET2.isConvex());
    }

    @Test
    public void testIsConvex3() {
        FuzzySet calculator = new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.5);
            put(2.0, 0.7);
            put(3.0, 0.6);
        }});
        assertTrue(calculator.isConvex());
    }

    @Test
    public void testIsConvex4() {
        FuzzySet calculator = new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.7);
            put(2.0, 0.5);
        }});
        assertTrue(calculator.isConvex());
    }

    @Test
    public void testIsConcave() {
        assertFalse(FUZZY_SET1.isConcave());
    }

    @Test
    public void testIsConcave2() {
        assertTrue(FUZZY_SET2.isConcave());
    }

    @Test
    public void testIsConcave3() {
        FuzzySet calculator = new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.5);
            put(2.0, 0.3);
            put(4.0, 0.6);
        }});
        assertTrue(calculator.isConcave());
    }

    @Test
    public void testIsConcave4() {
        FuzzySet calculator = new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.5);
            put(2.0, 0.7);
            put(4.0, 0.6);
        }});
        assertFalse(calculator.isConcave());
    }

    @Test
    public void testIsConcave5() {
        FuzzySet calculator = new FuzzySet(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.5);
            put(2.0, 0.3);
        }});
        assertTrue(calculator.isConcave());
    }

    private void assertEquals(double expected, double actual) {
        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testGetAddition() throws Exception {

        Map<Double, Double> result = FUZZY_SET1.getAddition();
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.4);
            put(2.0, 0.5);
            put(4.0, 1.0);
            put(8.0, 0.19999999999999996);
            put(9.0, 0.7);
        }};
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetUnionMax() throws Exception {

       FuzzySet result = FUZZY_SET1.getUnionMax(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 1.0);
            put(8.0, 1.0);
        }};
        Assert.assertEquals(expected, result.set);

    }

    @Test
    public void testGetIntersectionMax() throws Exception {

        FuzzySet result = FUZZY_SET1.getIntersectionMax(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.6);
            put(8.0, 0.8);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testDeductMax() throws Exception {
        FuzzySet result = FUZZY_SET1.deductMax(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(8.0, 0.0);
        }};
        Assert.assertEquals(expected, result.set);

    }

    @Test
    public void testGetUnionAlg() throws Exception {

        FuzzySet result = FUZZY_SET1.getUnionAlg(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 1.0);
            put(8.0, 1.0);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testGetIntersectionAlg() throws Exception {
        FuzzySet result = FUZZY_SET1.getIntersectionAlg(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.6);
            put(8.0, 0.8);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testDeductAlg() throws Exception {
        FuzzySet result = FUZZY_SET1.deductAlg(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(8.0, 0.0);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testSymmetricDeduction1Alg() throws Exception {
        FuzzySet result = FUZZY_SET1.symmetricDeduction1Alg(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.4);
            put(8.0, 0.19999999999999996);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testSymmetricDeduction2Alg() throws Exception {
        FuzzySet result = FUZZY_SET1.symmetricDeduction2Alg(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.2500000000000001);
            put(8.0, 0.0);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testGetUnionLim() throws Exception {
        FuzzySet result = FUZZY_SET1.getUnionLim(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.5);
            put(8.0, 0.3);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testGetIntersectionLim() throws Exception {
        FuzzySet result = FUZZY_SET1.getIntersectionLim(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.6000000000000001);
            put(8.0, 0.8);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testDeductLim() throws Exception {
        FuzzySet result = FUZZY_SET1.deductLim(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(8.0, 0.0);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testSymmetricDeduction1Lim() throws Exception {
        FuzzySet result = FUZZY_SET1.symmetricDeduction1Lim(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.3999999999999999);
            put(8.0, 0.19999999999999996);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testSymmetricDeduction2Lim() throws Exception {
        FuzzySet result = FUZZY_SET1.symmetricDeduction2Lim(FUZZY_SET2);
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(8.0, 0.0);
        }};
        Assert.assertEquals(expected, result.set);
    }

    @Test
    public void testGetConcentration() throws Exception {
        Map<Double, Double> result = FUZZY_SET1.getConcentration();
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.36);
            put(2.0, 0.25);
            put(4.0, 0.0);
            put(8.0, 0.6400000000000001);
            put(9.0, 0.09);
        }};
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetStretching() throws Exception {
        Map<Double, Double> result = FUZZY_SET1.getStretching();
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.7745966692414834);
            put(2.0, 0.7071067811865476);
            put(4.0, 0.0);
            put(8.0, 0.8944271909999159);
            put(9.0, 0.5477225575051661);
        }};
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetAlphaCut() throws Exception {
        List<Double> result = FUZZY_SET1.getAlphaCut(0.5);
        List<Double> expected = new ArrayList<Double>() {{
            add(1.0);
            add(2.0);
            add(8.0);
        }};
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetStrictAlphaCut() throws Exception {
        List<Double> result = FUZZY_SET1.getStrictAlphaCut(0.5);
        List<Double> expected = new ArrayList<Double>() {{
            add(1.0);
            add(8.0);
        }};
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testIsDominate() throws Exception {
        assertTrue(FUZZY_SET1.isDominate(FUZZY_SET2));
    }

    @Test
    public void testGetDefuzzificationCOG() throws Exception {
        double result = FUZZY_SET1.getDefuzzificationCOG();

        assertEquals(4.863636363636363, result);
    }

    @Test
    public void testGetDefuzzificationCOA() throws Exception {
        double result = FUZZY_SET1.getDefuzzificationCOA();

        assertEquals(8.0, result);
    }

    @Test
    public void testGetDefuzzificationLOM() throws Exception {
        double result = FUZZY_SET1.getDefuzzificationLOM();

        assertEquals(1.0, result);
    }

    @Test
    public void testGetDefuzzificationROM() throws Exception {
        double result = FUZZY_SET1.getDefuzzificationROM();

        assertEquals(8.0, result);
    }

    @Test
    public void testGetDefuzzificationMOM() throws Exception {
        double result = FUZZY_SET1.getDefuzzificationMOM();

        assertEquals(8.0, result);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(FUZZY_SET1.equals(FUZZY_SET1));
    }
}