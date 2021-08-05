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
    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");
    private static final int MULTIPLIER_FOR_MAXIMUM_TIMEOUT = 100;
    private static final double DIVIDE_NANO_TO_GET_SEC = 1_000_000_000;
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

        for (BigInteger i = startNumber; isLessOrEqual(i, endNumber); i = i.add(BigInteger.ONE)) {
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
            if (divisible(i, step)) {
                System.out.println("Progress: " + i + " from " + ThreeXPlusOneProblem.endNumber);
                System.out.println("Max of maximums: " + Collections.max(maximums));
                System.out.println("Max of lengths: " + Collections.max(lengths));
                System.out.println("Max of execution times in sec: " + Collections.max(executionTimes) / DIVIDE_NANO_TO_GET_SEC);
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
        if (divisible(inputNumber, TWO)) { //even
            searchCounterExample(divide(inputNumber, TWO));
        } else if (inputNumber.equals(ONE)) {
            return false;
        } else {
            searchCounterExample(multipleThreeThenAddOne(inputNumber));
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
        if (divisible(inputNumber, TWO)) { //even
            searchCounterExample(divide(inputNumber, TWO), testArgNumberFlag);
        } else if (inputNumber.equals(ONE)) {
            return false;
        } else {
            searchCounterExample(multipleThreeThenAddOne(inputNumber), testArgNumberFlag);
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
            if (currentProcessTimeOnSpecificNumber > Collections.max(executionTimes) * MULTIPLIER_FOR_MAXIMUM_TIMEOUT) {
                return true;
            }
        }
        return false;
    }

    private static BigInteger calculateStep(BigInteger start, BigInteger end) {
        BigInteger diffOfStartAndEnd = subtract(end, start);
        if (isLess(diffOfStartAndEnd, BigInteger.TEN)) {
            return BigInteger.ONE;
        }
        return divide(diffOfStartAndEnd, BigInteger.TEN);
    }

    private static boolean isLess(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1;
    }

    private static boolean isLessOrEqual(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1 || firstNumber.compareTo(secondNumber) == 0;
    }

    private static boolean divisible(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.mod(secondNumber).equals(ZERO);
    }

    private static BigInteger divide(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.divide(secondNumber);
    }

    private static BigInteger multipleThreeThenAddOne(BigInteger number) {
        return number.multiply(THREE).add(ONE);
    }

    private static BigInteger subtract(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.subtract(secondNumber);
    }
}
