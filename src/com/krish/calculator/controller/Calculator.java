package com.krish.calculator.controller;

import com.krish.calculator.model.ExpressionEstimator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class created by Krish
 */

public class Calculator implements Initializable {

    @FXML
    private TextField display;

    @FXML
    private Button second;

    @FXML
    private Button random;

    @FXML
    private Button delete;

    @FXML
    private Button log;

    @FXML
    private Button ceil;

    @FXML
    private Button round;

    @FXML
    private Button ln;

    @FXML
    private Button fraction;

    @FXML
    private Button scientific;

    @FXML
    private Button min;

    @FXML
    private Button clear;

    @FXML
    private Button pi;

    @FXML
    private Button sin;

    @FXML
    private Button cos;

    @FXML
    private Button tan;

    @FXML
    private Button divide;

    @FXML
    private Button exponent;

    @FXML
    private Button negativeOne;

    @FXML
    private Button leftParenthesis;

    @FXML
    private Button rightParenthesis;

    @FXML
    private Button multiply;

    @FXML
    private Button square;

    @FXML
    private Button seven;

    @FXML
    private Button eight;

    @FXML
    private Button nine;

    @FXML
    private Button minus;

    @FXML
    private Button xyz;

    @FXML
    private Button four;

    @FXML
    private Button five;

    @FXML
    private Button six;

    @FXML
    private Button plus;

    @FXML
    private Button sto;

    @FXML
    private Button one;

    @FXML
    private Button two;

    @FXML
    private Button three;

    @FXML
    private Button toggle;

    @FXML
    private Button on;

    @FXML
    private Button zero;

    @FXML
    private Button decimal;

    @FXML
    private Button negative;

    @FXML
    private Button enter;

    @FXML
    private Label hypLabel;

    @FXML
    private Label secondLabel;

    private boolean isSecond = false;
    private boolean isHyp = false;

    private Map<Button, String> single = new HashMap<>();
    private Map<Button, String[]> multiple = new HashMap<>();
    private Map<Button, String[]> trigonometry = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        single.put(zero, "0");
        single.put(one, "1");
        single.put(two, "2");
        single.put(three, "3");
        single.put(four, "4");
        single.put(five, "5");
        single.put(six, "6");
        single.put(seven, "7");
        single.put(eight, "8");
        single.put(nine, "9");
        single.put(leftParenthesis, "(");
        single.put(rightParenthesis, ")");
        single.put(plus, "+");
        single.put(minus, "-");
        single.put(multiply, "*");
        single.put(divide, "/");
        single.put(exponent, "pow(");
        single.put(negativeOne, "pow( , -1)");
        single.put(scientific, "*pow(10, ");
        single.put(negative, "-");
        single.put(random, "random()");

        multiple.put(decimal, new String[]{".", ","});
        multiple.put(log, new String[]{"log10(", "pow(10, "});
        multiple.put(ln, new String[]{"log(", "exp("});
        multiple.put(square, new String[]{"pow( , 2)", "sqrt("});
        multiple.put(ceil, new String[]{"ceil(", "floor("});
        multiple.put(round, new String[]{"round(", "abs("});
        multiple.put(min, new String[]{"min(", "max("});
        multiple.put(xyz, new String[]{"x", "%"});

        trigonometry.put(sin, new String[]{"sin(", "asin(", "sinh(", "asinh("});
        trigonometry.put(cos, new String[]{"cos(", "acos(", "cosh(", "acosh("});
        trigonometry.put(tan, new String[]{"tan(", "atan(", "tanh(", "atanh("});

        on.setOnAction(event -> {
            if (isSecond) System.exit(0);
        });

        second.setOnAction(event -> {
            isSecond = true;
            secondLabel.setVisible(true);
        });

        pi.setOnAction(event -> {
            if (isSecond) {
                isHyp = true;
                hypLabel.setVisible(true);
                isSecond = false;
                secondLabel.setVisible(false);
            } else {
                display.setText(display.getText() + "pi");
            }
        });

        delete.setOnAction(event -> {
            if (!display.getText().isEmpty()) display.setText(display.getText(0, display.getText().length() - 1));
        });

        clear.setOnAction(event -> display.setText(""));

        enter.setDefaultButton(true);
        enter.setOnAction(event -> {
            try {
                display.setText(String.valueOf(parse()));
                display.positionCaret(display.getText().length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        single.forEach((k, v) -> k.setOnAction(event -> {
            display.setText(display.getText() + v);
            isSecond = false;
            secondLabel.setVisible(false);
            isHyp = false;
            hypLabel.setVisible(false);
        }));

        for (var entry : multiple.entrySet()) {
            entry.getKey().setOnAction(event -> {
                if (isSecond) display.setText(display.getText() + entry.getValue()[1]);
                else display.setText(display.getText() + entry.getValue()[0]);
                isSecond = false;
                secondLabel.setVisible(false);
                isHyp = false;
                hypLabel.setVisible(false);
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
            secondLabel.setVisible(false);
            isHyp = false;
            hypLabel.setVisible(false);
        }));
    }

    private double parse() throws Exception {
        var text = display.getText();
        return ExpressionEstimator.calculate(text);
    }

}
