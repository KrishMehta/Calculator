package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.model.ExpressionParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Class created by Krish
 */

public class Calculator {

    @FXML
    private TextField display;

    @FXML
    private Button on;

    @FXML
    private Button zero;

    @FXML
    private Button one;

    @FXML
    private Button two;

    @FXML
    private Button three;

    @FXML
    private Button four;

    @FXML
    private Button five;

    @FXML
    private Button six;

    @FXML
    private Button seven;

    @FXML
    private Button eight;

    @FXML
    private Button nine;

    @FXML
    private Button decimal;

    @FXML
    private Button negative;

    @FXML
    private Button plus;

    @FXML
    private Button minus;

    @FXML
    private Button multiply;

    @FXML
    private Button divide;

    @FXML
    private Button sin;

    @FXML
    private Button cos;

    @FXML
    private Button tan;

    @FXML
    private Button log;

    @FXML
    private Button ln;

    @FXML
    private Button pi;

    @FXML
    private Button exponent;

    @FXML
    private Button square;

    @FXML
    private Button negativeOne;

    @FXML
    private Button scientific;

    @FXML
    private Button fraction;

    @FXML
    private Button delete;

    @FXML
    private Button clear;

    @FXML
    private Button table;

    @FXML
    private Button data;

    @FXML
    private Button prb;

    @FXML
    private Button leftParenthesis;

    @FXML
    private Button rightParenthesis;

    @FXML
    private Button xyz;

    @FXML
    private Button sto;

    @FXML
    private Button toggle;

    @FXML
    private Button enter;

    @FXML
    private Button mode;

    @FXML
    private Button second;

    private boolean isSecond = false;
    private boolean isHyp = false;

    private Map<Button, String> numbers = new HashMap<>();
    private Map<Button, String> operations = new HashMap<>();
    private Map<Button, String[]> multiple = new HashMap<>();
    private Map<Button, String[]> trigonometry = new HashMap<>();

    @FXML
    void initialize() {

        numbers.put(zero, "0");
        numbers.put(one, "1");
        numbers.put(two, "2");
        numbers.put(three, "3");
        numbers.put(four, "4");
        numbers.put(five, "5");
        numbers.put(six, "6");
        numbers.put(seven, "7");
        numbers.put(eight, "8");
        numbers.put(nine, "9");
        numbers.put(leftParenthesis, "(");
        numbers.put(rightParenthesis, ")");

        operations.put(plus, "+");
        operations.put(minus, "-");
        operations.put(multiply, "*");
        operations.put(divide, "÷");
        operations.put(negativeOne, "⁻¹");
        operations.put(scientific, "*10ⁿ");

        multiple.put(decimal, new String[]{".", ","});
        multiple.put(negative, new String[]{"-", "ans"});
        multiple.put(log, new String[]{"log(", "10ˣ"});
        multiple.put(ln, new String[]{"ln(", "eˣ"});
        multiple.put(exponent, new String[]{"ⁿ", "ⁿ√"});
        multiple.put(square, new String[]{"²", "√"});

        trigonometry.put(sin, new String[]{"sin(", "sin⁻¹(", "sinh(", "sinh⁻¹("});
        trigonometry.put(cos, new String[]{"cos(", "cos⁻¹(", "cosh(", "cosh⁻¹("});
        trigonometry.put(tan, new String[]{"tan(", "tan⁻¹(", "tanh(", "tanh⁻¹("});


        on.setOnAction(event -> {
            if (isSecond) System.exit(0);
        });
        pi.setOnAction(event -> {
            if (isSecond) isHyp = true;
            else display.setText(display.getText() + "π");
        });
        delete.setOnAction(event -> {
            if (!display.getText().isEmpty()) display.setText(display.getText(0, display.getText().length() - 1));
        });
        clear.setOnAction(event -> display.setText(""));
        second.setOnAction(event -> isSecond = true);
        enter.setOnAction(event -> display.setText(String.valueOf(parse())));

        // todo: fraction, table, data, prb, xyz, sto, toggle, mode

        numbers.forEach((k, v) -> k.setOnAction(event -> {
            display.setText(display.getText() + v);
            isSecond = false;
            isHyp = false;
        }));

        for (var entry : multiple.entrySet()) {
            entry.getKey().setOnAction(event -> {
                if (isSecond) display.setText(display.getText() + entry.getValue()[1]);
                else display.setText(display.getText() + entry.getValue()[0]);
                isSecond = false;
                isHyp = false;
            });
        }

        for (var entry : operations.entrySet()) {
            entry.getKey().setOnAction(event -> {
                if (display.getText().isEmpty()) display.setText("ans" + entry.getValue());
                else display.setText(display.getText() + entry.getValue());
                isSecond = false;
                isHyp = false;
            });
        }

        trigonometry.forEach((k, v) -> k.setOnAction(event -> {
            if (isSecond) {
                if (isHyp) display.setText(display.getText() + v[3]);
                else display.setText(display.getText() + v[1]);
            } else {
                if (isHyp) display.setText(display.getText() + v[2]);
                else display.setText(display.getText() + v[0]);
            }
            isSecond = false;
            isHyp = false;
        }));

    }

    private double parse() {
        var text = display.getText();
        String[] RPN = ExpressionParser.infixToRPN(text.split("\\s*"));
        for (String s : RPN) {
            System.out.print(s + "\t");
        }
        return 0;
    }

}
