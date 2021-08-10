package com.kvaradi.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculation {

    private final transient List<BigInteger> series = new ArrayList<>();
    private final transient List<BigInteger> maximums = new ArrayList<>();
    private final transient List<Long> executionTimes = new ArrayList<>();
    private final transient List<BigInteger> numbersInTheInterval = new ArrayList<>();
    private final transient Map<BigInteger, Integer> xSeriesLength = new HashMap<>();
    private transient long startTime;
    private transient boolean firstIteration = true;
    private transient String startNumber = "1";
    private transient String endNumber = "1000";
    private final transient Logger logger = LoggerFactory.getLogger(Calculation.class);
    private final transient Map<BigInteger, Boolean> alreadyVisitedNumbers = new HashMap<>();

    public void testInterval() {
        final BigInteger startNumber = new BigInteger(this.startNumber);
        final BigInteger endNumber = new BigInteger(this.endNumber);
        final BigInteger step = calculateStep(startNumber, endNumber);

        final long startTimeOverall = System.nanoTime();
        for (BigInteger i = startNumber; BigIntOperations.isLessOrEqual(i, endNumber); i = i.add(BigInteger.ONE)) {
            startTime = System.nanoTime();
            numbersInTheInterval.add(i);
            if (searchCounterExample(i)) {
                logger.info("Possible counter example: ");
                logger.info(i.toString());
                break;
            }
            final long endTime = System.nanoTime();
            final long duration = endTime - startTime;
            executionTimes.add(duration);
            maximums.add(Collections.max(series));
            xSeriesLength.put(i, series.size());
            if (BigIntOperations.divisible(i, step)) {
                logger.info("Progress: " + i + " from " + endNumber);
                logger.info("Max of maximums: " + Collections.max(maximums));
                logger.info("Max of lengths: " + Collections.max(xSeriesLength.values()));
                logger.info("Max of execution times in sec: " + Collections.max(executionTimes) / Constants.DIVIDE_NANO_TO_GET_SEC);
            }
            series.clear();
            firstIteration = false;
        }
        final long endTimeOverall = System.nanoTime();
        final long durationOverall = (long) ((endTimeOverall - startTimeOverall) / Constants.DIVIDE_NANO_TO_GET_MS);
        logger.info("Overall execution time: " + durationOverall + " ms.");
    }

    public void testArgNumber(final String arg) {
        final BigInteger number = new BigInteger(arg);
        searchCounterExample(number, true);
        logger.info("Max of the series: " + getMaxOfSeries());
        logger.info("Size of the series: " + getSizeOfSeries());
    }

    public List<BigInteger> getSeries() {
        return series;
    }

    public Map<BigInteger, Integer> getXSeriesLength() {
        return xSeriesLength;
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
        if (alreadyVisitedNumbers.containsKey(inputNumber)) { //this is the optimization 1-10000 takes 24ms without and 1.4 with this optimization
            return false;
        }
        alreadyVisitedNumbers.put(inputNumber, true);
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
