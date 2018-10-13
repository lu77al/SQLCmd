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
        TableRecord record = new TableRecord();
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        dbManager.create(record);

        TableRecord[] users = dbManager.getTableRecords("user");
        assertEquals(1, users.length);

        assertEquals("[name, password]", Arrays.toString(users[0].getNames()));
        assertEquals("[Vasya, PAROL]", Arrays.toString(users[0].getValues()));

    }

    @Test
    public void testUpdateData() {
        dbManager.clear("user");
        TableRecord record = new TableRecord();
        record.put("name", "Vasya");
        record.put("password", "PAROL");
        dbManager.create(record);

        TableRecord updateRecord = new TableRecord();
        updateRecord.put("password", "baraban");
        TableRecord whereRecord = new TableRecord();
        whereRecord.put("name", "Vasya");
        dbManager.update(updateRecord, whereRecord);

        TableRecord[] users = dbManager.getTableRecords("user");
        assertEquals(1, users.length);

        assertEquals("[name, password]", Arrays.toString(users[0].getNames()));
        assertEquals("[Vasya, baraban]", Arrays.toString(users[0].getValues()));

    }

}
