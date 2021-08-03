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
    private static final double DEVIDE_NANO_TO_GET_SEC = 1_000_000_000;
    private static final String START_NUMBER = "1";
    private static final String END_NUMBER = "10000";
    private static final BigInteger STEP = new BigInteger("1000");

    public static void main(String[] args) {
        final BigInteger startNumber = new BigInteger(START_NUMBER);
        final BigInteger endNumber = new BigInteger(END_NUMBER);

        for (BigInteger i = startNumber; isLess(i, endNumber); i = i.add(BigInteger.ONE)) {//NOPMD
            startTime = System.nanoTime();
            if (searchCounterExample(i)) {
                System.out.println("Possible counter example: ");
                System.out.println(i);
                break;
            }
            final long endTime = System.nanoTime();
            final long duration = endTime - startTime;
            executionTimes.add(duration);
            if (divisible(i, STEP)) {
                System.out.println("Progress: " + i + " from " + END_NUMBER);
                System.out.println("Max of maximums: " + Collections.max(maximums));
                System.out.println("Max of lengths: " + Collections.max(lengths));
                System.out.println("Max of execution times in sec: " + Collections.max(executionTimes) / DEVIDE_NANO_TO_GET_SEC);
            }
            maximums.add(Collections.max(series));
            lengths.add(series.size());
            series.clear();
            firstIteration = false;
        }
    }

    private static boolean searchCounterExample(BigInteger inputNumber) {
        if(isTheProcessingTakesTooLong()) {
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

    private static boolean isTheProcessingTakesTooLong()  {
        if(!firstIteration) {
            final long currentTime = System.nanoTime();
            final long currentProcessTimeOnSpecificNumber = currentTime - startTime;
            if(currentProcessTimeOnSpecificNumber > Collections.max(executionTimes)* MULTIPLIER_FOR_MAXIMUM_TIMEOUT) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLess(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1;
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
}
