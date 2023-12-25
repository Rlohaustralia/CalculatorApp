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
 * TODO: Implement error handling.
 * TODO: Add support for decimal points in input.
 * TODO: Implement the calculation process at the top.
 * -----------------------------------------------------------------------------
 */


package com.eojin.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorApp extends AppCompatActivity {


    TextView display;
    boolean isFirstInput = true;
    char operator = '+';
    int result = 0;
    final String CLEAR_VALUE = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
    }


    public void numButton(View view) {
        Button getButton = findViewById(view.getId());
        setText(getButton);
    }

    public void operatorButton(View view) {
        Button getButton = findViewById(view.getId());

        if (view.getId() == R.id.addition_button
            || view.getId() == R.id.subtraction_button
            || view.getId() == R.id.multiplication_button
            || view.getId() == R.id.division_button) {


            if(isFirstInput) {
                operator = getButton.getText().toString().charAt(0);
            } else {
                int lastNum = Integer.parseInt(display.getText().toString());
                result = calculate(result, lastNum, operator);
                operator = getButton.getText().toString().charAt(0);
                display.setText(String.valueOf(result));
                isFirstInput = true;

            }


        } else if (view.getId() == R.id.equals_button) {

            if(isFirstInput){
                clearText();
            } else {
//                int lastNum = Integer.parseInt(display.getText().toString());
                result = calculate(result, Integer.parseInt(display.getText().toString()), operator);
                display.setText(String.valueOf(result));
                isFirstInput = true;
            }

        }
    }



    public void functionButton(View view) {
        Button getButton = findViewById(view.getId());

        if (view.getId() == R.id.all_clear_button) {
            clearText();

            // Toggle the sign of the displayed number
        } else if (view.getId() == R.id.change_sign_button) {
            String currentText = display.getText().toString();
            int currentNum = Integer.parseInt(currentText);
            currentNum = -currentNum;
            display.setText(String.valueOf(currentNum));


        } else if (view.getId() == R.id.back_space_button) {
            if (display.getText().toString().length() > 1) {
                String getResult = display.getText().toString();
                String substring = getResult.substring(0,getResult.length()-1);
                display.setText(substring);
            } else {
                clearText();
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


    // Set text when numButtons are clicked
    public void setText(Button clickButton) {
        if(isFirstInput) {
            // Read which button user clicked
            display.setTextColor(0xFF000000); // Set text color in black when it is clicked
            display.setText(clickButton.getText().toString());
            isFirstInput = false;

        } else {
            display.append(clickButton.getText().toString());
        }
    }


    public void clearText() {
        isFirstInput = true;
        display.setTextColor(0xFF949194);
        display.setText(CLEAR_VALUE);

    }

}