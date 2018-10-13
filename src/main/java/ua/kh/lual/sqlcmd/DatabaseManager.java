package ua.kh.lual.sqlcmd;

public interface DatabaseManager {
    TableRecord[] getTableRecords(String tableName);

    String[] getTableNames();

    void connect(String database, String user, String password);

    void clear(String tableName);

    void create(TableRecord record);

    void update(TableRecord update, TableRecord where);
}
