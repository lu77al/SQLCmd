package ua.kh.lual.sqlcmd.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemoryDBManager implements DatabaseManager {
    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public Set<String> getTableNames() {
        return null;
    }

    @Override
    public void connect(String database, String user, String password) {

    }

    @Override
    public Set<String> getTableHeader(String tableName) {
        return null;
    }

    @Override
    public List<List> getAllContent(String tableName) {
        return null;
    }

    @Override
    public List<List> getFilteredContent(String tableName, Map<String, Object> key) {
        return null;
    }

    @Override
    public void clearTable(String tableName) {

    }

    @Override
    public void insert(String tableName, Map<String, Object> record) {

    }

    @Override
    public void update(String tableName, Map<String, Object> update, Map<String, Object> where) {

    }

    @Override
    public void delete(String tableName, Map<String, Object> key) {

    }

    @Override
    public void createTable(String tableName, Set<String> columns) {

    }

    @Override
    public void dropTable(String tableName) {

    }
}
