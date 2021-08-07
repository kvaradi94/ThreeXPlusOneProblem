package com.kvaradi.app;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreeXPlusOneProblem {

    private static List<BigInteger> series = new ArrayList<>();
    private static List<BigInteger> maximums = new ArrayList<>();
    private static List<Integer> lengths = new ArrayList<>();
    private static List<Long> executionTimes = new ArrayList<>();
    private static long startTime;
    private static boolean firstIteration = true;
    private static String startNumber = "1";
    private static String endNumber = "10000";
    private static BigInteger step;

    public static void main(String[] args) {
        if (args.length == 1) {
            testArgNumber(args[0]);
            System.out.println("Max of the series: " + Collections.max(series));
            System.out.println("Size of the series: " + series.size());
            return;
        }
        if (args.length == 2) {
            startNumber = args[0];
            endNumber = args[1];
        }

        final BigInteger startNumber = new BigInteger(ThreeXPlusOneProblem.startNumber);
        final BigInteger endNumber = new BigInteger(ThreeXPlusOneProblem.endNumber);
        step = calculateStep(startNumber, endNumber);

        for (BigInteger i = startNumber; BigIntOperations.isLessOrEqual(i, endNumber); i = i.add(BigInteger.ONE)) {
            startTime = System.nanoTime();
            if (searchCounterExample(i)) {
                System.out.println("Possible counter example: ");
                System.out.println(i);
                break;
            }
            final long endTime = System.nanoTime();
            final long duration = endTime - startTime;
            executionTimes.add(duration);
            maximums.add(Collections.max(series));
            lengths.add(series.size());
            if (BigIntOperations.divisible(i, step)) {
                System.out.println("Progress: " + i + " from " + ThreeXPlusOneProblem.endNumber);
                System.out.println("Max of maximums: " + Collections.max(maximums));
                System.out.println("Max of lengths: " + Collections.max(lengths));
                System.out.println("Max of execution times in sec: " + Collections.max(executionTimes) / Constants.DIVIDE_NANO_TO_GET_SEC);
            }
            series.clear();
            firstIteration = false;
        }
    }

    private static boolean searchCounterExample(BigInteger inputNumber) {
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

    private static boolean searchCounterExample(BigInteger inputNumber, boolean testArgNumberFlag) {
        if (isTheProcessingTakesTooLong()) {
            return true;
        }
        if (testArgNumberFlag) {
            System.out.println(inputNumber);
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

    private static void testArgNumber(String arg) {
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

    private static BigInteger calculateStep(BigInteger start, BigInteger end) {
        BigInteger diffOfStartAndEnd = BigIntOperations.subtract(end, start);
        if (BigIntOperations.isLess(diffOfStartAndEnd, BigInteger.TEN)) {
            return BigInteger.ONE;
        }
        return BigIntOperations.divide(diffOfStartAndEnd, BigInteger.TEN);
    }


}
