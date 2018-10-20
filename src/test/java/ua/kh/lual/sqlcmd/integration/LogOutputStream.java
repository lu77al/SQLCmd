package ua.kh.lual.sqlcmd.integration;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class LogOutputStream extends OutputStream {

    private static StringBuilder log = new StringBuilder();

    @Override
    public void write(int b) throws IOException {
        log.append((char)b);

    }

    public String getLog() {
        return log.toString();
    }
}
