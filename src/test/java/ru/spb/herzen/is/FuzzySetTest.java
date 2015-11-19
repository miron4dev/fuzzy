package ru.spb.herzen.is;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <code>@FixMethodOrder</code> is required the order to avoid collision data after <code>normalize</code> method execution.
 * JIT optimization.
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class FuzzySetTest {

    private static final Map<Double, Double> FUZZY_SET1 = new LinkedHashMap<Double, Double>() {{
        put(1.0, 0.6);
        put(2.0, 0.5);
        put(9.0, 0.3);
        put(8.0, 0.8);
        put(4.0, 0.0);
    }};
    private static final Map<Double, Double> FUZZY_SET2 = new LinkedHashMap<Double, Double>() {{
        put(3.0, 0.5);
        put(1.0, 1.0);
        put(5.0, 0.0);
        put(8.0, 1.0);
    }};

    @Test
    public void testGetCore() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        List<Double> core = calculator.getCore();
        assertEquals(0, core.size());
    }

    @Test
    public void testGetCore2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        List<Double> core = calculator.getCore();
        assertEquals(2, core.size());
        assertEquals(1.0, core.get(0));
        assertEquals(8.0, core.get(1));
    }

    @Test
    public void testGetTransitionPoint() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        List<Double> transitionPoint = calculator.getTransitionPoint();
        assertEquals(1, transitionPoint.size());
        assertEquals(2.0, transitionPoint.get(0));
    }

    @Test
    public void testGetTransitionPoint2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        List<Double> transitionPoint = calculator.getTransitionPoint();
        assertEquals(1, transitionPoint.size());
        assertEquals(3.0, transitionPoint.get(0));
    }

    @Test
    public void testGetHeight() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        double height = calculator.getHeight();
        assertEquals(0.8, height);
    }

    @Test
    public void testGetHeight2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        double height = calculator.getHeight();
        assertEquals(1.0, height);
    }

    @Test
    public void testGetSupport() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        List<Double> support = calculator.getSupport();
        assertEquals(4, support.size());
        assertEquals(1.0, support.get(0));
        assertEquals(2.0, support.get(1));
        assertEquals(9.0, support.get(2));
        assertEquals(8.0, support.get(3));
    }

    @Test
    public void testGetSupport2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        List<Double> support = calculator.getSupport();
        assertEquals(3, support.size());
        assertEquals(3.0, support.get(0));
        assertEquals(1.0, support.get(1));
        assertEquals(8.0, support.get(2));
    }

    @Test
    public void testNormalize() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        assertEquals(0.8, calculator.getHeight());
        Map<Double, Double> normalizedSet = calculator.normalize();
        System.out.println("NORMALIZED FUZZY SET:");
        System.out.println(normalizedSet);
        assertEquals(1.0, calculator.getHeight());
        assertEquals(0.75, normalizedSet.get(1.0));
        assertEquals(0.625, normalizedSet.get(2.0));
        assertEquals(0.375, normalizedSet.get(9.0));
        assertEquals(1.0, normalizedSet.get(8.0));
        assertEquals(0.0, normalizedSet.get(4.0));
    }

    @Test
    public void testNormalize2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        assertEquals(1.0, calculator.getHeight());
        Map<Double, Double> normalizedSet = calculator.normalize();
        System.out.println("NORMALIZED FUZZY SET:");
        System.out.println(normalizedSet);
        assertEquals(1.0, calculator.getHeight());
        Assert.assertEquals(FUZZY_SET2, normalizedSet);
    }

    @Test
    public void testGetHammingDistance() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        double hammingDistance = calculator.getHammingDistance(FUZZY_SET2);
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
        double hammingDistance = calculator.getHammingDistance(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(2.0, 0.2);
            put(9.0, 1.0);
            put(8.0, 0.7);
        }});
        assertEquals(1.1, hammingDistance);
    }

    @Test
    public void testGetEuclideanDistance() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        double euclideanDistance = calculator.getEuclideanDistance(FUZZY_SET2);
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
        double euclideanDistance = calculator.getEuclideanDistance(new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.0);
            put(2.0, 0.2);
            put(9.0, 0.6);
            put(3.0, 0.1);
            put(8.0, 1.0);
        }});
        System.out.println(euclideanDistance);
        assertEquals(1.307, euclideanDistance);
    }

    @Test
    public void testGetNearestSet() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        List<Integer> nearestSet = calculator.getNearestClearSet();
        List<Integer> expected = Arrays.asList(1, 0, 0, 1, 0);
        Assert.assertEquals(expected, nearestSet);
    }

    @Test
    public void testGetNearestSet2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        List<Integer> nearestSet = calculator.getNearestClearSet();
        List<Integer> expected = Arrays.asList(0, 1, 0, 1);
        Assert.assertEquals(expected, nearestSet);
    }

    @Test
    public void testGetEntropy() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        double entropy = calculator.getEntropy();
        assertEquals(0.74, entropy);
    }

    @Test
    public void testGetEntropy2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        double entropy = calculator.getEntropy();
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
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        assertFalse(calculator.isConvex());
    }

    @Test
    public void testIsConvex2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        assertFalse(calculator.isConvex());
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
        FuzzySet calculator = new FuzzySet(FUZZY_SET1);
        assertFalse(calculator.isConcave());
    }

    @Test
    public void testIsConcave2() {
        FuzzySet calculator = new FuzzySet(FUZZY_SET2);
        assertTrue(calculator.isConcave());
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
}