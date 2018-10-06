package org.litespring.utils;

import java.math.BigInteger;

public class NumberUtils {

    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;

        //Handle minus sign，if present
        if(value.startsWith("-")) {
            negative = true;
            index++;
        }
    }
}
