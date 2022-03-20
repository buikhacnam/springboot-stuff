package com.buinam.schedulemanger.utils;

public class CommonUtils {
    public static Long safeToLong(Object input) {
        Long output = 0L;
        try {
            if (input == null) {
                return output;
            }
            return Long.valueOf(input.toString());
        } catch (Exception e) {
            // logger.warn("Error happened", e);
            System.out.println("Error happened " + e);
            output = 0L;
        }
        return output;
    }
}
