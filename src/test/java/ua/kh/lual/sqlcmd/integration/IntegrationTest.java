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

    static final String database = "sqlcmd";
    static final String user = "postgres";
    static final String password = "12345";

    @Before
    public void setup() {
        in = new PreparedInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        ua.kh.lual.sqlcmd.view.Console.suppressFormating();
    }

    @Test
    public void testExit() {
        in.add("exit");
        String expected =
            "Hello. Your are using SQLcmd application\n" +
            "\n" +
            "Enter command (help for commands list)\n" +
            "exit\n" +
            "Bye\n" +
            "See you later ;)\n";
        performTest(expected);
    }

    @Test
    public void testUnknown() {
        in.add("something");
        in.add("exit");
        String expected =
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
        performTest(expected);
    }

    @Test
    public void testConnect() {
        in.add("connect|" + database + "|" + user + "|" + password);
        in.add("exit");
        String expected =
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
        performTest(expected);
    }

    private void performTest(String expected) {
        Main.main(new String[0]);
        String actual = getLog().replaceAll("\r", "");
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
