package com.github.rustock0.evaluator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** The parameters of an evaluator.
 * <br>An evaluator may have different parameters as the supported operators, the supported functions, etc ...
 * @author Jean-Marc Astesana
 * @see <a href="../../../license.html">License information</a>
 */
public class Parameters {
    private String functionSeparator;
    private final List<Operator> operators;
    private final Map<String, String> translations;
    private final List<BracketPair> expressionBrackets;
    private final List<BracketPair> functionBrackets;

    /** Constructor.
     * <br>This method builds an instance with no operator, no function, no constant, no translation and no bracket
     * <br>Function argument separator is set to ','.
     */
    public Parameters() {
        this.operators = new ArrayList<Operator>();
        this.translations = new HashMap<String, String>();
        this.expressionBrackets = new ArrayList<BracketPair>();
        this.functionBrackets = new ArrayList<BracketPair>();
    }

    /** Gets the supported operators.
     * @return a Collection of operators.
     */
    public Collection<Operator> getOperators() {
        return this.operators;
    }
}
