package com.udacity.stockhawk.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by daniel on 05/05/2017.
 */

public class StockFormat {

    private static final DecimalFormat dollarFormat =  (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
    private static final DecimalFormat dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
    private static final DecimalFormat percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());


    public static String dollarFormat(float value){
        return dollarFormat.format(value);
    }

    public static String dollarFormatWithPlus(float value){
        dollarFormatWithPlus.setPositivePrefix("+$");
        return dollarFormatWithPlus.format(value);
    }

    public static String percentageFormat(float value){
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
        return percentageFormat.format(value);
    }


}
