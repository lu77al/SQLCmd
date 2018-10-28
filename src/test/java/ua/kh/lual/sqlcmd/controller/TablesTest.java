package ua.kh.lual.sqlcmd.controller;

import org.junit.Before;
import org.junit.Test;
import ua.kh.lual.sqlcmd.controller.command.Clear;
import ua.kh.lual.sqlcmd.controller.command.Tables;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TablesTest extends ABasicCommandTestClass {

    @Before
    public void setup() {
        setupMocks();
        cmd = new Tables();
    }

    @Test
    public void testTables() {
        // given
        when(dbManager.getTableNames()).thenReturn(new String[]{"users", "pets", "cars"});
        // when
        cmd.process("tables");
        // then
        verify(view).write("[users, pets, cars]");
    }

}

