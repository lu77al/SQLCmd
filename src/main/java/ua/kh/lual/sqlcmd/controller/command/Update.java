package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DataSet;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;
import ua.kh.lual.sqlcmd.utils.TextTable;

import java.util.Arrays;

public class Update extends UserCommandClass {
    @Override
    public String format() {
        return "update|tableName|destColumn|destValue|keyColumn|keyValue";
    }

    @Override
    public String description() {
        return "Updates value of specified cells in the table <tableName>" +
                "\t<destColumn> - name of column to update\n" +
                "\t<destValue>  - new value for column to update\n" +
                "\t<keyColumn>  - name of column to check before update\n" +
                "\t<keyValue>   - update occurs if keyValue equals actual value of keyColumn\n" +
                "\t  * data in several rows could be updated";
    }

    @Override
    protected void execute(String[] parameters) {
        try {
            dbManager.selectTable(parameters[0]);
            DataSet whereRecord = new DataSet();
            whereRecord.put(parameters[3], parameters[4]);
            Object[][] updatePreviousState = dbManager.getFilteredContent(whereRecord);
            if (updatePreviousState.length == 0) {
                view.write("Nothing matches key. No update performed");
                return;
            }
            view.write(new TextTable(dbManager.getTableHeader(), updatePreviousState, 2).toString());
            DataSet updateRecord = new DataSet();
            updateRecord.put(parameters[1], parameters[2]);
            dbManager.update(updateRecord, whereRecord);
            view.write("Rows above where updated");
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }

    }
}
