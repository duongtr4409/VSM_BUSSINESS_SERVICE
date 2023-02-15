package com.vsm.business.utils;

public class OTPUtils {
    public static String generate(int length) {
        String result = "";
        while (result.length() < length) {
            result += (int) Math.floor(Math.random() * (9 - 0 + 1));
        }
        return result;
    }
}
