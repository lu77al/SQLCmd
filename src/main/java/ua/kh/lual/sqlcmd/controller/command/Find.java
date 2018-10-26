package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;
import ua.kh.lual.sqlcmd.utils.TextTable;

public class Find extends UserCommandClass {

    @Override
    public String format() {
        return "find|tableName";
    }

    @Override
    public String description() {
        return  "Prints content of the table <tableName>";
    }

    @Override
    public void process(String command) {
        String[] parameters = extractParameters(command);
        try {
            dbManager.selectTable(parameters[0]);
            view.write(new TextTable(dbManager.getTableHeader(),
                    dbManager.getAllContent(),
                    2).toString());
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }
    }
}
