package org.example.demo.common.util;

public class StringUtils {

    public static boolean isDigit(String str) {
        return str != null && str.chars().allMatch(Character::isDigit);
    }
}
