package ua.kh.lual.sqlcmd.controller;

import org.junit.Before;
import org.junit.Test;
import ua.kh.lual.sqlcmd.controller.command.Exit;
import ua.kh.lual.sqlcmd.controller.exceptions.ExitException;
import ua.kh.lual.sqlcmd.model.DataSet;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ExitTest extends ABasicCommandTestClass {

    @Before
    public void setup() {
        setupMocks();
        cmd = new Exit();
    }

    @Test
    public void testExit() {
        // given
        // when
        try {
            cmd.process("exit");
            fail("ExitException was expected but wasn't thrown");
        } catch (ExitException e) {
            // do nothing
        }
        // then
        assertOutput( "" +
                "Bye\n" +
                "See you later ;)\n");
    }

}
