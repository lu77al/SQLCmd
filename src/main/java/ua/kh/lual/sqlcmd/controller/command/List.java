package ua.kh.lual.sqlcmd.controller.command;

import java.util.Arrays;

public class List extends UserCommandClass {

    @Override
    public String format() {
        return "list";
    }

    @Override
    public String description() {
        return "Prints table names of isConnected database";
    }

    @Override
    public void process(String command) {
        String[] parameters = extractParameters(command);
        if (parameters == null) return;
        String[] tableNames = dbManager.getTableNames();
        String message = Arrays.toString(tableNames);
        view.write(message);
    }
}
