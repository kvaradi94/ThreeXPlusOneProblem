package com.kvaradi.app.chartbuilder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.util.Collections;

public class BasicLineChartBuilder extends AbstractChartBuilder {

    private final transient String firstNumber;

    public BasicLineChartBuilder(final String firstNumber) {
        super();
        this.firstNumber = firstNumber;
    }

    @Override
    public void generateChart() {
        calculation.testArgNumber(firstNumber);
        final String categoryAxisLabel = "Max of the series: " + Collections.max(calculation.getSeries()) +
                ", Size of the series: " + calculation.getSeries().size();
        final JFreeChart lineChart = ChartFactory.createLineChart(
                "3x+1 analysis",
                categoryAxisLabel, "Current value",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        final ChartPanel chartPanel = new ChartPanel(lineChart);
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    private DefaultCategoryDataset createDataset() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < calculation.getSeries().size(); i++) {
            dataset.addValue(calculation.getSeries().get(i), "values", i);
        }
        return dataset;
    }
}
