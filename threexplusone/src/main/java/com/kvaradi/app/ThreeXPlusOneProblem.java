package com.kvaradi.app;

public final class ThreeXPlusOneProblem {

    private ThreeXPlusOneProblem() {}

    public static void main(final String[] args) {
        final boolean isCliMode = isCli(args[0]);
        final boolean isChartMode = isChart(args[0]);
        final String firstNumber = args[1];
        final Calculation calculation = new Calculation();
        if (args.length == Constants.TEST_ONE_NUMBER) {
            if (isCliMode) {
                calculation.testArgNumber(firstNumber);
                return;
            } else if(isChartMode) {
                final ChartGenerator chartGenerator = new ChartGenerator(firstNumber);
                chartGenerator.generateBasicLineChart(DataKinds.ONE_NUMBER);
            }
        }
        if (args.length == Constants.TEST_AN_INTERVAL && isCliMode) {
            final String secondNumber = args[2];
            calculation.setStartNumber(firstNumber);
            calculation.setEndNumber(secondNumber);
            calculation.testInterval();
        } else if(isChartMode) {
            final String secondNumber = args[2];
            final ChartGenerator chartGenerator = new ChartGenerator(firstNumber, secondNumber);
            chartGenerator.generateBasicLineChart(DataKinds.INTERVAL_MAX_LENGTH_FOR_EACH_NUMBER);
        }
    }

    private static boolean isCli(final String arg) {
        return arg.equals(Constants.CLI_ARG_VALUE);
    }

    private static boolean isChart(final String arg) {
        return arg.equals(Constants.CHART_FOR_TESTING_ONE_NUMBER_ARG_VALUE);
    }

}
