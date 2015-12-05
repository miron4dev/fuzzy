package ru.spb.herzen.is;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Implementation of methods to work with Fuzzy Sets.
 *
 * @author Evgeny Mironenko
 */
public class FuzzySet {

    private final Map<Double, Double> set;

    private List<Double> core;
    private List<Double> transitionPoint;
    private Double height;
    private List<Double> support;
    private List<Integer> nearestClearSet;
    private Double entropy;
    private Boolean convex;
    private Boolean concave;
    private Map<Double, Double> addition;
    private Map<Double, Double> concentration;
    private Map<Double, Double> stretching;

    public FuzzySet(Map<Double, Double> set) {
        this.set = set;
    }

    /**
     * Calculate a fuzzy set.
     */
    public void calculate() {
        core = getCore();
        transitionPoint = getTransitionPoint();
        height = getHeight();
        support = getSupport();
        nearestClearSet = getNearestClearSet();
        entropy = getEntropy();
        convex = isConvex();
        concave = isConcave();
        addition = getAddition();
        concentration = getConcentration();
        stretching = getStretching();
    }

    /**
     * Returns a core of the current fuzzy set.
     *
     * @return see description.
     */
    public List<Double> getCore() {
        if (core == null) {
            core = new ArrayList<>(set.entrySet().stream().filter(entry -> entry.getValue() == 1.0).map(Map.Entry::getKey)
                .collect(Collectors.toList()));
        }
        return core;
    }

    /**
     * Returns a transition point of the current fuzzy set.
     *
     * @return see description.
     */
    public List<Double> getTransitionPoint() {
        if (transitionPoint == null) {
            transitionPoint = new ArrayList<>(set.entrySet().stream().filter(entry -> entry.getValue() == 0.5).map(Map.Entry::getKey)
                .collect(Collectors.toList()));
        }
        return transitionPoint;
    }

    /**
     * Returns a height "supremum(set)" of the current fuzzy set.
     *
     * @return see description.
     */
    public double getHeight() {
        if (height == null) {
            height = 0.0;
            for (Double mu : set.values()) {
                if (mu > height) {
                    height = mu;
                }
            }
        }
        return height;
    }

    /**
     * Returns a support of the current fuzzy set.
     *
     * @return see description.
     */
    public List<Double> getSupport() {
        if (support == null) {
            support = new ArrayList<>(set.entrySet().stream().filter(entry -> entry.getValue() > 0.0).map(Map.Entry::getKey)
                .collect(Collectors.toList()));
        }
        return support;
    }

    /**
     * Normalizes current fuzzy set, if it's subnormal. Replace the old set by the new one and returns it.
     *
     * @return a normalized fuzzy set.
     */
    public Map<Double, Double> normalize() {
        if (getHeight() != 1.0) {
            for (Map.Entry<Double, Double> entry : set.entrySet()) {
                set.put(entry.getKey(), entry.getValue() / height);
            }
            height = 1.0;
        }
        return set;
    }

    /**
     * Returns a Hamming distance between the current fuzzy set and the specified another set.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public double getHammingDistance(final Map<Double, Double> anotherSet) {
        double distance = 0.0;
        Iterator<Double> setIter = set.values().iterator();
        Iterator<Double> anotherSetIter = anotherSet.values().iterator();
        while (setIter.hasNext() && anotherSetIter.hasNext()) {
            distance += Math.abs(setIter.next() - anotherSetIter.next());
        }
        for (Iterator<Double> lastIter = setIter.hasNext() ? setIter : anotherSetIter; lastIter.hasNext(); ) {
            distance += lastIter.next();
        }
        return distance;
    }

    /**
     * Returns an Euclidean distance between the current fuzzy set and the specified another set.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public double getEuclideanDistance(final Map<Double, Double> anotherSet) {
        double distance = 0.0;
        Iterator<Double> setIter = set.values().iterator();
        Iterator<Double> anotherSetIter = anotherSet.values().iterator();
        while (setIter.hasNext() && anotherSetIter.hasNext()) {
            distance += Math.pow(setIter.next() - anotherSetIter.next(), 2);
        }
        for (Iterator<Double> lastIter = setIter.hasNext() ? setIter : anotherSetIter; lastIter.hasNext(); ) {
            distance += lastIter.next();
        }
        return Math.sqrt(distance);
    }

    /**
     * Returns a nearest clear set for the current fuzzy set.
     *
     * @return see description.
     */
    public List<Integer> getNearestClearSet() {
        if (nearestClearSet == null) {
            nearestClearSet = new ArrayList<>();
            for (Double mu : set.values()) {
                if (mu > 0.5) {
                    nearestClearSet.add(1);
                } else {
                    nearestClearSet.add(0);
                }
            }
        }
        return nearestClearSet;
    }

