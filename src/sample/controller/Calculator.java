package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

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

    private Map<Button, String> numbers = new HashMap<>() {{
        put(zero, "0");
        put(one, "1");
        put(two, "2");
        put(three, "3");
        put(four, "4");
        put(five, "5");
        put(six, "6");
        put(seven, "7");
        put(eight, "8");
        put(nine, "9");
        put(leftParenthesis, "(");
        put(rightParenthesis, ")");
    }};

    private Map<Button, String> operations = new HashMap<>() {{
        put(plus, "+");
        put(minus, "-");
        put(multiply, "*");
        put(divide, "÷");
        put(negativeOne, "⁻¹");
        put(scientific, "*10ⁿ");
    }};
    
    private Map<Button, String[]> multiple = new HashMap<>() {{
        put(decimal, new String[]{".", ","});
        put(negative, new String[]{"-", "ans"});
        put(log, new String[]{"log(", "10ˣ"});
        put(ln, new String[]{"ln(", "eˣ"});
        put(exponent, new String[]{"ⁿ", "ⁿ√"});
        put(square, new String[]{"²", "√"});
    }};
    
    private Map<Button, String[]> trigonometry = new HashMap<>() {{
        put(sin, new String[]{"sin(", "sin⁻¹(", "sinh(", "sinh⁻¹("});
        put(cos, new String[]{"cos(", "cos⁻¹(", "cosh(", "cosh⁻¹("});
        put(tan, new String[]{"tan(", "tan⁻¹(", "tanh(", "tanh⁻¹("});
    }};

    @FXML
    void initialize() {

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
        enter.setOnAction(event -> parse());

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

    private void parse() {

    }

}
