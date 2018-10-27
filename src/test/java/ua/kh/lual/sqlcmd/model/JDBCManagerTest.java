package ua.kh.lual.sqlcmd.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JDBCManagerTest {

    private static final String database =  "sqlcmd";
    private static final String user =      "postgres";
    private static final String password =  "12345";
    private static final String table =     "modeltesttable";

    private DatabaseManager dbManager;

    private void createTable() {
        try {
            dbManager.createTable(table, new String[]{"name", "password"});
        } catch (Exception e) {
            // Just catch
        }
    }

    private void dropTable() {
        try {
            dbManager.dropTable(table);
        } catch (Exception e) {
            // Just catch
        }
    }

    private void fillTable() throws Exception {
        DataSet row = new DataSet();
        row.put("name", "Vasiliy");
        row.put("password", "parol");
        dbManager.insert(table, row);
        row.put("name", "Marina");
        row.put("password", "hook");
        dbManager.insert(table, row);
    }

    @Before
    public void setup() {
        dbManager = new JDBCManager();
        dbManager.connect(database, user, password);
    }

    @Test
    public void testConnect() {
        assertTrue(dbManager.isConnected());
    }

    @Test
    public void testCreateTable() {
        boolean failed = false;
        try {
            dbManager.createTable(table, new String[]{"name", "password"});
        } catch (Exception e) {
            failed = true;
        }
        dropTable();
        assertFalse(failed);
    }


    @Test
    public void testDropTable() {
        createTable();
        boolean failed = false;
        try {
            dbManager.dropTable(table);
        } catch (Exception e) {
            failed = true;
        }
        assertFalse(failed);
    }

    @Test
    public void testGetTableNames() {
        createTable();
        String tables = Arrays.toString(dbManager.getTableNames());
        dropTable();
        assertTrue(tables.indexOf(table) != -1);
    }

    @Test
    public void testGetTableHeader() {
        createTable();
        String header = Arrays.toString(dbManager.getTableHeader(table));
        dropTable();
        assertEquals("[name, password]", header);
    }

    @Test
    public void testInsert() {
        createTable();
        boolean failed = false;
        try {
            fillTable();
        } catch (Exception e) {
            failed = true;
        }
        dropTable();
        assertFalse(failed);
    }

    @Test
    public void testGetAllContent() {
        createTable();
        try {
            fillTable();
        } catch (Exception e) {
            // Just catch
        }
        Object[][] content = dbManager.getAllContent(table);
        dropTable();
        assertEquals("[[Vasiliy, parol], [Marina, hook]]", Arrays.deepToString(content));
    }

    @Test
    public void testGetFilteredContent() {
        createTable();
        try {
            fillTable();
        } catch (Exception e) {
            // Just catch
        }
        DataSet key = new DataSet();
        key.put("name", "Vasiliy");
        Object[][] content = dbManager.getFilteredContent(table, key);
        dropTable();
        assertEquals("[[Vasiliy, parol]]", Arrays.deepToString(content));
    }

    @Test
    public void testClearTable() {
        createTable();
        try {
            fillTable();
            dbManager.clearTable(table);
        } catch (Exception e) {
            // Just catch
        }
        Object[][] content = dbManager.getAllContent(table);
        dropTable();
        assertEquals("[]", Arrays.deepToString(content));
    }

    @Test
    public void testDelete() {
        createTable();
        try {
            fillTable();
        } catch (Exception e) {
            // Just catch
        }
        DataSet key = new DataSet();
        key.put("name", "Vasiliy");
        dbManager.delete(table, key);
        Object[][] content = dbManager.getAllContent(table);
        dropTable();
        assertEquals("[[Marina, hook]]", Arrays.deepToString(content));
    }

    @Test
    public void testUpdate() {
        createTable();
        try {
            fillTable();
        } catch (Exception e) {
            // Just catch
        }
        DataSet key = new DataSet();
        key.put("name", "Vasiliy");
        DataSet update = new DataSet();
        update.put("password", "ChertPoberi");
        dbManager.update(table, update, key);
        Object[][] content = dbManager.getAllContent(table);
        dropTable();
        assertEquals("[[Marina, hook], [Vasiliy, ChertPoberi]]", Arrays.deepToString(content));
    }
}