    /**
     * Returns an entropy of the current fuzzy set.
     *
     * @return see description.
     */
    public double getEntropy() {
        if (entropy == null) {
            entropy = 0.0;
            double p;
            for (Double mu : set.values()) {
                p = mu / set.values().stream().mapToDouble(Double::doubleValue).sum();
                if (p == 0.0) {
                    continue;
                }
                entropy += p * Math.log(p) / Math.log(2);
            }
            entropy = (-1 * entropy) / (Math.log(6) / Math.log(2));
        }
        return entropy;
    }

    /**
     * Returns true if the current fuzzy set is convex.
     *
     * @return see description.
     */
    public boolean isConvex() {
        if (convex == null) {
            if (set.size() < 3) {
                convex = true;
                return true;
            }
            List<Map.Entry<Double, Double>> entries = new ArrayList<>(set.entrySet());
            double x1, x2, x3, mu1, mu2, mu3;
            for (int i = 0; i < set.size() - 2; i++) {
                x1 = entries.get(i).getKey();
                mu1 = entries.get(i).getValue();
                x2 = entries.get(i + 1).getKey();
                mu2 = entries.get(i + 1).getValue();
                x3 = entries.get(i + 2).getKey();
                mu3 = entries.get(i + 2).getValue();
                if (!(mu2 >= (x3 - x2) / (x3 - x1) * mu1 + (x2 - x1) / (x3 - x1) * mu3)) {
                    convex = false;
                    return false;
                }
            }
            convex = true;
        }
        return convex;
    }

    /**
     * Returns true if the current fuzzy set is concave.
     *
     * @return see description.
     */
    public boolean isConcave() {
        if (concave == null) {
            if (set.size() < 3) {
                concave = true;
                return true;
            }
            List<Map.Entry<Double, Double>> entries = new ArrayList<>(set.entrySet());
            double x1, x2, x3, mu1, mu2, mu3;
            for (int i = 0; i < set.size() - 2; i++) {
                x1 = entries.get(i).getKey();
                mu1 = entries.get(i).getValue();
                x2 = entries.get(i + 1).getKey();
                mu2 = entries.get(i + 1).getValue();
                x3 = entries.get(i + 2).getKey();
                mu3 = entries.get(i + 2).getValue();
                if (!(mu2 <= (x3 - x2) / (x3 - x1) * mu1 + (x2 - x1) / (x3 - x1) * mu3)) {
                    concave = false;
                    return false;
                }
            }
            concave = true;
        }
        return concave;
    }

    /**
     * Returns an addition of the current fuzzy set.
     *
     * @return see description.
     */
    public Map<Double, Double> getAddition() {
        if (addition == null) {
            addition = new TreeMap<>();
            for (Map.Entry<Double, Double> entry : set.entrySet()) {
                addition.put(entry.getKey(), 1 - entry.getValue());
            }
        }
        return addition;
    }

