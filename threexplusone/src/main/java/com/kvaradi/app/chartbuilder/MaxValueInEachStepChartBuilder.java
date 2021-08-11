package com.kvaradi.app.chartbuilder;

import com.kvaradi.app.BigIntOperations;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.math.BigInteger;

public class MaxValueInEachStepChartBuilder extends AbstractChartBuilder {

    private final transient String firstNumber;
    private final transient String secondNumber;

    public MaxValueInEachStepChartBuilder(final String firstNumber, final String secondNumber) {
        super();
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    @Override
    public void generateChart() {
        final JFreeChart lineChart = ChartFactory.createLineChart(
                "3x+1 analysis",
                "", "Current value",
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
        calculation.setStartNumber(firstNumber);
        calculation.setEndNumber(secondNumber);
        calculation.testInterval();
        final BigInteger startNumber = new BigInteger(firstNumber);
        for (int i = 0; i < calculation.getXSeriesLength().size(); i++) {
            final BigInteger key = BigIntOperations.add(
                    startNumber, new BigInteger(String.valueOf(i)));
            dataset.addValue(calculation.getXSeriesLength().get(key), "values", key);
        }
        return dataset;
    }
}
