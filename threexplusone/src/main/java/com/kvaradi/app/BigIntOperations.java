package com.kvaradi.app;

import java.math.BigInteger;

public final class BigIntOperations {

    private BigIntOperations() {}

    public static boolean isLess(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1;
    }

    public static boolean isLessOrEqual(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1 || firstNumber.compareTo(secondNumber) == 0;
    }

    public static boolean divisible(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.mod(secondNumber).equals(Constants.ZERO);
    }

    public static BigInteger divide(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.divide(secondNumber);
    }

    public static BigInteger multipleThreeThenAddOne(final BigInteger number) {
        return number.multiply(Constants.THREE).add(Constants.ONE);
    }

    public static BigInteger subtract(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.subtract(secondNumber);
    }

    public static BigInteger add(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.add(secondNumber);
    }
}
