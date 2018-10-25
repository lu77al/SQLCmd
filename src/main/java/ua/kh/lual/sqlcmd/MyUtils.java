package ua.kh.lual.sqlcmd;

import com.sun.deploy.util.StringUtils;

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

    public static String tableToString(String[] header, Object[][] content, int margin) {
        int[] columnsWidth = getColumnsWidth(header, content, margin);
        String horizontalLine = getTableHorizontalLine(columnsWidth);
        String lineEnd = System.lineSeparator();
        String result = horizontalLine + lineEnd;
        result += getTableRow(header, columnsWidth) + lineEnd;
        if (content.length != 0) {
            result += horizontalLine + lineEnd;
            for (Object[] row : content) {
                result += getTableRow(row, columnsWidth) + lineEnd;
            }
        }
        result += horizontalLine;
        return result;
    }

    private static int[] getColumnsWidth(String[] header, Object[][] content, int margin) {
        int[] columnWidth = new int[header.length];
        for (int i = 0; i < header.length; i++) {
            columnWidth[i] = header[i].length();
            for (Object[] rowCells: content) {
                int width = rowCells[i].toString().length();
                if (width > columnWidth[i]) {
                    columnWidth[i] = width;
                }
            }
            columnWidth[i] += margin * 2;
        }
        return columnWidth;
    }

    private static String getTableHorizontalLine(int[] columnWidth) {
        StringBuilder result = new StringBuilder("+");
        for (int width: columnWidth) {
            for (int i = 0; i < width; i++) {
                result.append('-');
            }
            result.append('+');
        }
        return result.toString();
    }

    private static String getTableRow(Object[] items, int[] columnWidth) {
        StringBuilder result = new StringBuilder("+");
        for (int i = 0; i < items.length; i++) {
            String cell = items[i].toString();
            int frontMargin = (columnWidth[i] - cell.length()) / 2;
            for (int j = 0; j < frontMargin; j++) {
                result.append(' ');
            }
            result.append(cell);
            for (int j = 0; j < columnWidth[i] - cell.length() - frontMargin; j++) {
                result.append(' ');
            }
            result.append('+');
        }
        return result.toString();
    }

}
