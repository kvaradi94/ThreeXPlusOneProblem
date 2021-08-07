package com.kvaradi.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ThreeXPlusOneProblem {

    private static List<BigInteger> series = new ArrayList<>();
    private static List<BigInteger> maximums = new ArrayList<>();
    private static List<Integer> lengths = new ArrayList<>();
    private static List<Long> executionTimes = new ArrayList<>();
    private static long startTime;
    private static boolean firstIteration = true;
    private static String startNumber = "1";
    private static String endNumber = "1000";
    private static BigInteger step;

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreeXPlusOneProblem.class);

    private ThreeXPlusOneProblem() {}

    public static void main(final String[] args) {
        if (args.length == Constants.TEST_ONE_NUMBER) {
            testArgNumber(args[0]);
            LOGGER.info("Max of the series: " + Collections.max(series));
            LOGGER.info("Size of the series: " + series.size());
            return;
        }
        if (args.length == Constants.TEST_AN_INTERVAL) {
            startNumber = args[0];
            endNumber = args[1];
        }

        startInCli();
    }

    private static void startInCli() {
        final BigInteger startNumber = new BigInteger(ThreeXPlusOneProblem.startNumber);
        final BigInteger endNumber = new BigInteger(ThreeXPlusOneProblem.endNumber);
        step = calculateStep(startNumber, endNumber);

        for (BigInteger i = startNumber; BigIntOperations.isLessOrEqual(i, endNumber); i = i.add(BigInteger.ONE)) {
            startTime = System.nanoTime();
            if (searchCounterExample(i)) {
                LOGGER.info("Possible counter example: ");
                LOGGER.info(i.toString());
                break;
            }
            final long endTime = System.nanoTime();
            final long duration = endTime - startTime;
            executionTimes.add(duration);
            maximums.add(Collections.max(series));
            lengths.add(series.size());
            if (BigIntOperations.divisible(i, step)) {
                LOGGER.info("Progress: " + i + " from " + ThreeXPlusOneProblem.endNumber);
                LOGGER.info("Max of maximums: " + Collections.max(maximums));
                LOGGER.info("Max of lengths: " + Collections.max(lengths));
                LOGGER.info("Max of execution times in sec: " + Collections.max(executionTimes) / Constants.DIVIDE_NANO_TO_GET_SEC);
            }
            series.clear();
            firstIteration = false;
        }
    }

    private static boolean searchCounterExample(final BigInteger inputNumber) {
        if (isTheProcessingTakesTooLong()) {
            return true;
        }
        series.add(inputNumber);
        if (BigIntOperations.divisible(inputNumber, Constants.TWO)) { //even
            searchCounterExample(BigIntOperations.divide(inputNumber, Constants.TWO));
        } else if (inputNumber.equals(Constants.ONE)) {
            return false;
        } else {
            searchCounterExample(BigIntOperations.multipleThreeThenAddOne(inputNumber));
        }
        return false;
    }

    private static boolean searchCounterExample(final BigInteger inputNumber, final boolean testArgNumberFlag) {
        if (isTheProcessingTakesTooLong()) {
            return true;
        }
        if (testArgNumberFlag) {
            LOGGER.info(inputNumber.toString());
        }
        series.add(inputNumber);
        if (BigIntOperations.divisible(inputNumber, Constants.TWO)) { //even
            searchCounterExample(BigIntOperations.divide(inputNumber, Constants.TWO), testArgNumberFlag);
        } else if (inputNumber.equals(Constants.ONE)) {
            return false;
        } else {
            searchCounterExample(BigIntOperations.multipleThreeThenAddOne(inputNumber), testArgNumberFlag);
        }
        return false;
    }

    private static void testArgNumber(final String arg) {
        final BigInteger number = new BigInteger(arg);
        searchCounterExample(number, true);
    }

    private static boolean isTheProcessingTakesTooLong() {
        if (!firstIteration) {
            final long currentTime = System.nanoTime();
            final long currentProcessTimeOnSpecificNumber = currentTime - startTime;
            if (currentProcessTimeOnSpecificNumber > Collections.max(executionTimes) * Constants.MULTIPLIER_FOR_MAXIMUM_TIMEOUT) {
                return true;
            }
        }
        return false;
    }

    private static BigInteger calculateStep(final BigInteger start, final BigInteger end) {
        final BigInteger diffOfStartAndEnd = BigIntOperations.subtract(end, start);
        if (BigIntOperations.isLess(diffOfStartAndEnd, BigInteger.TEN)) {
            return BigInteger.ONE;
        }
        return BigIntOperations.divide(diffOfStartAndEnd, BigInteger.TEN);
    }


}
