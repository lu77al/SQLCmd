package ua.kh.lual.sqlcmd;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JDBCManagerTest {

    private DatabaseManager dbManager;

    @Before
    public void setup() {
        dbManager = new JDBCManager();
        dbManager.connect("sqlcmd", "postgres", "tlp250");
    }

    @Test
    public void testGetAllTableNames() {
        String[] tablenames = dbManager.getTableNames();
        assertEquals("[user]", Arrays.toString(tablenames));
    }

    @Test
    public void testGetTableData() {
        dbManager.clear("user");
        DataSet record = new DataSet();
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        dbManager.create(record);

        DataSet[] users = dbManager.getTableRecords("user");
        assertEquals(1, users.length);

        assertEquals("[name, password]", Arrays.toString(users[0].getNames()));
        assertEquals("[Vasya, PAROL]", Arrays.toString(users[0].getValues()));

    }

    @Test
    public void testUpdateData() {
        dbManager.clear("user");
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
