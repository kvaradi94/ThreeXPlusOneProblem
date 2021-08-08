package com.kvaradi.app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.Collections;

public class ChartGenerator extends ApplicationFrame {

    private final String firstNumber;
    final Calculation calculation = new Calculation();

    public ChartGenerator(String firstNumber) {
        super(Constants.APPLICATION_TITLE);
        this.firstNumber = firstNumber;
    }

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < calculation.getSeries().size(); i++) {
            dataset.addValue(calculation.getSeries().get(i), "values", i);
        }
        return dataset;
    }

    public void generateChart() {
        calculation.testArgNumber(firstNumber);
        final String categoryAxisLabel = "Max of the series: " + Collections.max(calculation.getSeries()) +
                ", Size of the series: " + calculation.getSeries().size();
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Calculating 3x+1 series for number: " + firstNumber,
                categoryAxisLabel, "Current value",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}