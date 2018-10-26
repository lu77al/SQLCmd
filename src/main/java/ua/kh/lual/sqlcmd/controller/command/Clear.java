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
    public void process(String command) throws CommandFailedException {
        String[] parameters = extractParameters(command);
        try {
            dbManager.selectTable(parameters[0]);
            dbManager.dropTable();
            view.write("Table <" + parameters[0] + "> was cleared");
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }

    }
}
