package sample.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Class created by Krish
 */

public class ExpressionParser {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    // Map<"token", []{precendence, associativity}>
    private static final Map<String, int[]> OPERATORS = new HashMap<>() {{
        put("+", new int[]{0, LEFT});
        put("-", new int[]{0, LEFT});
        put("*", new int[]{5, LEFT});
        put("/", new int[]{5, LEFT});
    }};

    // test if token is an operator
    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    // test associativity of operator token
    private static boolean isAssociative(String token, int type) {
        if (!isOperator(token)) throw new IllegalArgumentException("Invalid token: " + token);
        return OPERATORS.get(token)[1] == type;
    }

    // compare precedence of operators
    private static int comparePrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2))
            throw new IllegalArgumentException("Invalid tokens: " + token1 + " " + token2);
        return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
    }

    // convert infix expression format into reverse Polish notation
    public static String[] infixToRPN(String[] tokens) {
        ArrayList<String> out = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            if (isOperator(token)) {
                while (!stack.empty() && isOperator(stack.peek())) {
                    if ((isAssociative(token, LEFT) && comparePrecedence(token, stack.peek()) <= 0)
                            || (isAssociative(token, RIGHT) && comparePrecedence(token, stack.peek()) < 0)) {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }
                // push the new operator on the stack
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop());
                }
                stack.pop();
            } else {
                // if token is a number
                out.add(token);
            }
        }

        while (!stack.empty()) {
            out.add(stack.pop());
        }

        String[] output = new String[out.size()];
        return out.toArray(output);
    }

    public static double RPNtoDouble(String[] tokens) {
        Stack<Double> stack = new Stack<>();

        for (String token : tokens) {
            // if the token is a value push it onto the stack
            if (!isOperator(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                // token is an operator: pop top two entries
                Double d2 = stack.pop();
                Double d1 = stack.pop();

                Double result = token.matches("\\+") ? d1 + d2
                        : token.matches("-") ? d1 - d2
                        : token.matches("\\*") ? d1 * d2
                        : token.matches("[/÷]") ? d1 / d2
                        : d1;

                // push result onto stack
                stack.push(result);
            }
        }
        return stack.pop();
    }

}
