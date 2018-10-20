package ua.kh.lual.sqlcmd.integration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;

public class PreparedInputStream extends InputStream {
    private String line;
    private StringBuilder output = new StringBuilder();

    @Override
    public int read() throws IOException {
        int result;
        if (line.length() == 0) {
            result = -1;
        } else {
            char ch = line.charAt(0);
            line = line.substring(1);
            result = (short)ch;
        }
        if (result == (short)'\n') {
            System.out.println(output.toString());
            output.setLength(0);
        } else if (result != - 1) {
            output.append((char)result);
        }
        return result;
    }

    public void add(String line) {
        if (this.line == null) {
            this.line = line + "\n\uffff";
            output.setLength(0);
        } else {
            this.line += line + "\n\uffff";
        }
    }
}
