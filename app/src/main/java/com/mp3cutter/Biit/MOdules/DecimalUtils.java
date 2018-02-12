package com.mp3cutter.Biit.MOdules;

import java.math.BigDecimal;

/**
 * This utility class rounds a decimal number.
 *
 * @author www.codejava.net
 */
public class DecimalUtils {

    public static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
}
