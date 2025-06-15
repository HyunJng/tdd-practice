package org.example.demo.common.service;

public class StringUtils {

    public static boolean isDigit(String str) {
        return str != null && str.chars().allMatch(Character::isDigit);
    }
}
