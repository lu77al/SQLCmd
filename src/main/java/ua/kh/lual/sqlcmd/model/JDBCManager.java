package ua.kh.lual.sqlcmd.model;


import java.sql.*;

import static ua.kh.lual.sqlcmd.utils.MyUtils.resizeArray;

public class JDBCManager implements DatabaseManager {

    private Connection connection;
    private String selectedTable;

    @Override
    public void connect(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new JDBCManagerException("Can't process databases\n" +
                     "\tPlease add postgresql-42.2.5.jar to project");
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, user, password);
        } catch (SQLException e) {
            connection = null;
            throw new JDBCManagerException(
                    String.format("Can't get connection for database:<%s> user:<%s> password:<%s>",
                    database, user, password ));
        }
    }

    @Override
    public String[] getTableNames() {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT table_name FROM information_schema.tables" +
                    " WHERE table_schema='public' AND table_type='BASE TABLE'");
            String[] tables = new String[20];
            int index = 0;
            while (rs.next()) {
                if (index >= tables.length) {
                    tables = resizeArray(tables, tables.length + 20);
                }
                tables[index++] = rs.getString("table_name");
            }
            tables = resizeArray(tables, index);
            rs.close();
            st.close();
            return tables;
        } catch (SQLException e) {
            throw new JDBCManagerException("Can't get tables names");
        }
    }

    @Override
    public void selectTable(String tableName) {
        selectedTable = "\"" + tableName + "\"";
    }

    @Override
    public String[] getColumnNames() {
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM " + selectedTable + " WHERE false");
            ResultSetMetaData md = st.getMetaData();
            String[] columnNames = new String[md.getColumnCount()];
            for (int i = 0; i < columnNames.length; i++) {
                columnNames[i] = md.getColumnName(i + 1);
            }
            st.close();
            return columnNames;
        } catch (SQLException e) {
            throw new JDBCManagerException(String.format("Can't get table <%s> header", selectedTable));
        }
    }

    @Override
    public Object[][] getTableData() {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + selectedTable);
            Object[][] data = new Object[getTableSize()][rs.getMetaData().getColumnCount()];
            for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                if (!rs.next()) throw new SQLException();
                for (int colIndex = 1; colIndex <= data[0].length ; colIndex++) {
                    data[rowIndex][colIndex - 1] = rs.getObject(colIndex);
                }
            }
            rs.close();
            st.close();
            return data;
        } catch (SQLException e) {
            throw new JDBCManagerException(String.format("Can't get table <%s> content", selectedTable));
        }
    }

    @Override
    public void clearTable() {
        try {
            executeSQL("DELETE FROM " + selectedTable);
        } catch (SQLException e) {
            throw new JDBCManagerException(String.format("Can't clear table <%s>", selectedTable));
        }
    }

    @Override
    public void addRow(DataSet row) {
        String names = prepareList("\"%s\"", row.getNames());
        String values = prepareList("'%s\'", row.getValues());
        try {
            executeSQL("INSERT INTO " + selectedTable +" (" + names + ") VALUES (" + values + ")");
        } catch (SQLException e) {
            throw new JDBCManagerException(String.format("Can't insert data into table <%s>", selectedTable));
        }
    }

    @Override
    public void updateTable(DataSet set, DataSet where) {
        String setList = prepareList("\"%s\" = '%s'", set.getNames(), set.getValues());
        String whereList = prepareList("\"%s\" = '%s'", where.getNames(), where.getValues());
        try {
            executeSQL("UPDATE " + selectedTable + " SET " + setList + " WHERE " + whereList);
        } catch (SQLException e) {
            throw new JDBCManagerException(String.format("Can't update table <%s>", selectedTable));
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    private int getTableSize() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + selectedTable);
        rs.next();
        int size = rs.getInt(1);
        rs.close();
        st.close();
        return size;
    }

    private void executeSQL(String query) throws SQLException {
        Statement st = connection.createStatement();
        st.executeUpdate(query);
        st.close();
    }

    private String prepareList(String item, Object[] values) {
        StringBuilder list = new StringBuilder();
        for (Object value : values) {
            list.append(String.format(item, value.toString()));
            list.append(", ");
        }
        return list.substring(0, list.length() - 2);
    }

    private String prepareList(String item, Object[] values1, Object[] values2) {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < values1.length; i++) {
            list.append(String.format(item, values1[i].toString(), values2[i].toString()));
            list.append(", ");
        }
        return list.substring(0, list.length() - 2);
    }
}

