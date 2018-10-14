package ua.kh.lual.sqlcmd;

public interface DatabaseManager {
    DataSet[] getTableRecords(String tableName);

    String[] getTableNames();

    void connect(String database, String user, String password);

    void clear(String tableName);

    void create(DataSet record);

    void update(DataSet update, DataSet where);
}
