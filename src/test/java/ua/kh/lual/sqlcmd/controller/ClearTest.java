package ua.kh.lual.sqlcmd.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.kh.lual.sqlcmd.controller.command.Clear;
import ua.kh.lual.sqlcmd.controller.command.Find;
import ua.kh.lual.sqlcmd.controller.command.UserCommand;
import ua.kh.lual.sqlcmd.controller.command.UserCommandClass;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ClearTest {

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
    public void process() {
        // given
        UserCommand cmd = new Clear();
        when(dbManager.isConnected()).thenReturn(true);
        // when
        cmd.process("clear|user");
        // then
        verify(dbManager).clearTable("user");
        verify(view).write("Table <user> was cleared");
    }
}

