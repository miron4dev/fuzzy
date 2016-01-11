package com.github.rustock0.evaluator;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class BooleanEvaluatorTest {

    private final BooleanEvaluator defaultEvaluator = new BooleanEvaluator(null);

    @Test
    public void testEvaluate_negation() {
        assertFalse(new BooleanEvaluator(Collections.singletonMap("A", true)).evaluate("!A"));
        assertTrue(new BooleanEvaluator(Collections.singletonMap("A", false)).evaluate("!A"));
    }

    @Test
    public void testEvaluate_and() {
        String expression = "A & B";
        assertTrue(new BooleanEvaluator(createMap(entry("A", true), entry("B", true))).evaluate(expression));
        assertFalse(new BooleanEvaluator(createMap(entry("A", true), entry("B", false))).evaluate(expression));
        assertFalse(new BooleanEvaluator(createMap(entry("A", false), entry("B", true))).evaluate(expression));
        assertFalse(new BooleanEvaluator(createMap(entry("A", false), entry("B", false))).evaluate(expression));
    }

    @Test
    public void testEvaluate_or() {
        String expression = "A | B";
        assertTrue(new BooleanEvaluator(createMap(entry("A", true), entry("B", true))).evaluate(expression));
        assertTrue(new BooleanEvaluator(createMap(entry("A", true), entry("B", false))).evaluate(expression));
        assertTrue(new BooleanEvaluator(createMap(entry("A", false), entry("B", true))).evaluate(expression));
        assertFalse(new BooleanEvaluator(createMap(entry("A", false), entry("B", false))).evaluate(expression));
    }

    @Test
    public void testEvaluate_implication() {
        String expression = "A -> B";
        assertTrue(new BooleanEvaluator(createMap(entry("A", true), entry("B", true))).evaluate(expression));
        assertFalse(new BooleanEvaluator(createMap(entry("A", true), entry("B", false))).evaluate(expression));
        assertTrue(new BooleanEvaluator(createMap(entry("A", false), entry("B", true))).evaluate(expression));
        assertTrue(new BooleanEvaluator(createMap(entry("A", false), entry("B", false))).evaluate(expression));
    }

    @Test
    public void testEvaluate_equivalence() {
        String expression = "A <-> B";
        assertTrue(new BooleanEvaluator(createMap(entry("A", true), entry("B", true))).evaluate(expression));
        assertFalse(new BooleanEvaluator(createMap(entry("A", true), entry("B", false))).evaluate(expression));
        assertFalse(new BooleanEvaluator(createMap(entry("A", false), entry("B", true))).evaluate(expression));
        assertTrue(new BooleanEvaluator(createMap(entry("A", false), entry("B", false))).evaluate(expression));
    }

    @Test
    public void testEvaluate_boolean() {
        assertTrue(defaultEvaluator.evaluate("true | false -> false & true <-> false"));
        assertFalse(defaultEvaluator.evaluate("true | false -> false & true <-> !false"));
        assertTrue(defaultEvaluator.evaluate("true & false | true <-> true"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluate_unknownLiteral() {
        assertTrue(new BooleanEvaluator(createMap(entry("A", true))).evaluate("A | B"));
    }

    @Test
    public void testEvaluate() {
        assertFalse(new BooleanEvaluator(createMap(entry("A", true), entry("B", true), entry("C", false), entry("D", true),
            entry("E", false))).evaluate("A | B -> C & D <-> !E"));
    }

    //
    // Helper methods
    //

    private Map<String, Boolean> createMap(Map.Entry<String, Boolean>... entries) {
        return Stream.of(entries).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, Boolean> entry(String key, boolean value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}