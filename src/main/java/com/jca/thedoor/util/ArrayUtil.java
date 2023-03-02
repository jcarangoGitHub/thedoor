package com.jca.thedoor.util;

import java.util.List;

public class ArrayUtil {
    public static boolean isArrayNullOrEmpty(String[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isListStringNullOrEmpty(List<String> arr) {
        return arr == null || arr.isEmpty() || arr.contains("");
    }
}
