package com.github.rustock0.evaluator;

import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class FuzzyEvaluatorTest {

    @Test
    public void testEvaluate_negation() {
        assertEquals(0.9, new FuzzyEvaluator(Collections.singletonMap("A", 0.1)).evaluate("!A"));
        assertEquals(0.4, new FuzzyEvaluator(Collections.singletonMap("A", 0.6)).evaluate("!A"));
    }

    @Test
    public void testEvaluate_and() {
        String expression = "A & B";
        assertEquals(0.2, new FuzzyEvaluator(createMap(entry("A", 0.2), entry("B", 0.4))).evaluate(expression));
        assertEquals(0.3, new FuzzyEvaluator(createMap(entry("A", 0.3), entry("B", 0.5))).evaluate(expression));
    }

    @Test
    public void testEvaluate_or() {
        String expression = "A | B";
        assertEquals(0.4, new FuzzyEvaluator(createMap(entry("A", 0.2), entry("B", 0.4))).evaluate(expression));
        assertEquals(0.5, new FuzzyEvaluator(createMap(entry("A", 0.3), entry("B", 0.5))).evaluate(expression));
    }

    @Test
    public void testEvaluate_implication() {
        String expression = "A -> B";
        assertEquals(0.8, new FuzzyEvaluator(createMap(entry("A", 0.2), entry("B", 0.4))).evaluate(expression));
        assertEquals(0.7, new FuzzyEvaluator(createMap(entry("A", 0.3), entry("B", 0.5))).evaluate(expression));
    }

    @Test
    public void testEvaluate_equivalence() {
        String expression = "A <-> B";
        assertEquals(0.9, new FuzzyEvaluator(createMap(entry("A", 0.1), entry("B", 0.9))).evaluate(expression));
        assertEquals(0.5, new FuzzyEvaluator(createMap(entry("A", 0.6), entry("B", 0.5))).evaluate(expression));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluate_unknownLiteral() {
        new FuzzyEvaluator(createMap(entry("A", 0.5))).evaluate("A | B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluate_invalidData() {
        new FuzzyEvaluator(createMap(entry("A", 0.5), entry("B", 12))).evaluate("A | B");
    }

    @Test
    public void testEvaluate() {
        assertEquals(0.6, new FuzzyEvaluator(createMap(entry("A", 0.1), entry("B", 0.8), entry("C", 0.4), entry("D", 0.9),
            entry("E", 0.8))).evaluate("A | B -> C & D <-> !E"));
    }

    //
    // Helper methods
    //

    private Map<String, Double> createMap(Map.Entry<String, Double>... entries) {
        return Stream.of(entries).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, Double> entry(String key, double value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private void assertEquals(double a1, double a2) {
        Assert.assertEquals(a1, a2, 0.0);
    }
}