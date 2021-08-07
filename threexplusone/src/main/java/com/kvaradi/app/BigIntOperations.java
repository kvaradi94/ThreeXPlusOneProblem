package com.kvaradi.app;

import java.math.BigInteger;

/* default */ class BigIntOperations {

    static boolean isLess(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1;
    }

    static boolean isLessOrEqual(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.compareTo(secondNumber) == -1 || firstNumber.compareTo(secondNumber) == 0;
    }

    static boolean divisible(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.mod(secondNumber).equals(Constants.ZERO);
    }

    static BigInteger divide(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.divide(secondNumber);
    }

    static BigInteger multipleThreeThenAddOne(BigInteger number) {
        return number.multiply(Constants.THREE).add(Constants.ONE);
    }

    static BigInteger subtract(BigInteger firstNumber, BigInteger secondNumber) {
        return firstNumber.subtract(secondNumber);
    }
}
