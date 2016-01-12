package com.github.rustock0.exam;

import com.github.rustock0.fuzzy.FuzzyNumber;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FirstTaskTest {

    @Test
    public void testFirst() throws Exception {
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(0.0, 0.375);
            put(0.5, 0.4375);
            put(2.0, 0.625);
            put(1.4, 0.55);
        }};
        assertEquals(expected, new FirstTask(Arrays.asList(1.0, 0.5, -1.0, -0.4)).first());
    }

    @Test
    public void testSecond() throws Exception {
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.625);
            put(0.7071067811865476, 0.4375);
            put(0.6597539553864471, 0.55);
        }};
        assertEquals(expected, new FirstTask(Arrays.asList(1.0, 0.5, -1.0, -0.4)).second());
    }

    @Test
    public void testThird() throws Exception {
        Map<Double, Double> expected = new LinkedHashMap<Double, Double>() {{
            put(1.0, 0.625);
            put(0.7071067811865476, 0.4375);
            put(0.6597539553864471, 0.55);
        }};
        assertEquals(expected, new FirstTask(Arrays.asList(1.0, 0.5, -1.0, -0.4)).second());
    }
}