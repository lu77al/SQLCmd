package ua.kh.lual.sqlcmd.model;

import java.util.List;
import java.util.Set;

public interface DatabaseManager {

    boolean isConnected();

    Set<String> getTableNames();

    void connect(String database, String user, String password);

    Set<String> getTableHeader(String tableName);

    List<List> getAllContent(String tableName);

    List<List> getFilteredContent(String tableName, DataSet key);

    void clearTable(String tableName);

    void insert(String tableName, DataSet record);

    void update(String tableName, DataSet update, DataSet where);

    void delete(String tableName, DataSet key);

    void createTable(String tableName, String[] columns);

    void dropTable(String tableName);

}
