package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.List;

/**
 * Class created by Krish
 */

public class Calculator {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane screen;

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

    private String output = null;
    private boolean isSecond = false;
    private boolean isHyp = false;
    private List<Button> list = Arrays.asList(on, zero, one, two, three, four, five, six, seven, eight, nine,
            decimal, negative, plus, minus, multiply, divide, sin, cos, tan, cos, tan, log, ln, pi, exponent,
            square, negativeOne, scientific, fraction, delete, clear, table, data, prb, leftParenthesis,
            rightParenthesis, xyz, sto, toggle, enter, mode, second);

    @FXML
    void initialize() {
        on.setOnAction(event -> {
            if (isSecond) System.exit(0);
        });
        zero.setOnAction(event -> output = "0");
        one.setOnAction(event -> output = "1");
        two.setOnAction(event -> output = "2");
        three.setOnAction(event -> output = "3");
        four.setOnAction(event -> output = "4");
        five.setOnAction(event -> output = "5");
        six.setOnAction(event -> output = "6");
        seven.setOnAction(event -> output = "7");
        eight.setOnAction(event -> output = "8");
        nine.setOnAction(event -> output = "9");
        decimal.setOnAction(event -> output = isSecond ? "," : ".");
        negative.setOnAction(event -> output = isSecond ? "ans" : "-");
        plus.setOnAction(event -> output = display.getText().isEmpty() ? "ans+" : "+");
        minus.setOnAction(event -> output = display.getText().isEmpty() ? "ans-" : "-");
        multiply.setOnAction(event -> output = display.getText().isEmpty() ? "ans*" : "*");
        divide.setOnAction(event -> output = display.getText().isEmpty() ? "ans÷" : "÷");
        sin.setOnAction(event -> output = isSecond ? isHyp ? display.getText() + "sinh⁻¹(" : display.getText() + "sin⁻¹(" : isHyp ? display.getText() + "sinh(" : display.getText() + "sin(");
        cos.setOnAction(event -> output = isSecond ? isHyp ? display.getText() + "cosh⁻¹(" : display.getText() + "cos⁻¹(" : isHyp ? display.getText() + "cosh(" : display.getText() + "cos(");
        tan.setOnAction(event -> output = isSecond ? isHyp ? display.getText() + "tanh⁻¹(" : display.getText() + "tan⁻¹(" : isHyp ? display.getText() + "tanh(" : display.getText() + "tan(");
        log.setOnAction(event -> output = isSecond ? display.getText() + "10ˣ" : display.getText() + "log(");
        ln.setOnAction(event -> output = isSecond ? display.getText() + "eˣ" : display.getText() + "log(");
        pi.setOnAction(event -> {
            if (isSecond) isHyp = true;
            else display.setText(display.getText() + "π");
        });
        exponent.setOnAction(event -> output = display.getText().isEmpty() ? "ansⁿ" : "ⁿ");
        square.setOnAction(event -> output = display.getText().isEmpty() ? "ans²" : "²");
        negativeOne.setOnAction(event -> output = display.getText().isEmpty() ? "ans⁻¹" : "⁻¹");
        scientific.setOnAction(event -> output = display.getText().isEmpty() ? "ans*10ⁿ" : "*10ⁿ");
        fraction.setOnAction(event -> output = "");
        delete.setOnAction(event -> {
            if (!display.getText().isEmpty()) display.setText(display.getText(0, display.getText().length() - 1));
        });
        clear.setOnAction(event -> display.setText(""));
        table.setOnAction(event -> output = "");
        data.setOnAction(event -> output = "");
        prb.setOnAction(event -> output = "");
        leftParenthesis.setOnAction(event -> output = display.getText() + "(");
        rightParenthesis.setOnAction(event -> output = display.getText() + ")");
        xyz.setOnAction(event -> output = "");
        sto.setOnAction(event -> output = "");
        toggle.setOnAction(event -> output = "");
        enter.setOnAction(event -> parse());
        mode.setOnAction(event -> output = "");
        second.setOnAction(event -> isSecond = true);

        list.forEach(button -> button.setOnAction(event -> {
            if (output != null) {
                display.setText(display.getText() + output);
            }
        }));
    }

    private void parse() {

    }

}
