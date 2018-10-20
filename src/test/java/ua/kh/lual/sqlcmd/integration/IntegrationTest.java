package ua.kh.lual.sqlcmd.integration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.kh.lual.sqlcmd.controller.Main;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static PreparedInputStream in;
    private static LogOutputStream out;

    @BeforeClass
    public static void setup() {
        in = new PreparedInputStream();
        out = new LogOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }


    static final String databaseName = "postgres";
    static final String userName = "postgres";
    static final String password = "12345";

    @Test
    public void testExit() {
        in.add("something");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("", out.getLog());
    }



}
