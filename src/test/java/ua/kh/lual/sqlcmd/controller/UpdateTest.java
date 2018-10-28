package ua.kh.lual.sqlcmd.controller;

import org.junit.Before;
import org.junit.Test;
import ua.kh.lual.sqlcmd.controller.command.Delete;
import ua.kh.lual.sqlcmd.controller.command.Update;
import ua.kh.lual.sqlcmd.model.DataSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UpdateTest extends ABasicCommandTestClass {

    @Before
    public void setup() {
        setupMocks();
        cmd = new Update();
    }

    @Test
    public void testUpdate() {
        // given
        when(dbManager.getTableHeader("users")).thenReturn(new String[]{"id", "name", "password"});
        when(dbManager.getFilteredContent(eq("users"), any(DataSet.class))).thenReturn(new Object[][]{{"8", "Ferdinand", "ww1"}});
        // when
        cmd.process("update|users|password|peace|name|Ferdinand");
        // then
        assertOutput( "" +
                "+------+-------------+------------+\n" +
                "+  id  +    name     +  password  +\n" +
                "+------+-------------+------------+\n" +
                "+  8   +  Ferdinand  +    ww1     +\n" +
                "+------+-------------+------------+\n" +
                "Rows above where updated\n");
        verify(dbManager).update(eq("users"), any(DataSet.class), any(DataSet.class));
    }

    @Test
    public void testUpdateNothing() {
        // given
        when(dbManager.getTableHeader("users")).thenReturn(new String[]{"id", "name", "password"});
        when(dbManager.getFilteredContent(eq("users"), any(DataSet.class))).thenReturn(new Object[][]{});
        // when
        cmd.process("update|users|password|peace|name|Ferdinand");
        // then
        verify(view).write("Nothing matches key field. No update performed");
        verify(dbManager, never()).update(anyString(), any(DataSet.class), any(DataSet.class));
    }

}
