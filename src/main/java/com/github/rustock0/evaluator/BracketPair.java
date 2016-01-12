package com.github.rustock0.evaluator;

/**
 * Created by eugen on 12.01.16.
 */
public class BracketPair {

    public static final BracketPair PARENTHESES = new BracketPair('(', ')');

    private String open;
    private String close;

    /** Constructor.
     * @param open The character used to open the brackets.
     * @param close The character used to close the brackets.
     */
    public BracketPair(char open, char close) {
        super();
        this.open = new String(new char[]{open});
        this.close = new String(new char[]{close});
    }

    /** Gets the open bracket character.
     * @return a char
     */
    public String getOpen() {
        return open;
    }

    /** Gets the close bracket character.
     * @return a char
     */
    public String getClose() {
        return close;
    }
}
