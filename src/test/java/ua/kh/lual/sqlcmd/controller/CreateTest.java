package ua.kh.lual.sqlcmd.controller;

import org.junit.Before;
import org.junit.Test;
import ua.kh.lual.sqlcmd.controller.command.Clear;
import ua.kh.lual.sqlcmd.controller.command.Create;

import static org.mockito.Mockito.verify;

public class CreateTest extends ABasicCommandTestClass {

    @Before
    public void setup() {
        setupMocks();
        cmd = new Create();
    }

    @Test
    public void testClear() {
        // when
        cmd.process("create|user|id|name|password");
        // then
        verify(dbManager).createTable("user", new String[]{"id", "name", "password"});
        verify(view).write("Table <user> was created successfully");
    }

}
