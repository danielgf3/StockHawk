package com.udacity.stockhawk.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by daniel on 05/05/2017.
 */

public class StockFormat {

    private static final DecimalFormat dollarFormat =  (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
    private static final DecimalFormat dollarFormatWithPlus = configDollarFormatWithPlus();
    private static final DecimalFormat percentageFormat = configPercentageFormat();

    private static DecimalFormat configPercentageFormat(){
        DecimalFormat percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
        percentageFormat.setNegativePrefix("-");
        return percentageFormat;
    }

    private static DecimalFormat configDollarFormatWithPlus(){
        DecimalFormat dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
        dollarFormatWithPlus.setPositivePrefix("+$");
        dollarFormatWithPlus.setNegativePrefix("-$");
        return dollarFormatWithPlus;
    }


    public static String dollarFormat(float value){
        return dollarFormat.format(value);
    }

    public static String dollarFormatWithPlus(float value){
        return dollarFormatWithPlus.format(value);
    }

    public static String percentageFormat(float value){
        return percentageFormat.format(value);
    }


}
