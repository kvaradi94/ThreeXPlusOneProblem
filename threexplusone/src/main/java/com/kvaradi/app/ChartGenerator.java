package com.kvaradi.app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.math.BigInteger;

public class ChartGenerator extends ApplicationFrame {

    private static final long serialVersionUID = -6359175313085101703L;
    private final transient String firstNumber;
    private final transient String secondNumber;
    private final transient Calculation calculation = new Calculation();

    public ChartGenerator(final String firstNumber) {
        super(Constants.APPLICATION_TITLE);
        this.firstNumber = firstNumber;
        this.secondNumber = "";
    }

    public ChartGenerator(final String firstNumber, final String secondNumber) {
        super(Constants.APPLICATION_TITLE);
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public void generateBasicLineChart(final DataKinds kindOfTheData) {
        /*final String categoryAxisLabel = "Max of the series: " + Collections.max(calculation.getSeries()) +
                ", Size of the series: " + calculation.getSeries().size();*/
        final JFreeChart lineChart = ChartFactory.createLineChart(
                "3x+1 analysis",
                ""/*categoryAxisLabel*/, "Current value",
                createDataset(kindOfTheData),
                PlotOrientation.VERTICAL,
                true, true, false);

        final ChartPanel chartPanel = new ChartPanel(lineChart);
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    private DefaultCategoryDataset createDataset(final DataKinds kindOfTheData) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        switch (kindOfTheData) {
            case ONE_NUMBER:
                calculation.testArgNumber(firstNumber);
                for (int i = 0; i < calculation.getSeries().size(); i++) {
                    dataset.addValue(calculation.getSeries().get(i), "values", i);
                }
                break;
            case INTERVAL_MAX_LENGTH_FOR_EACH_NUMBER:
                calculation.setStartNumber(firstNumber);
                calculation.setEndNumber(secondNumber);
                calculation.testInterval();
                final BigInteger startNumber = new BigInteger(firstNumber);
                for (int i = 0; i < calculation.getXSeriesLength().size(); i++) {
                    final BigInteger key = BigIntOperations.add(
                            startNumber, new BigInteger(String.valueOf(i)));
                    dataset.addValue(calculation.getXSeriesLength().get(key), "values", key);
                }
                break;
        }
        return dataset;
    }


}