package ua.kh.lual.sqlcmd;

import java.util.Arrays;

public class TableRecord {

    private class Data {
        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    private Data[] data = new Data[100];
    private int index = 0;

    public void put(String columnName, Object value) {
        data[index++] = new Data(columnName, value);
    }

    public Object[] getValues() {
        Object[] result = new Object[index];
        for (int i = 0; i < index ; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[index];
        for (int i = 0; i < index ; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return "TableRecord{\n" +
                "names:" + Arrays.toString(getNames()) + "\n" +
                "values:" + Arrays.toString(getValues()) + "\n" +
                "}";
    }
}