    /**
     * Returns an union of the current and specified fuzzy sets.
     * Implements Maximin algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> getUnionMax(Map<Double, Double> anotherSet) {
        Map<Double, Double> result = new HashMap<>();
        for (Map.Entry<Double, Double> entry : anotherSet.entrySet()) {
            if (set.containsKey(entry.getKey())) {
                result.put(entry.getKey(), Math.max(entry.getValue(), set.get(entry.getKey())));
            }
        }
        return result;
    }

    /**
     * Returns an intersection of the current and specified fuzzy sets.
     * Implements Maximin algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> getIntersectionMax(Map<Double, Double> anotherSet) {
        Map<Double, Double> result = new HashMap<>();
        for (Map.Entry<Double, Double> entry : anotherSet.entrySet()) {
            if (set.containsKey(entry.getKey())) {
                result.put(entry.getKey(), Math.min(entry.getValue(), set.get(entry.getKey())));
            }
        }
        return result;
    }

    /**
     * Deducts specified fuzzy set from the current fuzzy set and returns the result.
     * Implements Maximin algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return result of deduction.
     */
    public Map<Double, Double> deductMax(Map<Double, Double> anotherSet) {
        return getIntersectionMax(new FuzzySet(anotherSet).getAddition());
    }

    /**
     * Returns an union of the current and specified fuzzy sets.
     * Implements algebraic algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> getUnionAlg(Map<Double, Double> anotherSet) {
        Map<Double, Double> result = new HashMap<>();
        for (Map.Entry<Double, Double> entry : anotherSet.entrySet()) {
            if (set.containsKey(entry.getKey())) {
                double mua = set.get(entry.getKey());
                double mub = entry.getValue();
                result.put(entry.getKey(), mua + mub - mua * mub);
            }
        }
        return result;
    }

    /**
     * Returns an intersection of the current and specified fuzzy sets.
     * Implements algebraic algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> getIntersectionAlg(Map<Double, Double> anotherSet) {
        Map<Double, Double> result = new HashMap<>();
        for (Map.Entry<Double, Double> entry : anotherSet.entrySet()) {
            if (set.containsKey(entry.getKey())) {
                double mua = set.get(entry.getKey());
                double mub = entry.getValue();
                result.put(entry.getKey(), mua * mub);
            }
        }
        return result;
    }

    /**
     * Deducts specified fuzzy set from the current fuzzy set and returns the result.
     * Implements algebraic algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return result of deduction.
     */
    public Map<Double, Double> deductAlg(Map<Double, Double> anotherSet) {
        return getIntersectionAlg(new FuzzySet(anotherSet).getAddition());
    }

    /**
     * Implements the first symmetric algorithm of algebraic deduction between the current and specified fuzzy sets.
     *
     * @param anotherSet an another fuzzy set.
     * @return results of deduction.
     */
    public Map<Double, Double> symmetricDeduction1Alg(Map<Double, Double> anotherSet) {
        Map<Double, Double> abDeduction = deductAlg(anotherSet);
        Map<Double, Double> baDeduction = new FuzzySet(anotherSet).deductAlg(set);
        return new FuzzySet(abDeduction).getUnionAlg(baDeduction);
    }

    /**
     * Implements the second symmetric algorithm of algebraic deduction between the current and specified fuzzy sets.
     *
     * @param anotherSet an another fuzzy set.
     * @return results of deduction.
     */
    public Map<Double, Double> symmetricDeduction2Alg(Map<Double, Double> anotherSet) {
        Map<Double, Double> aPlusB = getUnionAlg(anotherSet);
        Map<Double, Double> aBIntersection = getIntersectionAlg(anotherSet);
        return new FuzzySet(aPlusB).deductAlg(aBIntersection);
    }

