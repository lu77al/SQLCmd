package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;

public class Clear extends UserCommandClass {
    @Override
    public String format() {
        return "clear|tableName";
    }

    @Override
    public String description() {
        return  "Clears table <tableName>";
    }

    @Override
    protected void execute(String[] parameters) {
        try {
            dbManager.selectTable(parameters[0]);
            dbManager.clearTable();
            view.write("Table <" + parameters[0] + "> was cleared");
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }

    }
}
