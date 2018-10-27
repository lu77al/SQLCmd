package ua.kh.lual.sqlcmd.model;

public interface DatabaseManager {

    String[] getTableNames();

    void connect(String database, String user, String password);

    void selectTable(String tableName);

    String[] getTableHeader();

    Object[][] getAllContent();

    Object[][] getFilteredContent(DataSet key);

    void clearTable();

    void insert(DataSet record);

    void update(DataSet update, DataSet where);

    void delete(DataSet key);

    void createTable(String tableName, String[] columns);

    void dropTable(String tableName);

    boolean isConnected();
}
