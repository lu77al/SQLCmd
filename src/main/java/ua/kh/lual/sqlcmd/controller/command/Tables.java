package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;

import java.util.Arrays;

public class Tables extends UserCommandClass {

    @Override
    public String format() {
        return "tables";
    }

    @Override
    public String description() {
        return "Prints tables names of connected database";
    }

    @Override
    protected void execute(String[] parameters) {
        try {
            String[] tableNames = dbManager.getTableNames();
            String message = Arrays.toString(tableNames);
            view.write(message);
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }
    }
}
