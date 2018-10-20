package ua.kh.lual.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import ua.kh.lual.sqlcmd.model.DataSet;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.model.JDBCManager;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class JDBCManagerTest {

    private static final String database =  "sqlcmd";
    private static final String user =      "postgres";
    private static final String password =  "12345";
    private static final String tableName = "users";

    private DatabaseManager dbManager;


    @Before
    public void setup() {
        dbManager = new JDBCManager();
        dbManager.connect(database, user, password);
        dbManager.selectTable(tableName);
    }

    @Test
    public void testGetTableNames() {
        String[] tableNames = dbManager.getTableNames();
        assertEquals("[users, pets]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetColumnNames() {
        assertEquals("[id, name, password]", Arrays.toString(dbManager.getColumnNames()));
    }

    @Test
    public void testAddGetTableData() {
        dbManager.clearTable();
        DataSet record = new DataSet();
        record.put("id", 1);
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        dbManager.addRow(record);
        record.put("id", 2);
        record.put("name", "Manya");
        record.put("password", "parol");
        dbManager.addRow(record);
        assertEquals("[[1, Vasya, PAROL], [2, Manya, parol]]", Arrays.deepToString(dbManager.getTableData()));
    }

    @Test
    public void testUpdateTable() {
        testAddGetTableData();
        DataSet updateRecord = new DataSet();
        updateRecord.put("password", "baraban");
        DataSet whereRecord = new DataSet();
        whereRecord.put("name", "Vasya");
        dbManager.updateTable(updateRecord, whereRecord);
        assertEquals("[[2, Manya, parol], [1, Vasya, baraban]]", Arrays.deepToString(dbManager.getTableData()));
    }

    @Test
    public void testIsConnected() {
        assertTrue(dbManager.isConnected());
    }

}
