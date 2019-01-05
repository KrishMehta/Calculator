package com.krish.calculator.model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class created by Krish
 */

public final class SlowExpressionEstimator {

    private static final String[][] lexer = {
            {"exp()", "log()", "pow(,)", "sqrt()", "abs()", "random()", "min(,)", "max(,)"},
            {"pi", "e", "sqrt2", "sqrt1_2", "ln2", "ln10", "log2e", "log10e"},
            {"sin()", "asin()", "cos()", "acos()", "tan()", "atan()", "atan2(,)"},
            {"ceil()", "floor()", "round()"}
    };

    private static final ScriptEngine engine =
            new ScriptEngineManager().getEngineByName("JavaScript");

    /***
     * return estimation of expression string. For example estimate("1+sin(pi)")=1
     * @param expression as string
     * @return double value of string exception
     * @throws Exception on error
     */
    public static double estimate(String expression) throws Exception {

        String s = expression;
        int i, j, k, l;

        // check unbalanced parentheses
        char[] parentheses = {'(', ')', '[', ']', '{', '}'};
        for (l = 0; l < parentheses.length - 1; l += 2) {
            for (i = 0; i < s.length(); i++) {
                if (s.charAt(i) == parentheses[l]) {
                    for (k = 0, j = i + 1; j < s.length(); j++) {
                        if (s.charAt(j) == parentheses[l]) {
                            k++;
                        } else if (s.charAt(j) == parentheses[l + 1]) {
                            if (k == 0) {
                                try {
                                    estimate(s.substring(i + 1, j));
                                } catch (Exception ex) {
                                    throw new Exception("Unbalanced parentheses");
                                }
                                break;
                            }
                            k--;
                        }
                    }
                    if (j == s.length()) {
                        throw new Exception("Unbalanced parentheses");
                    }
                }
            }
        }


        String[] from = {"[", "]", "{", "}", " ", "\t"};
        String[] to = {"(", ")", "(", ")", "", ""};
        for (i = 0; i < from.length; i++) {
            s = s.replace(from[i], to[i]);
        }

        if (s.length() == 0) {
            throw new Exception("Empty expression");
        }

        // JavaScript allows "random(bla, bla, bla)"
        if (Pattern.compile("random\\(" + "(?!\\))", Pattern.CASE_INSENSITIVE).matcher(s).find()) {
            throw new Exception("Argument(s) of random function is not possible");
        }

        // JavaScript allows "2/+2"
        if (Pattern.compile("[+\\-*/][+\\-*/]", Pattern.CASE_INSENSITIVE).matcher(s).find()) {
            throw new Exception("Two operators in a row");
        }


        for (String[] s1 : lexer) {
            for (String title : s1) {
                if (getNumberOfArguments(title) == 0) {
                    title = title.toUpperCase();
                } else {
                    title = title.substring(0, title.indexOf('('));
                    if (!title.equals("random")) {
                        // JavaScript allows "sin()"
                        if (Pattern.compile(title + "\\(" + "(?=\\))", Pattern.CASE_INSENSITIVE).matcher(s).find()) {
                            throw new Exception("Function '" + title + "' should have argument(s)");
                        }
                    }
                }
                s = getMatcher(title, s).replaceAll("Math." + title);
            }
        }

        //System.out.println(s);

        Object v = engine.eval("eval(" + s + " )");
        if (v instanceof Double) {
            return (double) v;
        } else {
            throw new Exception("Not a number return type " + v.getClass().getName());
        }
    }

    public static Matcher getMatcher(String lexer, String s) {
        return Pattern.compile("(?<=^|\\W)" + lexer + "(?=\\W|$)", Pattern.CASE_INSENSITIVE).matcher(s);
    }

    /*
     * "pow(,)"=2
     * "exp()"=1
     * "pi"=0
     */
    static int getNumberOfArguments(String title) {
        int k = title.indexOf('(');
        if (k == -1) {
            return 0;
        } else {
            return title.indexOf(',') == -1 ? 1 : 2;
        }
    }

    /*
     * "pow(,)" -> "pow"
     * "log()" -> "log"
     * "pi" -> pi
     */
    static String getPureTitle(String title, int arguments) {
        return arguments == 0 ? title : title.substring(0, title.indexOf('('));
    }

    static String getInsertionString(String title, String substring) {
        final int arguments = getNumberOfArguments(title);
        final String pureTitle = SlowExpressionEstimator.getPureTitle(title, arguments);

        if (arguments == 0) {//no arguments
            return pureTitle + substring;
        } else {
            return pureTitle + "(" + substring + (arguments == 1 ? "" : ",") + ")";
        }
    }

}

