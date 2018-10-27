package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DataSet;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;

import java.util.Arrays;

public class Create extends UserCommandClass {
    @Override
    public String format() {
        return "create|tableName|column1|column2|...|columnN";
    }

    @Override
    public String description() {
        return "creates table <tableName> with specified columns";
    }

    @Override
    protected void execute(String[] parameters) {
        try {
            String table = parameters[0];
            String[] columns = Arrays.copyOfRange(parameters, 1, parameters.length);
            dbManager.createTable(table, columns);
            view.write("Table <" + table + "> was created successfully");
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }
    }

    @Override
    protected void checkParametersCount(int actualCount) {
        if (actualCount < 2) {
            throw new CommandFailedException("Not enough parameters\nPlease use format: <" + format() + ">");
        }
    }

}
