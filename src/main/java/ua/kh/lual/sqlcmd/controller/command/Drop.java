package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;

public class Drop extends UserCommandClass{

    @Override
    public String format() {
        return "drop|tableName";
    }

    @Override
    public String description() {
        return "Deletes table <tableName> from database";
    }

    @Override
    protected void execute(String[] parameters) {
        try {
            String table = parameters[0];
            dbManager.dropTable(table);
            view.write("Table <" + table + "> was deleted");
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }

    }
}
