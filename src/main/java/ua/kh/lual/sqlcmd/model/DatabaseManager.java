package ua.kh.lual.sqlcmd.model;

public interface DatabaseManager {

    String[] getTableNames();

    void connect(String database, String user, String password);

    void selectTable(String tableName);

    String[] getColumnNames();

    Object[][] getTableData();

    void clearTable();

    void addRow(DataSet record);

    void updateTable(DataSet update, DataSet where);

    boolean connected();
}
