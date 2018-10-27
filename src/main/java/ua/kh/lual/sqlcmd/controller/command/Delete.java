package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DataSet;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;
import ua.kh.lual.sqlcmd.utils.TextTable;

public class Delete extends UserCommandClass{
    @Override
    public String format() {
        return "delete|tableName|column|value";
    }

    @Override
    public String description() {
        return "Deletes rows from table <tableName> in which column <column> has value <value>";
    }

    @Override
    protected void execute(String[] parameters) {
        try {
            String table = parameters[0];
            DataSet whereRecord = new DataSet();
            whereRecord.put(parameters[1], parameters[2]);
            Object[][] updatePreviousState = dbManager.getFilteredContent(table, whereRecord);
            if (updatePreviousState.length == 0) {
                view.write("Nothing matches key field. No delete performed");
                return;
            }
            view.write(new TextTable(dbManager.getTableHeader(table), updatePreviousState, 2).toString());
            dbManager.delete(table, whereRecord);
            view.write("Rows above where deleted");
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }

    }
}