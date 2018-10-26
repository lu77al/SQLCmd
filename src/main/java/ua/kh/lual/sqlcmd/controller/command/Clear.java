package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;
import ua.kh.lual.sqlcmd.utils.TextTable;

public class Clear extends UserCommandClass {
    @Override
    public String format() {
        return "clear|table_name";
    }

    @Override
    public String description() {
        return  "Clears table <table_name>";
    }

    @Override
    public void process(String command) throws CommandFailedException {
        String[] parameters = extractParameters(command);
        try {
            dbManager.selectTable(parameters[0]);
            dbManager.clearTable();
            view.write("Table <" + parameters[0] + "> was cleared");
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }

    }
}
