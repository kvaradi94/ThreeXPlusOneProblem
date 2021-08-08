package com.kvaradi.app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.util.Collections;

public class ChartGenerator extends ApplicationFrame {

    private final String firstNumber;
    final Calculation calculation = new Calculation();

    public ChartGenerator(String applicationTitle, String chartTitle, String firstNumber) {
        super(applicationTitle);
        this.firstNumber = firstNumber;
        calculation.testArgNumber(firstNumber);
        final String categoryAxisLabel = "Max of the series: " + Collections.max(calculation.getSeries()) +
                ", Size of the series: " + calculation.getSeries().size();
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                categoryAxisLabel, "Current value",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        setContentPane(chartPanel);
    }

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < calculation.getSeries().size(); i++) {
            dataset.addValue(calculation.getSeries().get(i), "values", i);
        }
        return dataset;
    }
}