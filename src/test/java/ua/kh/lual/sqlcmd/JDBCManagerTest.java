package ua.kh.lual.sqlcmd;

import org.junit.Before;
import org.junit.Test;
import ua.kh.lual.sqlcmd.model.DataSet;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.model.JDBCManager;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JDBCManagerTest {

    private DatabaseManager dbManager;

    @Before
    public void setup() {
        dbManager = new JDBCManager();
        dbManager.connect("sqlcmd", "postgres", "12345");
    }

    @Test
    public void testGetTableNames() {
        String[] tableNames = dbManager.getTableNames();
        assertEquals("[users, pets]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetColumnNames() {
        dbManager.selectTable("users");
        assertEquals("[id, name, password]", Arrays.toString(dbManager.getColumnNames()));
    }

    @Test
    public void testAddGetTableData() {
        dbManager.selectTable("users");
        dbManager.clear("users");
        DataSet record = new DataSet();
        record.put("id", 1);
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        dbManager.create(record);
        record.put("id", 2);
        record.put("name", "Manya");
        record.put("password", "parol");
        dbManager.create(record);
        assertEquals("[[1, Vasya, PAROL], [2, Manya, parol]]", Arrays.deepToString(dbManager.getTableData()));
    }

/*
    @Test
    public void testGetTableData() {
        DataSet record = new DataSet();
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        dbManager.create(record);

        DataSet[] users = dbManager.getTableRecords("user");
        assertEquals(1, users.length);

        assertEquals("[name, password]", Arrays.toString(users[0].getNames()));
        assertEquals("[Vasya, PAROL]", Arrays.toString(users[0].getValues()));

    }
*/

    @Test
    public void testUpdateData() {
        dbManager.clear("users");
        DataSet record = new DataSet();
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        dbManager.create(record);

        DataSet updateRecord = new DataSet();
        updateRecord.put("password", "baraban");
        DataSet whereRecord = new DataSet();
        whereRecord.put("name", "Vasya");
        dbManager.update(updateRecord, whereRecord);

        DataSet[] users = dbManager.getTableRecords("user");
        assertEquals(1, users.length);

        assertEquals("[name, password]", Arrays.toString(users[0].getNames()));
        assertEquals("[Vasya, baraban]", Arrays.toString(users[0].getValues()));

    }

}
