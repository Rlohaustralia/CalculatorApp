/**
 * -----------------------------------------------------------------------------
 * FILE:         CalculatorApp.java
 * DESCRIPTION:  Android app for a simple calculator.
 * AUTHOR:       Eojin Ra
 * CREATED:      20 Dec 2023
 * -----------------------------------------------------------------------------
 * USAGE:
 * This activity serves as the main screen of the calculator app. It includes
 * basic arithmetic operations and a simple user interface for numeric input.
 * -----------------------------------------------------------------------------
 * MODIFICATIONS:
 * Date            Author          Description
 * -----------------------------------------------------------------------------
 * 25 Dec 2023     Eojin Ra       Initial version
 * 27 Dec 2023     Eojin Ra       Error handling
 * 28 Dec 2023     Eojin Ra       Added the function of showing calculation process
 * TODO: Add support for decimal points in input.
 * -----------------------------------------------------------------------------
 */

package com.eojin.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class CalculatorApp extends AppCompatActivity {


    TextView display;
    TextView calculation_process;
    boolean isFirstInput = true;
    char operator = '+';
    int result = 0;
    final String CLEAR_VALUE = "0";
    DecimalFormat decimalFormat = new DecimalFormat("###,###.#####");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
        calculation_process = findViewById(R.id.calculation_process);
    }


    public void numButton(View view) {
        Button getButton = findViewById(view.getId());
        setText(getButton);
        calculationProcess(getButton);
    }

    public void operatorButton(View view) {
        Button getButton = findViewById(view.getId());

        if (view.getId() == R.id.addition_button
                || view.getId() == R.id.subtraction_button
                || view.getId() == R.id.multiplication_button
                || view.getId() == R.id.division_button) {

            if(isFirstInput) {

                operator = getButton.getText().toString().charAt(0);
                calculationProcess(getButton);

            } else {
                String tempStr = getIntegerString(display.getText().toString());
                int lastNum = Integer.parseInt(tempStr);
                result = calculate(result, lastNum, operator);
                operator = getButton.getText().toString().charAt(0);
                display.setText(getDecimalString(String.valueOf(result)));
                calculationProcess(getButton);
                isFirstInput = true;

            }


        } else if (view.getId() == R.id.equals_button) {

            if(isFirstInput){

                result = 0;
                operator = '+';
                clearText();

            } else {
                String tempStr = getIntegerString(display.getText().toString());
                result = calculate(result, Integer.parseInt(tempStr), operator);
                display.setText(getDecimalString(String.valueOf(result)));
                calculation_process.setText(getDecimalString(String.valueOf(result)));
                isFirstInput = true;
            }

        }
    }



    public void functionButton(View view) {

        Button getButton = findViewById(view.getId());

        if (view.getId() == R.id.all_clear_button) {

            result = 0;
            operator = '+';
            clearText();

            // Toggle the sign of the displayed number
        } else if (view.getId() == R.id.change_sign_button) {

            String currentText = getIntegerString(display.getText().toString());
            int currentNum = Integer.parseInt(currentText);
            currentNum = -currentNum;
            display.setText(getDecimalString(String.valueOf(currentNum)));
            calculation_process.setText(getDecimalString(String.valueOf(currentNum)));

            if(isFirstInput) {
                result = currentNum;
            } else {
                result = 0;
            }



        } else if (view.getId() == R.id.back_space_button) {

            String getResult = getIntegerString(display.getText().toString());
            if (getResult.length() > 1) {
                String substring = getResult.substring(0,getResult.length()-1);
                result = Integer.parseInt(substring);
                display.setText(getDecimalString(substring));
                calculation_process.setText(getDecimalString(substring));
                result = 0;
            } else {
                result = 0;
                clearText();
            }

        } else if (view.getId() == R.id.button_decimal) {

            if (isFirstInput) {
                display.setTextColor(0xFF000000);
                display.setText("0.");
                calculation_process.setText("0.");
                isFirstInput = false;

            } else {
                display.append(".");
                calculation_process.append(".");

            }
        }
    }


    public int calculate(int resultNum, int lastNum, char operator) {

        switch (operator) {
            case ('+'):
                resultNum += lastNum;
                break;
            case ('−'):
                resultNum -= lastNum;
                break;
            case ('x'):
                resultNum *= lastNum;
                break;
            case ('÷'):
                resultNum /= lastNum;
                break;

        } return resultNum;
    }



    public void setText(Button clickButton) {

        if(isFirstInput) {
            
            // Read which button user clicked
            display.setTextColor(0xFF000000); // Set text color in black when it is clicked
            display.setText(clickButton.getText().toString());
            isFirstInput = false;

        } else {
            
            String getDisplay = display.getText().toString().replace(",","");
            getDisplay = getDisplay + clickButton.getText().toString();
            String getDecimalString = getDecimalString(getDisplay);
            display.setText(getDecimalString);
        }
    }


    public String getDecimalString (String thousandSeparator) {

        String setDeciamlString = thousandSeparator.replace(",","");
        return decimalFormat.format(Double.parseDouble(setDeciamlString));
    }

    public String getIntegerString (String intString) {
        return intString.replace(",","");
    }


    public void clearText() {

        isFirstInput = true;
        display.setTextColor(0xFF949194);
        display.setText(CLEAR_VALUE);
        calculation_process.setTextColor(0xFF949194);
        calculation_process.setText(CLEAR_VALUE);
    }


    public void calculationProcess(Button clickButton) {

        String checkZero = calculation_process.getText().toString();

        if (checkZero.equals("0")) {
            calculation_process.setText(clickButton.getText().toString());
        } else {
            calculation_process.append(clickButton.getText().toString());
        }
    }
}