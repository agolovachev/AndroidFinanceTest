package ru.javabegin.training.android6.finance.activities.reports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import ru.javabegin.training.android6.finance.R;

public class BaseReportActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_report);

        barChart = (BarChart) findViewById(R.id.bar_chart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 10f));
        barEntries.add(new BarEntry(1, 15f));
        barEntries.add(new BarEntry(2, 20f));
        barEntries.add(new BarEntry(3, 25f));
        barEntries.add(new BarEntry(4, 30f));
        barEntries.add(new BarEntry(5, 25f));
        barEntries.add(new BarEntry(6, 40f));


        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");

        ArrayList<String> dates = new ArrayList<>();
        dates.add("April");
        dates.add("May");
        dates.add("June");
        dates.add("July");
        dates.add("August");
        dates.add("September");

        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);
        barChart.setTouchEnabled(true);
        barChart.invalidate();
    }
}
