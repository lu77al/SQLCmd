package ua.kh.lual.sqlcmd;

public class MyUtils {

    public static String[] resizeArray(String[] arr, int newLength) {
        String[] newArr = new String[newLength];
        System.arraycopy(arr, 0, newArr, 0, Math.min(arr.length,newLength));
        return newArr;
    }

    public static Object[] resizeArray(Object[] arr, int newLength) {
        Object[] newArr = new Object[newLength];
        System.arraycopy(arr, 0, newArr, 0, Math.min(arr.length,newLength));
        return newArr;
    }

    public static String rowToString(Object[] items) {
        StringBuilder result = new StringBuilder("| ");
        for (Object item: items) {
            result.append(item);
            result.append(" |\t");
        }
        return result.toString();
    }

}
