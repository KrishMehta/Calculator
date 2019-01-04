package com.krish.calculator.model;

import static java.lang.Math.*;

/**
 * Class created by Krish
 */

public class ExpressionEstimator {

    private enum OPERATOR {
        PLUS, MINUS, MULTIPLY, DIVIDE, LEFT_BRACKET, RIGHT_BRACKET, LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET, LEFT_CURLY_BRACKET, RIGHT_CURLY_BRACKET, COMMA,
        SIN, COS, TAN, COT, SEC, CSC,
        ASIN, ACOS, ATAN, ACOT, ASEC, ACSC,
        SINH, COSH, TANH, COTH, SECH, CSCH,
        ASINH, ACOSH, ATANH, ACOTH, ASECH, ACSCH,
        RANDOM, CEIL, FLOOR, ROUND, ABS, EXP, LOG, SQRT, POW, ATAN2, MIN, MAX,
        X, NUMBER, UNARY_MINUS, END
    }

    private enum CONSTANT_NAME {
        PI, E, SQRT2, SQRT1_2, LN2, LOG10, LOG2E, LOG10E 
    }

    private static final double[] CONSTANT_VALUE = {
            PI, E, sqrt(2), sqrt(.5), log(2), log(10), 1. / log(2), 1. / log(10)
    };

    private OPERATOR operator;
    private Node root = null;
    private int position;
    private int arguments;
    private double tokenValue;
    private byte[] expression;
    private double[] argument;

    private class Node {
        OPERATOR operator;
        Node left, right;
        double value;

        private void init(OPERATOR operator, double value, Node left) {
            this.operator = operator;
            this.value = value;
            this.left = left;
        }

        Node(OPERATOR operator, Node left) {
            init(operator, 0, left);
        }

        Node(OPERATOR operator) {
            init(operator, 0, null);
        }

        Node(OPERATOR operator, double value) {
            init(operator, value, null);
        }

        double calculate() throws Exception {
            double x;
            switch (operator) {
                case NUMBER: return value;
                case PLUS: return left.calculate() + right.calculate();
                case MINUS: return left.calculate() - right.calculate();
                case MULTIPLY: return left.calculate() * right.calculate();
                case DIVIDE: return left.calculate() / right.calculate();
                case UNARY_MINUS: return -left.calculate();
                case SIN: return sin(left.calculate());
                case COS: return cos(left.calculate());
                case TAN: return tan(left.calculate());
                case COT: return 1 / tan(left.calculate());
                case SEC: return 1 / cos(left.calculate());
                case CSC: return 1 / sin(left.calculate());
                case ASIN: return asin(left.calculate());
                case ACOS: return acos(left.calculate());
                case ATAN: return atan(left.calculate());
                case ACOT: return PI / 2 - atan(left.calculate());
                case ASEC: return acos(1 / left.calculate());
                case ACSC: return asin(1 / left.calculate());
                case SINH:
                    x = left.calculate();
                    return (exp(x) - exp(-x)) / 2;
                case COSH:
                    x = left.calculate();
                    return (exp(x) + exp(-x)) / 2;
                case TANH:
                    x = left.calculate();
                    return (exp(2 * x) - 1) / (exp(2 * x) + 1);
                case COTH:
                    x = left.calculate();
                    return (exp(2 * x) + 1) / (exp(2 * x) - 1);
                case SECH:
                    x = left.calculate();
                    return 2 / (exp(x) + exp(-x));
                case CSCH:
                    x = left.calculate();
                    return 2 / (exp(x) - exp(-x));
                case ASINH:
                    x = left.calculate();
                    return log(x + sqrt(x * x + 1));
                case ACOSH:
                    x = left.calculate();
                    return log(x + sqrt(x * x - 1));
                case ATANH:
                    x = left.calculate();
                    return log((1 + x) / (1 - x)) / 2;
                case ACOTH:
                    x = left.calculate();
                    return log((x + 1) / (x - 1)) / 2;
                case ASECH:
                    x = left.calculate();
                    return log((1 + sqrt(1 - x * x)) / x);
                case ACSCH:
                    x = left.calculate();
                    return log(1 / x + sqrt(1 + x * x) / abs(x));
                case RANDOM:
                    return random();
                case CEIL:
                    return ceil(left.calculate());
                case FLOOR:
                    return floor(left.calculate());
                case ROUND:
                    return round(left.calculate());
                case ABS:
                    return abs(left.calculate());
                case EXP:
                    return exp(left.calculate());
                case LOG:
                    return log(left.calculate());
                case SQRT:
                    return sqrt(left.calculate());
                case POW:
                    return pow(left.calculate(), right.calculate());
                case ATAN2:
                    return atan2(left.calculate(), right.calculate());
                case MIN:
                    return min(left.calculate(), right.calculate());
                case MAX:
                    return max(left.calculate(), right.calculate());
                case X:
                    return argument[(int) value];
                default:
                    throw new Exception("Node.calculate error");
            }
        }
    }

