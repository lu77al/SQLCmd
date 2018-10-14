package ua.kh.lual.sqlcmd;

import java.util.Arrays;

public class MyUtils {

    public static String[] expandArray(String[] arr, int step) {
        String[] newArr = new String[arr.length + step];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }

    public static Object[] expandArray(Object[] arr, int step) {
        Object[] newArr = new Object[arr.length + step];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }
}
