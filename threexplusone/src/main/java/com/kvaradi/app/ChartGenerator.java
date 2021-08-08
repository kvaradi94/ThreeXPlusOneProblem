package com.kvaradi.app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class ChartGenerator extends ApplicationFrame {

    private final String firstNumber;

    public ChartGenerator(String applicationTitle, String chartTitle, String firstNumber) {
        super(applicationTitle);
        this.firstNumber = firstNumber;
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Iteration", "Current value",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        ///chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane(chartPanel);
    }

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final Calculation calculation = new Calculation();
        calculation.testArgNumber(firstNumber);
        for (int i = 0; i < calculation.getSeries().size(); i++) {
            dataset.addValue(calculation.getSeries().get(i), "values", i);
        }
        return dataset;
    }
}