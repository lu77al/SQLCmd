package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DataSet;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;
import ua.kh.lual.sqlcmd.utils.TextTable;

public class Insert extends UserCommandClass {
    @Override
    public String format() {
        return "insert|tableName|column1|value1|column2|value2| ... |columnN |valueN";
    }

    @Override
    public String description() {
        return "Inserts one new row in the table <tableName>\n" +
                "\t<column1> - name of first column to insert\n" +
                "\t<value1>  - value to insert into first column\n" +
                "\t<column2> - name of second column to insert\n" +
                "\t<value2>  - value to insert into second column\n" +
                "\t<columnN> - name of N's column to insert\n" +
                "\t<valueN>  - value to insert into N's column\n";
    }

    @Override
    public void process(String command) throws CommandFailedException {
        String[] parameters = extractParameters(command);
        try {
            dbManager.selectTable(parameters[0]);
            DataSet insert = new DataSet();
            for (int i = 1; i < parameters.length; i += 2) {
                insert.put(parameters[i], parameters[i + 1]);
            }
            dbManager.insert(insert);
            view.write("New data added successfully");
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }
    }

    @Override
    protected void checkParametersCount(int actualCount) {
        if (actualCount < 3) {
            throw new CommandFailedException("Not enough parameters\nPlease use format: <" + format() + ">");
        }
        if (actualCount % 2 == 0) {
            throw new CommandFailedException("Unpaired parameters column/value\nPlease use format: <" + format() + ">");
        }
    }
}
