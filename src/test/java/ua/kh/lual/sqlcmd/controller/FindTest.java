package ua.kh.lual.sqlcmd.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import ua.kh.lual.sqlcmd.controller.command.Find;
import ua.kh.lual.sqlcmd.controller.command.UserCommand;
import ua.kh.lual.sqlcmd.controller.command.UserCommandClass;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

import static org.junit.Assert.assertEquals;

public class FindTest {

    private DatabaseManager dbManager;
    private View view;

    @Before
    public void setup() {
        dbManager = mock(DatabaseManager.class);
        view = mock(View.class);
        UserCommandClass.setDbManager(dbManager);
        UserCommandClass.setView(view);
    }

    @Test
    public void test() {
        // given
        UserCommand cmd = new Find();
        when(dbManager.isConnected()).thenReturn(true);
        when(dbManager.getTableHeader("user")).thenReturn(new String[]{"id", "name", "password"});
        when(dbManager.getAllContent("user")).thenReturn(new Object[][]{{"1", "Vasya", "sobaka"},
                                                                                          {"2", "Manya", "12345"}});
        // when
        cmd.process("find|user");
        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        String expected =
                "+------+---------+------------+\n" +
                "+  id  +  name   +  password  +\n" +
                "+------+---------+------------+\n" +
                "+  1   +  Vasya  +   sobaka   +\n" +
                "+  2   +  Manya  +   12345    +\n" +
                "+------+---------+------------+";
        assertEquals(expected, captor.getValue().toString());
    }
}
