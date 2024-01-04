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
 * 28 Dec 2023     Eojin Ra       Add the function of showing calculation process
 * 03 Jan 2024     Eojin Ra       Add support for decimal points in input
 * 02 Jan 2024     Eojin Ra       Create 'Calculator' class, Organise the code, Add annotation
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
    TextView calculation_process;
    boolean isFirstInput = true;
    char operator = '+';
    double result = 0;
    final String CLEAR_VALUE = "0";


    Calculator calculator = new Calculator();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
        calculation_process = findViewById(R.id.calculation_process);
    }


    // Handles numeric button clicks
    public void numButton(View view) {
        Button getButton = findViewById(view.getId()); // Find the numeric button based on the clicked view
        setDisplay(getButton); // Update the display with the clicked numeric button
        setCalculationProcess(getButton); // Update the calculation process with the clicked numeric button
    }


    // Handles operator button clicks
    public void operatorButton(View view) {
        Button getButton = findViewById(view.getId());

        if (view.getId() == R.id.addition_button
                || view.getId() == R.id.subtraction_button
                || view.getId() == R.id.multiplication_button
                || view.getId() == R.id.division_button) {

            if(isFirstInput) {
                // If it's the first input, set the operator and update the calculation process
                operator = getButton.getText().toString().charAt(0);
                setCalculationProcess(getButton);

            } else {
                // If it's not the first input, perform the calculation with the previous operator
                String tempStr = calculator.getIntegerString(display.getText().toString());
                double lastNum = Double.parseDouble(tempStr);
                result = calculator.calculate(result, lastNum, operator);
                operator = getButton.getText().toString().charAt(0);
                display.setText(calculator.getDecimalString(String.valueOf(result)));
                setCalculationProcess(getButton);
                isFirstInput = true; // Reset isFirstInput for the next input
            }


        } else if (view.getId() == R.id.equals_button) {
            // If the equals button is clicked

            if(isFirstInput){
                // If it's the first input, reset the calculator and clear the display
                result = 0;
                operator = '+';
                clearText();

            } else {
                // If it's not the first input, perform the calculation and display the result
                String tempStr = calculator.getIntegerString(display.getText().toString());
                result = calculator.calculate(result, Double.parseDouble(tempStr), operator);
                display.setText(calculator.getDecimalString(String.valueOf(result)));
                calculation_process.setText(calculator.getDecimalString(String.valueOf(result)));
                isFirstInput = true;
            }
        }
    }



    // Handles special function button clicks
    public void functionButton(View view) {

        if (view.getId() == R.id.all_clear_button) {
            // If the "All Clear" button is clicked, reset the calculator and clear the display
            result = 0;
            operator = '+';
            clearText();

        } else if (view.getId() == R.id.change_sign_button) {
            // If the "Change Sign" button is clicked, toggle the sign of the displayed number

            // Get the current displayed number as text
            String currentText = calculator.getIntegerString(display.getText().toString());
            // Parse the text to a double
            double currentNum = Double.parseDouble(currentText);
            // Toggle the sign
            currentNum = -currentNum;
            // Update the display and calculation process with the new signed number
            display.setText(calculator.getDecimalString(String.valueOf(currentNum)));
            calculation_process.setText(calculator.getDecimalString(String.valueOf(currentNum)));

            if(isFirstInput) {
                // If it's the first input, update the result with the signed number
                result = currentNum;
                isFirstInput = false;
            } else {
                // If it's not the first input, reset the result
                result = 0;
            }


        } else if (view.getId() == R.id.back_space_button) {
            // If the "Backspace" button is clicked, remove the last digit or clear the display

            // Get the current displayed number as text
            String getResult = calculator.getIntegerString(display.getText().toString());

            if (getResult.length() > 1) {
                // If there's more than one digit, remove the last digit
                String substring = getResult.substring(0, getResult.length() - 1);
                result = Double.parseDouble(substring);
                // Update the display and calculation process with the new number
                display.setText(calculator.getDecimalString(substring));
                calculation_process.setText(calculator.getDecimalString(substring));
                // Reset the result for the next input
                result = 0;

            } else {
                // If there's only one digit, reset the calculator
                result = 0;
                clearText();
            }
            // Set isFirstInput to false for the next input
            isFirstInput = false;


        } else if (view.getId() == R.id.button_decimal) {
            // If the decimal button is clicked, add a decimal point to the displayed number

            if (isFirstInput) {
                // If it's the first input, set the display and calculation process to "0." and update isFirstInput
                display.setTextColor(0xFF000000);
                display.setText("0.");
                calculation_process.setText("0.");
                isFirstInput = false;

            } else {
                // If it's not the first input, append a decimal point to the display and calculation process
                display.append(".");
                calculation_process.append(".");
            }
        }
    }


    // Sets the display based on the clicked numeric button
    public void setDisplay(Button clickButton) {

        if (isFirstInput) {
            // If it's the first input, set the text color to black and update the display
            display.setTextColor(0xFF000000); // Set text color in black when it is clicked
            display.setText(clickButton.getText().toString());
            isFirstInput = false;

        } else {
            // If it's not the first input, append the clicked button text to the current display text

            // Get the current display text without commas
            String getDisplay = display.getText().toString().replace(",", "");
            // Concatenate the clicked button text
            getDisplay = getDisplay + clickButton.getText().toString();
            // Format the resulting string and update the display
            String getDecimalString = calculator.getDecimalString(getDisplay);
            display.setText(getDecimalString);
        }
    }


    // Sets the calculation process based on the clicked button
    public void setCalculationProcess(Button clickButton) {
        // Get the current text in the calculation process
        String currentText = calculation_process.getText().toString();

        if (currentText.equals("0")) {
            // If the calculation process is currently "0", replace it with the clicked button text
            calculation_process.setText(clickButton.getText().toString());

        } else {
            // If the calculation process is not "0", append the clicked button text to the current text
            calculation_process.append(clickButton.getText().toString());
        }
    }


    // Clears the display and calculation process, resetting the calculator
    public void clearText() {
        // Set isFirstInput to true to indicate a new input
        isFirstInput = true;

        // Set text color and content in both display and calculation process to default values
        display.setTextColor(0xFF949194);
        display.setText(CLEAR_VALUE);
        calculation_process.setTextColor(0xFF949194);
        calculation_process.setText(CLEAR_VALUE);
    }
}