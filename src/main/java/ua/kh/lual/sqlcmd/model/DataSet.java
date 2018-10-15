package ua.kh.lual.sqlcmd.model;

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
    private int length = 0;

    public void put(String name, Object value) {
        for (int i = 0; i < length; i++) {
            if (data[i].getName().equals(name)) {
                data [i].value = value;
                return;
            }
        }
        if (length >= data.length) {
            Data[] newdata = new Data[data.length + sizeStep];
            System.arraycopy(data, 0, newdata, 0, data.length);
            data = newdata;
        }
        data[length++] = new Data(name, value);
    }

    public Object[] getValues() {
        Object[] result = new Object[length];
        for (int i = 0; i < length; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    public Object getValue(String name) {
        for (int i = 0; i < length; i++) {
            if (data[i].getName() == name) {
                return data[i].getValue();
            }
        }
        return null;
    }



    @Override
    public String toString() {
        return "DataSet{\n" +
                "names:" + Arrays.toString(getNames()) + "\n" +
                "values:" + Arrays.toString(getValues()) + "\n" +
                "}";
    }
}
