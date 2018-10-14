package ua.kh.lual.sqlcmd;

import java.util.Arrays;

public class DataSet {

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

    private final int sizeStep = 10;
    private Data[] data = new Data[sizeStep];
    private int newIndex = 0;

    public void put(String name, Object value) {
        for (int i = 0; i < newIndex ; i++) {
            if (data[i].getName().equals(name)) {
                data [i].value = value;
                return;
            }
        }
        if (newIndex >= data.length) {
            Data[] newdata = new Data[data.length + sizeStep];
            System.arraycopy(data, 0, newdata, 0, data.length);
            data = newdata;
        }
        data[newIndex++] = new Data(name, value);
    }

    public Object[] getValues() {
        Object[] result = new Object[newIndex];
        for (int i = 0; i < newIndex; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[newIndex];
        for (int i = 0; i < newIndex; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return "DataSet{\n" +
                "names:" + Arrays.toString(getNames()) + "\n" +
                "values:" + Arrays.toString(getValues()) + "\n" +
                "}";
    }
}
