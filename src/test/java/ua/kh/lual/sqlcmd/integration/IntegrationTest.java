package ua.kh.lual.sqlcmd.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.kh.lual.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static PreparedInputStream in;
    private static ByteArrayOutputStream out;

    private static final String database = "sqlcmd";
    private static final String user = "postgres";
    private static final String password = "12345";

    private String expected;

    @BeforeClass
    public static void setup() {
        in = new PreparedInputStream();
        System.setIn(in);
        ua.kh.lual.sqlcmd.view.Console.suppressFormating();
    }

    @Before
    public void clearOutput() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testExit() {
        // given
        in.userTypes("exit");
        // after
        expected =
            "Hello. Your are using SQLcmd application\n" +
            "\n" +
            "Enter command (help for commands list)\n" +
            "exit\n" +
            "Bye\n" +
            "See you later ;)\n";
        // execute and check
        performTest();
    }

    @Test
    public void testUnknown() {
        // given
        in.userTypes("something");
        in.userTypes("exit");
        // after
        expected =
            "Hello. Your are using SQLcmd application\n" +
            "\n" +
            "Enter command (help for commands list)\n" +
            "something\n" +
            "Unknown command: something\n" +
            "\n" +
            "Enter command (help for commands list)\n" +
            "exit\n" +
            "Bye\n" +
            "See you later ;)\n";
        // execute and check
        performTest();
    }

    @Test
    public void testConnect() {
        // given
        in.userTypes("connect|" + database + "|" + user + "|" + password);
        in.userTypes("exit");
        // after
        expected =
            "Hello. Your are using SQLcmd application\n" +
            "\n" +
            "Enter command (help for commands list)\n" +
            "connect|" + database + "|" + user + "|" + password + "\n" +
            "User " + user + " successfully connected to database " + database +"\n" +
            "\n" +
            "Enter command (help for commands list)\n" +
            "exit\n" +
            "Bye\n" +
            "See you later ;)\n";
        // execute and check
        performTest();
    }

    private void performTest() {
        Main.main(new String[0]);
        String actual = getLog().replaceAll("\r\n", "\n").replaceAll("\r", "\n");
        assertEquals(expected, actual);
    }

    private String getLog() {
        try {
            return new String(out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

}
