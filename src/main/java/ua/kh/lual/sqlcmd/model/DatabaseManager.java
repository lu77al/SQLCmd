package ua.kh.lual.sqlcmd.model;

public interface DatabaseManager {

    String[] getTableNames();

    void connect(String database, String user, String password);

    void selectTable(String tableName);

    String[] getTableHeader();

    Object[][] getTableContent();

    void dropTable();

    void insert(DataSet record);

    void update(DataSet update, DataSet where);

    boolean isConnected();
}