    private boolean isLetter() {
        return Character.isLetter(expression[position]);
    }

    private boolean isDigit() {
        return Character.isDigit(expression[position]);
    }

    private boolean isPoint() {
        return expression[position] == '.';
    }

    private boolean isFunctionSymbol() {
        byte c = expression[position];
        return Character.isLetterOrDigit(c) || c == '_';
    }

    private void getToken() throws Exception {
        int i;
        if (position == expression.length - 1) {
            operator = OPERATOR.END;
        } else if ((i = "+-*/()[]{},".indexOf(expression[position])) != -1) {
            position++;
            operator = OPERATOR.values()[i];
        } else if (isLetter()) {
            for (i = position++; isFunctionSymbol(); position++);
            String token = new String(expression, i, position - i);
            try {
                if (token.charAt(0) == 'X' && token.length() == 1) {
                    throw new Exception("Unknown keyword");
                } else if (token.charAt(0) == 'X' && token.length() > 1 && Character.isDigit(token.charAt(1))) {
                    i = Integer.parseInt(token.substring(1));
                    if (i < 0) {
                        throw new Exception("Index of 'x' should be a non-negative integer number");
                    }
                    if (arguments < i + 1) {
                        arguments = i + 1;
                    }
                    operator = OPERATOR.X;
                    tokenValue = i;
                } else {
                    operator = OPERATOR.valueOf(token);
                    i = operator.ordinal();
                    if (i < OPERATOR.SIN.ordinal() || i > OPERATOR.MAX.ordinal()) {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (IllegalArgumentException e) {
                try {
                    tokenValue = CONSTANT_VALUE[CONSTANT_NAME.valueOf(token).ordinal()];
                    operator = OPERATOR.NUMBER;
                } catch (IllegalArgumentException ex) {
                    throw new Exception("Unknown keyword");
                }
            }
        } else if (isDigit() || isPoint()) {
            for (i = position++;
                 isDigit() || isPoint() || expression[position] == 'E' || expression[position - 1] == 'E' && "+-".indexOf(expression[position]) != -1;
                 position++);
            tokenValue = Double.parseDouble(new String(expression, i, position - i));
            operator = OPERATOR.NUMBER;
        } else {
            throw new Exception("Unknown symbol");
        }

    }

    public void compile(String expression) throws Exception {
        position = 0;
        arguments = 0;

        String s = expression.toUpperCase();
        String from[] = {" ", "\t"};
        for (int i = 0; i < from.length; i++) {
            s = s.replace(from[i], "");
        }
        this.expression = (s + '\0').getBytes();

        getToken();
        if (operator == OPERATOR.END) {
            throw new Exception("Unexpected end of expression");
        }
        root = parse();
        if (operator != OPERATOR.END) {
            throw new Exception("End of expression expected");
        }

    }

    private Node parse() throws Exception {
        Node node = parse1();
        while (operator == OPERATOR.PLUS || operator == OPERATOR.MINUS) {
            node = new Node(operator, node);
            twoOperators();
            node.right = parse1();
        }
        return node;
    }

    private Node parse1() throws Exception {
        Node node = parse2();
        while (operator == OPERATOR.MULTIPLY || operator == OPERATOR.DIVIDE) {
            node = new Node(operator, node);
            twoOperators();
            node.right = parse2();
        }
        return node;
    }

    private void twoOperators() throws Exception {
        getToken();
        if (operator == OPERATOR.PLUS || operator == OPERATOR.MINUS) {
            throw new Exception("Two operators in a row");
        }
    }

    private Node parse2() throws Exception {
        Node node;
        if (operator == OPERATOR.MINUS) {
            getToken();
            node = new Node(OPERATOR.UNARY_MINUS, parse3());
        } else {
            if (operator == OPERATOR.PLUS) {
                getToken();
            }
            node = parse3();
        }
        return node;
    }

    private Node parse3() throws Exception {
        Node node;
        OPERATOR open;

        if (operator.ordinal() >= OPERATOR.SIN.ordinal() && operator.ordinal() <= OPERATOR.MAX.ordinal()) {
            int arguments;
            if (operator.ordinal() >= OPERATOR.POW.ordinal() && operator.ordinal() <= OPERATOR.MAX.ordinal()) {
                arguments = 2;
            } else {
                arguments = operator == OPERATOR.RANDOM ? 0 : 1;
            }

            node = new Node(operator);

            getToken();
            open = operator;
            if (operator != OPERATOR.LEFT_BRACKET && operator != OPERATOR.LEFT_SQUARE_BRACKET && operator != OPERATOR.LEFT_CURLY_BRACKET) {
                throw new Exception("Open bracket expected");
            }
            getToken();

            if (arguments > 0) {
                node.left = parse();

                if (arguments == 2) {
                    if (operator != OPERATOR.COMMA) {
                        throw new Exception("Comma expected");
                    }
                    getToken();
                    node.right = parse();
                }
            }
            checkBracketBalance(open);
        } else {
            switch (operator) {

                case X:
                case NUMBER:
                    node = new Node(operator, tokenValue);
                    break;

                case LEFT_BRACKET:
                case LEFT_SQUARE_BRACKET:
                case LEFT_CURLY_BRACKET:
                    open = operator;
                    getToken();
                    node = parse();
                    checkBracketBalance(open);
                    break;

                default:
                    throw new Exception("Unexpected operator");
            }

        }
        getToken();
        return node;
    }

    private void checkBracketBalance(OPERATOR open) throws Exception {
        if (open == OPERATOR.LEFT_BRACKET && operator != OPERATOR.RIGHT_BRACKET || open == OPERATOR.LEFT_SQUARE_BRACKET && operator != OPERATOR.RIGHT_SQUARE_BRACKET || open == OPERATOR.LEFT_CURLY_BRACKET && operator != OPERATOR.RIGHT_CURLY_BRACKET) {
            throw new Exception("Close bracket expected or another type of close bracket");
        }
    }

    public double calculate(double[] x) throws Exception {
        this.argument = x;
        return calculate();
    }

    public double calculate() throws Exception {
        if (root == null) {
            throw new Exception("Using of calculate() without compile()");
        }
        int length = argument == null ? 0 : argument.length;
        if (length != arguments) {
            throw new Exception("Invalid number of expression arguments");
        }
        return root.calculate();
    }

    /**
     * @return number of expression arguments
     */
    public int getArguments() {
        return arguments;
    }

    public static double calculate(String s) throws Exception {
        ExpressionEstimator estimator = new ExpressionEstimator();
        estimator.compile(s);
        estimator.argument = null;
        return estimator.calculate();
    }

}
