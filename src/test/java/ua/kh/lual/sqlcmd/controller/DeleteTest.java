package ua.kh.lual.sqlcmd.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.kh.lual.sqlcmd.controller.command.Create;
import ua.kh.lual.sqlcmd.controller.command.Delete;
import ua.kh.lual.sqlcmd.model.DataSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

public class DeleteTest extends ABasicCommandTestClass {

    @Before
    public void setup() {
        setupMocks();
        cmd = new Delete();
    }

    @Test
    public void testDelete() {
        // given
        when(dbManager.getTableHeader("users")).thenReturn(new String[]{"id", "name", "password"});
//        DataSet key = new DataSet();
//        key.put("name", "Marlen");
        when(dbManager.getFilteredContent(eq("users"), any(DataSet.class))).thenReturn(new Object[][]{{"3", "Marlen", "jasasyn"}});
        // when
        cmd.process("delete|users|name|Marlen");
        // then
        assertOutput( "" +
                "+------+----------+------------+\n" +
                "+  id  +   name   +  password  +\n" +
                "+------+----------+------------+\n" +
                "+  3   +  Marlen  +  jasasyn   +\n" +
                "+------+----------+------------+\n" +
                "Rows above where deleted\n");
        verify(dbManager).delete(eq("users"), any(DataSet.class));
    }

    @Test
    public void testDeleteNothing() {
        // given
        when(dbManager.getTableHeader("users")).thenReturn(new String[]{"id", "name", "password"});
//        DataSet key = new DataSet();
//        key.put("name", "Marlen");
        when(dbManager.getFilteredContent(eq("users"), any(DataSet.class))).thenReturn(new Object[][]{});
        // when
        cmd.process("delete|users|name|Marlen");
        // then
        verify(view).write("Nothing matches key field. No delete performed");
        verify(dbManager, never()).delete(anyString(), any(DataSet.class));
    }

}
