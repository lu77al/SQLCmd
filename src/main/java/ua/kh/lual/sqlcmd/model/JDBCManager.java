package ua.kh.lual.sqlcmd.model;


import java.sql.*;
import java.util.Arrays;

import static ua.kh.lual.sqlcmd.model.MyUtils.resizeArray;

public class JDBCManager implements DatabaseManager {
    private Connection connection;
    private String table;

    public static void main(String[] argv) throws ClassNotFoundException, SQLException {
        JDBCManager db = new JDBCManager();
        db.dynamicMain();
    }

    public void dynamicMain() throws ClassNotFoundException, SQLException {
/*
        String user = "postgres";
        String databse = "sqlcmd";
        String password = "tlp250";
        Connection connection = connect(user, databse, password);
*/
        connect("sqlcmd", "postgres", "tlp250");

        getTableNames();

        clear("user");

        DataSet record = new DataSet();
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        create(record);

        String tableName = "user";

        DataSet[] result = getTableRecords(tableName);
        System.out.println(Arrays.toString(result));

        DataSet updateRec = new DataSet();
        updateRec.put("password", "******");
        DataSet whereRec = new DataSet();
        whereRec.put("name", "Vasya");

        update(updateRec, whereRec);

        result = getTableRecords(tableName);
        System.out.println(Arrays.toString(result));



        /*
        Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO public.user VALUES ('y_sFatima', 'y_spppp')");
         */

        /*
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM public.user WHERE name = 'y_sFatima'");
        */
        /*
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE public.user SET password = 'putin' WHERE name = 'James'");
        stmt.close();

        String sqlrec = "SELECT * FROM public.user ORDER BY name ASC";
        selectSQL(connection, sqlrec);

        //TODO see preparestatement ...
        connection.close();
        */

        System.out.println("Done :)");
    }

    @Override
    public void connect(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Please add postgresql-42.2.5.jar to project");
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, user, password);
        } catch (SQLException e) {
            System.out.println(String.format("Can't get connection for database:%s user:%s password:%s", database, user, password ));
            e.printStackTrace();
            connection = null;
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
                            // tables = Arrays.copyOf(tables, index, String[].class); - possible variant
            rs.close();
            st.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    @Override
    public void selectTable(String tableName) {
        table = tableName;
    }

    @Override
    public String[] getColumnNames() {
        try {
            PreparedStatement st = connection.prepareStatement(String.format("SELECT * FROM public.%s WHERE false", table));
            ResultSetMetaData md = st.getMetaData();
            String[] columnNames = new String[md.getColumnCount()];
            for (int i = 0; i < columnNames.length; i++) {
                columnNames[i] = md.getColumnName(i + 1);
            }
            st.close();
            return columnNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    @Override
    public Object[][] getTableData() {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM public.%s", table));
            Object[][] data = new Object[getTableSize()][rs.getMetaData().getColumnCount()];
            for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                if (!rs.next()) throw new SQLException();
                for (int colImdex = 1; colImdex <= data[0].length ; colImdex++) {
                    data[rowIndex][colImdex - 1] = rs.getObject(colImdex);
                }
            }
            rs.close();
            st.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }

    private int getTableSize() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(String.format("SELECT COUNT(*) FROM public.%s", table));
        rs.next();
        int size = rs.getInt(1);
        rs.close();
        st.close();
        return size;
    }

    @Override
    public DataSet[] getTableRecords(String tableName) {
        try {
            int size = getTableSize();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM public.%s ORDER BY name ASC", tableName));
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            while (rs.next()) {
                DataSet rec = new DataSet();
                result[index++] = rec;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    rec.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            rs.close();
            st.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataSet[0];
        }
    }


    private static void selectSQL(Connection connection, String sqlrec) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sqlrec);
        while (rs.next())
        {
            System.out.println(rs.getString(1) + " | " + rs.getString("password"));
        }
        rs.close();
        st.close();
    }

    @Override
    public void clear(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public." + tableName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(DataSet record) {
        try {
            String names = "";
            for (String name: record.getNames()) {
                names += name + ",";
            }
            names = names.substring(0, names.length() - 1);
            String values = "";
            for (Object value: record.getValues()) {
                values += "'" + value.toString() + "',";
            }
            values = values.substring(0, values.length() - 1);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO public.users (" + names + ") VALUES (" + values + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DataSet update, DataSet where) {
        try {
            String updateSQL = "";
            String[] names = update.getNames();
            Object[] values = update.getValues();
            for (int i = 0; i < names.length; i++) {
                updateSQL += names[i] + " = '" + values[i].toString() + "',";
            }
            updateSQL = updateSQL.substring(0, updateSQL.length() - 1);
            names = where.getNames();
            values = where.getValues();
            String whereSQL = names[0] + " = '" + values[0].toString() + "'";

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE public.user SET " + updateSQL + " WHERE " + whereSQL);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
