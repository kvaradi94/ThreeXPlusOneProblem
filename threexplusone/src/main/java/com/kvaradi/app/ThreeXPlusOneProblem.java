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
                ChartGenerator chartGenerator = new ChartGenerator(firstNumber);
                chartGenerator.generateChart();
            }
        }
        if (args.length == Constants.TEST_AN_INTERVAL && isCliMode) {
	    final String secondNumber = args[2];
            calculation.setStartNumber(firstNumber);
            calculation.setEndNumber(secondNumber);
            calculation.testInterval();
        }
    }

    private static boolean isCli(final String arg) {
        return arg.equals(Constants.CLI_ARG_VALUE);
    }

    private static boolean isChart(final String arg) {
        return arg.equals(Constants.CHART_FOR_TESTING_ONE_NUMBER_ARG_VALUE);
    }

}
