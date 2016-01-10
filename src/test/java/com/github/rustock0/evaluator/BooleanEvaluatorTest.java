package com.github.rustock0.evaluator;

import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanEvaluatorTest {

    private final BooleanEvaluator evaluator = new BooleanEvaluator();

    @Test
    public void testEvaluate_negation() {
        assertFalse(evaluator.evaluate("!true"));
        assertTrue(evaluator.evaluate("!false"));
    }

    @Test
    public void testEvaluate_and() {
        assertTrue(evaluator.evaluate("true & true"));
        assertFalse(evaluator.evaluate("true & false"));
        assertFalse(evaluator.evaluate("false & true"));
        assertFalse(evaluator.evaluate("false & false"));
    }

    @Test
    public void testEvaluate_or() {
        assertTrue(evaluator.evaluate("true | true"));
        assertTrue(evaluator.evaluate("true | false"));
        assertTrue(evaluator.evaluate("false | true"));
        assertFalse(evaluator.evaluate("false | false"));
    }

    @Test
    public void testEvaluate_implication() {
        assertTrue(evaluator.evaluate("true -> true"));
        assertFalse(evaluator.evaluate("true -> false"));
        assertTrue(evaluator.evaluate("false -> true"));
        assertTrue(evaluator.evaluate("false -> false"));
    }

    @Test
    public void testEvaluate_equivalence() {
        assertTrue(evaluator.evaluate("true <-> true"));
        assertFalse(evaluator.evaluate("true <-> false"));
        assertFalse(evaluator.evaluate("false <-> true"));
        assertTrue(evaluator.evaluate("false <-> false"));
    }

    @Test
    public void testEvaluate() {
        assertTrue(evaluator.evaluate("true | false -> false & true <-> false"));
        assertFalse(evaluator.evaluate("true | false -> false & true <-> !false"));
    }
}