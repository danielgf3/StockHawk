package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daniel on 02/05/2017.
 */

public class MyXAxisValueFormatter implements IAxisValueFormatter {

    private Long[] mValues;
    private SimpleDateFormat mFormat;

    public MyXAxisValueFormatter(Long[] mValues) {
        this.mValues = mValues;
        mFormat = new SimpleDateFormat("dd/MM/yy");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date date = new Date(mValues[(int) value]);
        return mFormat.format(date);
    }
}
