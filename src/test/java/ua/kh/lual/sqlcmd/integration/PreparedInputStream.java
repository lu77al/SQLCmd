package ua.kh.lual.sqlcmd.integration;

import java.io.IOException;
import java.io.InputStream;

public class PreparedInputStream extends InputStream {

    private String line;

    @Override
    public int read() throws IOException {
        if (line.length() == 0) return -1;
        char ch = line.charAt(0);
        line = line.substring(1);
        return ch;
    }
}
