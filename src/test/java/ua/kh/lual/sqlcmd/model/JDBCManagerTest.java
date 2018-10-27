package ua.kh.lual.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class JDBCManagerTest {

    private static final String database =  "sqlcmd";
    private static final String user =      "postgres";
    private static final String password =  "12345";
    private static final String table =     "users";

    private DatabaseManager dbManager;


    @Before
    public void setup() {
        dbManager = new JDBCManager();
        dbManager.connect(database, user, password);
    }

    @Test
    public void testGetTableNames() {
        String[] tableNames = dbManager.getTableNames();
        assertEquals("[users, pets]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetColumnNames() {
        assertEquals("[id, name, password]", Arrays.toString(dbManager.getTableHeader(table    )));
    }

    @Test
    public void testAddGetTableData() {
        dbManager.clearTable(table);
        DataSet record = new DataSet();
        record.put("id", 1);
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        dbManager.insert(table, record);
        record.put("id", 2);
        record.put("name", "Manya");
        record.put("password", "parol");
        dbManager.insert(table, record);
        assertEquals("[[1, Vasya, PAROL], [2, Manya, parol]]", Arrays.deepToString(dbManager.getAllContent(table)));
    }

    @Test
    public void testUpdateTable() {
        testAddGetTableData();
        DataSet updateRecord = new DataSet();
        updateRecord.put("password", "baraban");
        DataSet whereRecord = new DataSet();
        whereRecord.put("name", "Vasya");
        dbManager.update(table, updateRecord, whereRecord);
        assertEquals("[[2, Manya, parol], [1, Vasya, baraban]]", Arrays.deepToString(dbManager.getAllContent(table)));
    }

    @Test
    public void createDropTable() {
        testAddGetTableData();
        dbManager.createTable("testcreate", new String[]{"iD", "Model", "Price"});
        assertEquals("[iD, Model, Price]", Arrays.deepToString(dbManager.getTableHeader("testcreate")));
        dbManager.dropTable("testcreate");
        assertEquals(-1, Arrays.toString(dbManager.getTableNames()).indexOf("testcreate"));
    }

    @Test
    public void testIsConnected() {
        assertTrue(dbManager.isConnected());
    }

}
