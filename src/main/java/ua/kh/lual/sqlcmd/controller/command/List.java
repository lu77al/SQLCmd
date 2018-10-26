package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;

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
        extractParameters(command);
        String[] tableNames = dbManager.getTableNames();
        String message = Arrays.toString(tableNames);
        view.write(message);
    }
}
