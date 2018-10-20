package ua.kh.lual.sqlcmd.controller.command;

import java.util.Arrays;

public class List extends UserCommand {

    @Override
    public String format() {
        return "list";
    }

    @Override
    public String description() {
        return "Prints table names of connected database";
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        String[] tableNames = dbManager.getTableNames();
        String message = Arrays.toString(tableNames);
        view.write(message);
    }
}