    /**
     * Returns an union of the current and specified fuzzy sets.
     * Implements limited algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> getUnionLim(Map<Double, Double> anotherSet) {
        Map<Double, Double> result = new HashMap<>();
        for (Map.Entry<Double, Double> entry : anotherSet.entrySet()) {
            if (set.containsKey(entry.getKey())) {
                result.put(entry.getKey(), Math.min(1, set.get(entry.getKey() + entry.getValue())));
            }
        }
        return result;
    }

    /**
     * Returns an intersection of the current and specified fuzzy sets.
     * Implements limited algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public Map<Double, Double> getIntersectionLim(Map<Double, Double> anotherSet) {
        Map<Double, Double> result = new HashMap<>();
        for (Map.Entry<Double, Double> entry : anotherSet.entrySet()) {
            if (set.containsKey(entry.getKey())) {
                result.put(entry.getKey(), Math.max(0, set.get(entry.getKey()) + entry.getValue() - 1));
            }
        }
        return result;
    }

    /**
     * Deducts specified fuzzy set from the current fuzzy set and returns the result.
     * Implements limited algorithm.
     *
     * @param anotherSet an another fuzzy set.
     * @return result of deduction.
     */
    public Map<Double, Double> deductLim(Map<Double, Double> anotherSet) {
        return getIntersectionLim(new FuzzySet(anotherSet).getAddition());
    }

    /**
     * Implements the first symmetric algorithm of limited deduction between the current and specified fuzzy sets.
     *
     * @param anotherSet an another fuzzy set.
     * @return results of deduction.
     */
    public Map<Double, Double> symmetricDeduction1Lim(Map<Double, Double> anotherSet) {
        Map<Double, Double> abDeduction = deductLim(anotherSet);
        Map<Double, Double> baDeduction = new FuzzySet(anotherSet).deductLim(set);
        return new FuzzySet(abDeduction).getUnionAlg(baDeduction);
    }

    /**
     * Implements the second symmetric algorithm of limited deduction between the current and specified fuzzy sets.
     *
     * @param anotherSet an another fuzzy set.
     * @return results of deduction.
     */
    public Map<Double, Double> symmetricDeduction2Lim(Map<Double, Double> anotherSet) {
        Map<Double, Double> aPlusB = getUnionLim(anotherSet);
        Map<Double, Double> aBIntersection = getIntersectionLim(anotherSet);
        return new FuzzySet(aPlusB).deductLim(aBIntersection);
    }

    /**
     * Returns an concentration of the current fuzzy set.
     *
     * @return see description.
     */
    public Map<Double, Double> getConcentration() {
        if (concentration == null) {
            concentration = new TreeMap<>();
            for (Map.Entry<Double, Double> entry : set.entrySet()) {
                concentration.put(entry.getKey(), Math.pow(entry.getValue(), 2));
            }
        }
        return concentration;
    }

    /**
     * Returns an stretching of the current fuzzy set.
     *
     * @return see description.
     */
    public Map<Double, Double> getStretching() {
        if (stretching == null) {
            stretching = new TreeMap<>();
            for (Map.Entry<Double, Double> entry : set.entrySet()) {
                stretching.put(entry.getKey(), Math.sqrt(entry.getValue()));
            }
        }
        return stretching;
    }

