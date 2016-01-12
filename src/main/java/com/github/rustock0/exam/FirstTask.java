package com.github.rustock0.exam;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * #16
 *
 * @author Evgeny Mironenko.
 */
public class FirstTask {

    private static final int LOWER_INTERVAL = -1;
    private static final int HIGHER_INTERVAL = 1;
    private static final int INCORRECT_VALUE = 4;

    private Map<Double, Double> fuzzySet;

    public FirstTask(Collection<Double> set) {
        validate(set);
        fuzzySet = toFuzzySet(set);
        System.out.println(fuzzySet);
    }

    public Map<Double, Double> first() {
        return action(fuzzySet, x -> 1 - x);
    }

    public Map<Double, Double> second() {
        return action(fuzzySet, x -> Math.pow(2, Math.abs(x) - 1));
    }

    public Map<Double, Double> third() {
        return action(fuzzySet, x -> Math.sin(Math.PI * Math.abs(x) / 2));
    }

    /**
     * Applies the function of membership for the current set.
     *
     * @return fuzzy set with the function of membership mu(x) = (4-x)/8
     */
    private Map<Double, Double> toFuzzySet(Collection<Double> set) {
        Map<Double, Double> fuzzySet = new LinkedHashMap<>();
        for (Double x : set) {
            fuzzySet.put(x, (4 - x) / 8);
        }
        return fuzzySet;
    }

    private void validate(Collection<Double> set) {
        for (Double value : set) {
            if (value > HIGHER_INTERVAL || value < LOWER_INTERVAL || value == INCORRECT_VALUE) {
                throw new IllegalArgumentException("Invalid input data: " + value);
            }

        }
    }

    private Map<Double, Double> action(Map<Double, Double> set, Function<Double, Double> function) {
        Map<Double, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Double, Double> entrySet : set.entrySet()) {
            double key = function.apply(entrySet.getKey());
            double value = entrySet.getValue();
            if (result.containsKey(key)) {
                double oldValue = result.get(key);
                value = Math.max(value, oldValue);
            }
            result.put(key, value);
        }
        return result;
    }
}
