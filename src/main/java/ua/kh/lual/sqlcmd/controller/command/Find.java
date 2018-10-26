package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.utils.MyUtils;
import ua.kh.lual.sqlcmd.utils.TextTable;

public class Find extends UserCommandClass {

    @Override
    public String format() {
        return "find|table_name";
    }

    @Override
    public String description() {
        return  "Prints content of selected table";
    }

    @Override
    public void process(String command) {
        String[] parameters = extractParameters(command);
        dbManager.selectTable(parameters[0]);
        view.write(new TextTable(dbManager.getColumnNames(),
                                 dbManager.getTableData(),
                                 2).toString());
    }
}
