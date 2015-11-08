package ru.spb.herzen.is;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>@FixMethodOrder</code> is required the order to avoid collision data after <code>normalize</code> method execution.
 * JIT optimization.
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class FuzzySetCalculatorTest {

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
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET1);
        List<Double> core = calculator.getCore();
        assertEquals(0, core.size());
    }

    @Test
    public void testGetCore2() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET2);
        List<Double> core = calculator.getCore();
        assertEquals(2, core.size());
        assertEquals(1.0, core.get(0));
        assertEquals(8.0, core.get(1));
    }

    @Test
    public void testGetTransitionPoint() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET1);
        List<Double> transitionPoint = calculator.getTransitionPoint();
        assertEquals(1, transitionPoint.size());
        assertEquals(2.0, transitionPoint.get(0));
    }

    @Test
    public void testGetTransitionPoint2() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET2);
        List<Double> transitionPoint = calculator.getTransitionPoint();
        assertEquals(1, transitionPoint.size());
        assertEquals(3.0, transitionPoint.get(0));
    }

    @Test
    public void testGetHeight() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET1);
        double height = calculator.getHeight();
        assertEquals(0.8, height);
    }

    @Test
    public void testGetHeight2() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET2);
        double height = calculator.getHeight();
        assertEquals(1.0, height);
    }

    @Test
    public void testGetSupport() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET1);
        List<Double> support = calculator.getSupport();
        assertEquals(4, support.size());
        assertEquals(1.0, support.get(0));
        assertEquals(2.0, support.get(1));
        assertEquals(9.0, support.get(2));
        assertEquals(8.0, support.get(3));
    }

    @Test
    public void testGetSupport2() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET2);
        List<Double> support = calculator.getSupport();
        assertEquals(3, support.size());
        assertEquals(3.0, support.get(0));
        assertEquals(1.0, support.get(1));
        assertEquals(8.0, support.get(2));
    }

    @Test
    public void testNormalize() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET1);
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
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET2);
        assertEquals(1.0, calculator.getHeight());
        Map<Double, Double> normalizedSet = calculator.normalize();
        System.out.println("NORMALIZED FUZZY SET:");
        System.out.println(normalizedSet);
        assertEquals(1.0, calculator.getHeight());
        Assert.assertEquals(FUZZY_SET2, normalizedSet);
    }

    @Test
    public void testGetHammingDistance() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET1);
        double hammingDistance = calculator.getHammingDistance(FUZZY_SET2);
        assertEquals(1.01, hammingDistance);
    }

    @Test
    public void testGetHammingDistance2() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(new LinkedHashMap<Double, Double>() {{
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
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET1);
        double euclideanDistance = calculator.getEuclideanDistance(FUZZY_SET2);
        assertEquals(0.624, euclideanDistance);
    }

    @Test
    public void testGetEuclideanDistance2() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(new LinkedHashMap<Double, Double>() {{
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
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET1);
        List<Integer> nearestSet = calculator.getNearestClearSet();
        List<Integer> expected = Arrays.asList(1, 0, 0, 1, 0);
        Assert.assertEquals(expected, nearestSet);
    }

    @Test
    public void testGetNearestSet2() {
        FuzzySetCalculator calculator = new FuzzySetCalculator(FUZZY_SET2);
        List<Integer> nearestSet = calculator.getNearestClearSet();
        List<Integer> expected = Arrays.asList(0, 1, 0, 1);
        Assert.assertEquals(expected, nearestSet);
    }
    
    private void assertEquals(double expected, double actual) {
        Assert.assertEquals(expected, actual, 0.1);
    }
}