package ua.kh.lual.sqlcmd.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.kh.lual.sqlcmd.NamesAndPasswords;
import ua.kh.lual.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static PreparedInputStream in;
    private static ByteArrayOutputStream out;

    private static final String database = NamesAndPasswords.database;
    private static final String user = NamesAndPasswords.user;
    private static final String password = NamesAndPasswords.password;
    private static final String table = NamesAndPasswords.table;

    private String expected;

    private static String hello =
            "Hello. Your are using SQLcmd application\n\n" +
            "Enter command (help for commands list)\n";

    private static String goodBye =
            "\n\nEnter command (help for commands list)\n" +
            "exit\n" +
            "Bye\n" +
            "See you later ;)\n";

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
        expected = "";
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
            "something\n" +
            "Unknown command: something";
        // execute and check
        performTest();
    }

    @Test
    public void testTooManyParameters() {
        // given
        in.userTypes("exit|system");
        in.userTypes("exit");
        // after
        expected =
                "exit|system\n" +
                "Command failed\n" +
                "Too many parameters\n" +
                "Please use format: exit";
        // execute and check
        performTest();
    }

    @Test
    public void testNotEnoughParameters() {
        // given
        in.userTypes("connect|system");
        in.userTypes("exit");
        // after
        expected =
                "connect|system\n" +
                "Command failed\n" +
                "Not enough parameters\n" +
                "Please use format: connect|database|user|password";
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
        String actual = getLog().replaceAll(System.lineSeparator(), "\n");
        if (expected.length() != 0) {
            assertEquals(hello + expected + goodBye, actual);
        } else {
            assertEquals("Hello. Your are using SQLcmd application" + goodBye, actual);
        }
    }

    private String getLog() {
        try {
            return new String(out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

}
