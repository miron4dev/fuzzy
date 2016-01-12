package com.github.rustock0.fuzzy;

import com.github.rustock0.evaluator.BooleanEvaluator;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by eugen on 12.01.16.
 */
@SuppressWarnings("unchecked")
public class SecondTaskTest {

    @Test
    public void testExpressions() {
        String expression1 = "!P -> (P -> Q)";
        String expression2 = "(P | Q <-> Q) <-> (P -> Q)";

        final int n = 2;
        for (int i = 0; i < Math.pow(2, n); i++) {
            String bin = Integer.toBinaryString(i);
            while (bin.length() < n) {
                bin = "0" + bin;
            }

            boolean p = bin.charAt(0) == '0';
            boolean q = bin.charAt(1) == '1';
            BooleanEvaluator evaluator = new BooleanEvaluator(createMap(entry("P", p), entry("Q", q)));
            assertEquals(evaluator.evaluate(expression1), evaluator.evaluate(expression2));
        }
    }

    private Map<String, Boolean> createMap(Map.Entry<String, Boolean>... entries) {
        return Stream.of(entries).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, Boolean> entry(String key, boolean value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

}
