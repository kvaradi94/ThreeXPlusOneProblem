package com.kvaradi.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calculation {

    private final List<BigInteger> series = new ArrayList<>();
    private final List<BigInteger> maximums = new ArrayList<>();
    private final List<Integer> lengths = new ArrayList<>();
    private final List<Long> executionTimes = new ArrayList<>();
    private long startTime;
    private boolean firstIteration = true;
    private String startNumber = "1";
    private String endNumber = "1000";

    private final Logger logger = LoggerFactory.getLogger(Calculation.class);

    public void testInterval() {
        final BigInteger startNumber = new BigInteger(this.startNumber);
        final BigInteger endNumber = new BigInteger(this.endNumber);
        final BigInteger step = calculateStep(startNumber, endNumber);

        for (BigInteger i = startNumber; BigIntOperations.isLessOrEqual(i, endNumber); i = i.add(BigInteger.ONE)) {
            startTime = System.nanoTime();
            if (searchCounterExample(i)) {
                logger.info("Possible counter example: ");
                logger.info(i.toString());
                break;
            }
            final long endTime = System.nanoTime();
            final long duration = endTime - startTime;
            executionTimes.add(duration);
            maximums.add(Collections.max(series));
            lengths.add(series.size());
            if (BigIntOperations.divisible(i, step)) {
                logger.info("Progress: " + i + " from " + endNumber);
                logger.info("Max of maximums: " + Collections.max(maximums));
                logger.info("Max of lengths: " + Collections.max(lengths));
                logger.info("Max of execution times in sec: " + Collections.max(executionTimes) / Constants.DIVIDE_NANO_TO_GET_SEC);
            }
            series.clear();
            firstIteration = false;
        }
    }

    public void testArgNumber(final String arg) {
        final BigInteger number = new BigInteger(arg);
        searchCounterExample(number, true);
        logger.info("Max of the series: " + getMaxOfSeries());
        logger.info("Size of the series: " + getSizeOfSeries());
    }

    public void createChartForOneNumber() {
        //TODO
    }

    public void setStartNumber(final String startNumber) {
        this.startNumber = startNumber;
    }

    public void setEndNumber(final String endNumber) {
        this.endNumber = endNumber;
    }

    private BigInteger getMaxOfSeries() {
        return Collections.max(this.series);
    }

    private int getSizeOfSeries() {
        return this.series.size();
    }

    private boolean searchCounterExample(final BigInteger inputNumber) {
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

    private boolean searchCounterExample(final BigInteger inputNumber, final boolean testArgNumberFlag) {
        if (isTheProcessingTakesTooLong()) {
            return true;
        }
        if (testArgNumberFlag) {
            logger.info(inputNumber.toString());
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

    private boolean isTheProcessingTakesTooLong() {
        if (!firstIteration) {
            final long currentTime = System.nanoTime();
            final long currentProcessTimeOnSpecificNumber = currentTime - startTime;
            return currentProcessTimeOnSpecificNumber > Collections.max(executionTimes) * Constants.MULTIPLIER_FOR_MAXIMUM_TIMEOUT;
        }
        return false;
    }

    private BigInteger calculateStep(final BigInteger start, final BigInteger end) {
        final BigInteger diffOfStartAndEnd = BigIntOperations.subtract(end, start);
        if (BigIntOperations.isLess(diffOfStartAndEnd, BigInteger.TEN)) {
            return BigInteger.ONE;
        }
        return BigIntOperations.divide(diffOfStartAndEnd, BigInteger.TEN);
    }
}
