package ua.kh.lual.sqlcmd.utils;

public class TextTable {
    private Object[] header;
    private Object[][] content;
    private int margin;


    public TextTable(Object[] header, Object[][] content, int margin) {
        this.header = header;
        this.content = content;
        this.margin = margin;
    }

    @Override
    public String toString() {
        int[] columnsWidth = getColumnsWidth();
        String horizontalLine = getTableHorizontalLine(columnsWidth);
        String result = horizontalLine + '\n';
        result += getTableRow(header, columnsWidth) + '\n';
        if (content.length != 0) {
            result += horizontalLine + '\n';
            for (Object[] row : content) {
                result += getTableRow(row, columnsWidth) + '\n';
            }
        }
        result += horizontalLine;
        return result;
    }

    static final String nullString = "[null]";

    private int[] getColumnsWidth() {
        int[] columnWidth = new int[header.length];
        for (int i = 0; i < header.length; i++) {
            columnWidth[i] = header[i].toString().length();
            for (Object[] rowCells: content) {
                int width;
                if (rowCells[i] != null) {
                    width = rowCells[i].toString().length();
                } else {
                    width = nullString.length();
                }
                if (width > columnWidth[i]) {
                    columnWidth[i] = width;
                }
            }
            columnWidth[i] += margin * 2;
        }
        return columnWidth;
    }

    private String getTableHorizontalLine(int[] columnWidth) {
        StringBuilder result = new StringBuilder("+");
        for (int width: columnWidth) {
            for (int i = 0; i < width; i++) {
                result.append('-');
            }
            result.append('+');
        }
        return result.toString();
    }

    private String getTableRow(Object[] items, int[] columnWidth) {
        StringBuilder result = new StringBuilder("+");
        for (int i = 0; i < items.length; i++) {
            String cell = (items[i] != null) ? items[i].toString() : nullString;
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
