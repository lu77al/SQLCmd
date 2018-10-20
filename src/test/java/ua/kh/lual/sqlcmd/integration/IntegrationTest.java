package ua.kh.lual.sqlcmd.integration;

import org.junit.BeforeClass;
import org.junit.Test;

public class IntegrationTest {

    private static PreparedInputStream in;
    private static LogOutputStream out;

    @BeforeClass
    public void setup() {
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    public void testExit() {
    }
}
