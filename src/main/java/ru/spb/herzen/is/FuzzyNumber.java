package ru.spb.herzen.is;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Implementation of methods to work with Fuzzy Numbers.
 *
 * @author Evgeny Mironenko
 */
public class FuzzyNumber extends FuzzySet {

    public FuzzyNumber(Map<Double, Double> set) {
        super(set);
    }

    /**
     * Returns a result of addition current and specified sets.
     *
     * @param anotherNumber an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> add(FuzzyNumber anotherNumber) {
        return action(anotherNumber.set, (first, second) -> first + second);
    }

    /**
     * Returns a result of subtraction current and specified sets.
     *
     * @param anotherNumber an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> subtract(FuzzyNumber anotherNumber) {
        return action(anotherNumber.set, (first, second) -> first - second);
    }

    /**
     * Returns a result of multiplication of the current and specified set.
     *
     * @param anotherNumber an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> multiply(FuzzyNumber anotherNumber) {
        return action(anotherNumber.set, (first, second) -> first * second);
    }

    /**
     * Returns a result of division of the current and specified set.
     *
     * @param anotherNumber an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> divide(FuzzyNumber anotherNumber) {
        return action(anotherNumber.set, (first, second) -> first / second);
    }

    /**
     * Returns an extra maximum of the current and specified set.
     *
     * @param anotherNumber an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> extraMaximum(FuzzyNumber anotherNumber) {
        return action(anotherNumber.set, Math::max);
    }

    /**
     * Returns an extra minimum of the current and specified set.
     *
     * @param anotherNumber an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> extraMinimum(FuzzyNumber anotherNumber) {
        return action(anotherNumber.set, Math::min);
    }

    private Map<Double, Double> action(Map<Double, Double> anotherSet, BiFunction<Double, Double, Double> function) {
        Map<Double, Double> result = new TreeMap<>();
        for (Map.Entry<Double, Double> entrySet : set.entrySet()) {
            for (Map.Entry<Double, Double> anotherEntrySet : anotherSet.entrySet()) {
                double key = function.apply(entrySet.getKey(), anotherEntrySet.getKey());
                double value = Math.min(entrySet.getValue(), anotherEntrySet.getValue());
                if (result.containsKey(key)) {
                    double oldValue = result.get(key);
                    value = Math.max(value, oldValue);
                }
                result.put(key, value);
            }
        }
        return result;
    }
}