    /**
     * Returns an alpha cut according the specified alpha value.
     *
     * @param alpha a number between 0 and 1.
     * @return see description.
     */
    public List<Double> getAlphaCut(double alpha) {
        List<Double> result = new ArrayList<>();
        for (Map.Entry<Double, Double> entry : set.entrySet()) {
            if (entry.getValue() >= alpha) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    /**
     * Returns a strict alpha cut according the specified alpha value.
     *
     * @param alpha a number between 0 and 1.
     * @return see description.
     */
    public List<Double> getStrictAlphaCut(double alpha) {
        List<Double> result = new ArrayList<>();
        for (Map.Entry<Double, Double> entry : set.entrySet()) {
            if (entry.getValue() > alpha) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    /**
     * Returns true if the specified fuzzy set dominates (includes) the current fuzzy set.
     *
     * @param anotherSet an another fuzzy set.
     * @return see description.
     */
    public boolean isDominate(Map<Double, Double> anotherSet) {
        for (Map.Entry<Double, Double> entry : anotherSet.entrySet()) {
            if (set.containsKey(entry.getKey()) && entry.getValue() <= set.get(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a result of defuzzification for the current fuzzy set.
     * Implements Center of Gravity algorithm.
     *
     * @return see description.
     */
    public double getDefuzzificationCOG() {
        double sum1 = 0;
        double sum2 = 0;
        for (Map.Entry<Double, Double> entry : set.entrySet()) {
            sum1 += entry.getKey() * entry.getValue();
            sum2 += entry.getValue();
        }
        return sum1 / sum2;
    }

    /**
     * Returns a result of defuzzification for the current fuzzy set.
     * Implements Center of Area algorithm.
     *
     * @return see description.
     */
    public double getDefuzzificationCOA() {
        double result = Double.NaN;
        double minSum = Double.MAX_VALUE;
        for (Double x : set.keySet()) {
            double tmp = getPartialSumResidual(x);
            if (tmp < minSum) {
                minSum = tmp;
                result = x;
            }
        }
        return result;
    }

    private double getPartialSumResidual(double x) {
        Map<Double, Double> sortedSet = new TreeMap<>(set);
        double firstSum = 0;
        double secondSum = 0;
        boolean left = true;
        for (Map.Entry<Double, Double> entry : sortedSet.entrySet()) {
            if (entry.getKey() == x) {
                left = false;
            }
            if (left) {
                firstSum += entry.getValue();
            } else {
                secondSum += entry.getValue();
            }
        }
        return Math.abs(firstSum - secondSum);
    }

    /**
     * Returns a result of defuzzification for the current fuzzy set.
     * Implements Left of Maximum algorithm.
     *
     * @return see description.
     */
    public double getDefuzzificationLOM() {
        double result = Double.MAX_VALUE;
        double maxMu = 0;
        for (Map.Entry<Double, Double> entry : set.entrySet()) {
            if (entry.getValue() >= maxMu) {
                maxMu = entry.getValue();
                if (entry.getKey() < result) {
                    result = entry.getKey();
                }
            }
        }
        return result;
    }

    /**
     * Returns a result of defuzzification for the current fuzzy set.
     * Implements Right of Maximum algorithm.
     *
     * @return see description.
     */
    public double getDefuzzificationROM() {
        double result = Double.MIN_VALUE;
        double maxMu = 0;
        for (Map.Entry<Double, Double> entry : set.entrySet()) {
            if (entry.getValue() >= maxMu) {
                maxMu = entry.getValue();
                if (entry.getKey() > result) {
                    result = entry.getKey();
                }
            }
        }
        return result;
    }

    /**
     * Returns a result of defuzzification for the current fuzzy set.
     * Implements Mean of Maximums algorithm.
     *
     * @return see description.
     */
    public double getDefuzzificationMOM() {
        double maxMu = 0;
        for (Double mu : set.values()) {
            if (mu > maxMu) {
                maxMu = mu;
            }
        }
        double sum = 0;
        double length = 0;
        for (Map.Entry<Double, Double> entry : set.entrySet()) {
            if (entry.getValue() == maxMu) {
                sum += entry.getKey();
                length++;
            }
        }
        return sum / length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuzzySet that = (FuzzySet) o;

        return !(set != null ? !set.equals(that.set) : that.set != null);
    }

    @Override
    public String toString() {
        return "FuzzySet{" +
            "set=" + set +
            ", core=" + core +
            ", transitionPoint=" + transitionPoint +
            ", height=" + height +
            ", support=" + support +
            ", nearestClearSet=" + nearestClearSet +
            ", entropy=" + entropy +
            ", convex=" + convex +
            ", concave=" + concave +
            ", addition=" + addition +
            ", concentration=" + concentration +
            ", stretching=" + stretching +
            '}';
    }
}
