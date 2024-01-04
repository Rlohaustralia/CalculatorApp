package com.eojin.calculator;
import java.text.DecimalFormat;

public class Calculator {

    // Static decimal format for consistent number formatting
    static DecimalFormat decimalFormat = new DecimalFormat("###,###.#####");

    public double calculate(double resultNum, double lastNum, char operator) {

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


    // Formats a numeric string with thousand separators
    public String getDecimalString (String thousandSeparator) {
        String setDeciamlString = thousandSeparator.replace(",","");
        return decimalFormat.format(Double.parseDouble(setDeciamlString));
    }

    // Removes commas from a numeric string
    public String getIntegerString (String intString) {
        return intString.replace(",","");
    }

}
