package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.util.MyXAxisValueFormatter;
import com.udacity.stockhawk.util.MyYAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StockDetailActivity extends AppCompatActivity {

    private static final String TAG = "StockDetailActivity";

    public static final String ID_EXTRA_DATA = "symbol";

    private LineChart mLineChart;

    private String mSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mLineChart = (LineChart) findViewById(R.id.chart);

        mSymbol = getIntent().getStringExtra(ID_EXTRA_DATA);

        loadData(mSymbol);
    }

    private void loadData(String symbol){
        ((TextView) findViewById(R.id.tv_symbol)).setText(mSymbol);
        Cursor c = getContentResolver().query(
                Contract.Quote.makeUriForStock(symbol),
                null,
                null,
                null,
                null);
        try{
            if(c!=null) {
                c.moveToFirst();
                String currentPrice = "$ " + c.getString(Contract.Quote.POSITION_PRICE);
                String history = c.getString(Contract.Quote.POSITION_HISTORY);
                ((TextView) findViewById(R.id.tv_price)).setText(currentPrice);
                loadChart(history);
            }
        }finally {
            if(c != null && !c.isClosed()) c.close();
        }
    }

    private void loadChart(String history){
        if(history.equals("")){
            mLineChart.setVisibility(View.GONE);
            findViewById(R.id.tv_empty_history).setVisibility(View.VISIBLE);
            return;
        }

        List<Entry> entries = new ArrayList<>();
        int numOfWeek = 0;
        List<Long> priceValues = new ArrayList<>();

        List<String> listHistory = Arrays.asList(history.split("\n"));
        Collections.reverse(listHistory);

        for(String item: listHistory){
            String[] parserItem = item.split(", ");
            try {
                entries.add(new Entry(numOfWeek, Float.parseFloat(parserItem[1])));
                priceValues.add(Long.parseLong(parserItem[0]));
                numOfWeek++;
            }catch (Exception e){}
        }

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.chart_price));
        dataSet.setColor(Color.BLACK);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setCircleColor(Color.RED);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(priceValues.toArray(new Long[priceValues.size()])));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(Color.BLACK);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        mLineChart.getAxisRight().setEnabled(false); // no right axis
        YAxis yAxisL = mLineChart.getAxisLeft();
        yAxisL.setValueFormatter(new MyYAxisValueFormatter());
        yAxisL.setTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);
        mLineChart.setAutoScaleMinMaxEnabled(true);
        mLineChart.setDrawBorders(false);
        mLineChart.setKeepPositionOnRotation(true);
        mLineChart.setData(lineData);
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
