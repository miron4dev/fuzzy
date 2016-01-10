package com.github.rustock0.fuzzy.evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A String tokenizer that accepts delimiters that are greater than one character.
 *
 * @author Evgeny Mironenko.
 */
class Tokenizer {

    private Pattern pattern;
    private String tokenDelimiters;

    Tokenizer(List<String> delimiters) {
        if (onlyOneChar(delimiters)) {
            StringBuilder builder = new StringBuilder();
            for (String delimiter : delimiters) {
                builder.append(delimiter);
            }
            tokenDelimiters = builder.toString();
        } else {
            this.pattern = delimitersToRegexp(delimiters);
        }
    }

    /**
     * Converts a string into tokens.
     *
     * @param string The string to be split into tokens
     * @return The tokens
     */
    Iterator<String> tokenize(String string) {
        if (pattern != null) {
            List<String> res = new ArrayList<>();
            Matcher m = pattern.matcher(string);
            int pos = 0;
            while (m.find()) {
                // While there's a delimiter in the string
                if (pos != m.start()) {
                    // If there's something between the current and the previous delimiter
                    // Add to the tokens list
                    addToTokens(res, string.substring(pos, m.start()));
                }
                addToTokens(res, m.group()); // add the delimiter
                pos = m.end(); // Remember end of delimiter
            }
            if (pos != string.length()) {
                // If it remains some characters in the string after last delimiter
                addToTokens(res, string.substring(pos));
            }
            return res.iterator();
        } else {
            return new StringTokenizerIterator(new StringTokenizer(string, tokenDelimiters, true));
        }
    }

    /**
     * Tests whether a String list contains only 1 character length elements.
     *
     * @param delimiters The list to test
     * @return true if it contains only one char length elements (or no elements)
     */
    private boolean onlyOneChar(List<String> delimiters) {
        for (String delimiter : delimiters) {
            if (delimiter.length() != 1) {
                return false;
            }
        }
        return true;
    }

    private Pattern delimitersToRegexp(List<String> delimiters) {
        Collections.sort(delimiters, (o1, o2) -> -o1.compareTo(o2));
        StringBuilder result = new StringBuilder();
        result.append('(');
        for (String delimiter : delimiters) {
            if (result.length() != 1) {
                result.append('|');
            }
            // Quote the delimiter as it could contain some regexp reserved characters
            result.append("\\Q").append(delimiter).append("\\E");
        }
        result.append(')');
        return Pattern.compile(result.toString());
    }

    private void addToTokens(List<String> tokens, String token) {
        token = token.trim();
        if (!token.isEmpty()) {
            tokens.add(token);
        }
    }

    private class StringTokenizerIterator implements Iterator<String> {

        private final StringTokenizer tokens;

        public StringTokenizerIterator(StringTokenizer tokens) {
            this.tokens = tokens;
        }

        private String nextToken = null;

        @Override
        public boolean hasNext() {
            return buildNextToken();
        }

        @Override
        public String next() {
            if (!buildNextToken()) {
                throw new NoSuchElementException();
            }
            String token = nextToken;
            nextToken = null;
            return token;
        }

        private boolean buildNextToken() {
            while ((nextToken == null) && tokens.hasMoreTokens()) {
                nextToken = tokens.nextToken().trim();
                if (nextToken.isEmpty()) {
                    nextToken = null;
                }
            }
            return nextToken != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
