package com.kvaradi.app;

import java.math.BigInteger;

/* default */ final class BigIntOperations {

    private BigIntOperations() {}

    /* default */ static boolean isLess(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1;
    }

    /* default */ static boolean isLessOrEqual(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1 || firstNumber.compareTo(secondNumber) == 0;
    }

    /* default */ static boolean divisible(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.mod(secondNumber).equals(Constants.ZERO);
    }

    /* default */ static BigInteger divide(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.divide(secondNumber);
    }

    /* default */ static BigInteger multipleThreeThenAddOne(final BigInteger number) {
        return number.multiply(Constants.THREE).add(Constants.ONE);
    }

    /* default */ static BigInteger subtract(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.subtract(secondNumber);
    }

    /* default */ static BigInteger add(final BigInteger firstNumber, final BigInteger secondNumber) {
        return firstNumber.add(secondNumber);
    }
}
