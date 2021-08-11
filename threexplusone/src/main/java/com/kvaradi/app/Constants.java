package com.kvaradi.app;

import java.math.BigInteger;

public class Constants {
    public static final BigInteger ZERO = BigInteger.ZERO;
    public static final BigInteger ONE = BigInteger.ONE;
    public static final BigInteger TWO = new BigInteger("2");
    public static final BigInteger THREE = new BigInteger("3");
    public static final int MULTIPLIER_FOR_MAXIMUM_TIMEOUT = 100;
    public static final double DIVIDE_NANO_TO_GET_SEC = 1_000_000_000;
    public static final double DIVIDE_NANO_TO_GET_MS = 1_000_000;
    public static final int TEST_ONE_NUMBER = 2; //run app with 1 number + 1 mode arg
    public static final int TEST_AN_INTERVAL = 3; //run app with 2 number + 1 mode arg
    public static final String CLI_ARG_VALUE = "cli";
    public static final String CHART_FOR_TESTING_ONE_NUMBER_ARG_VALUE = "chart";
    public static final String APPLICATION_TITLE = "3x + 1 Problem";
}
