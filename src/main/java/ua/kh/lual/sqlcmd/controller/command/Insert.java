package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DataSet;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;
import ua.kh.lual.sqlcmd.utils.TextTable;

import java.util.LinkedHashMap;
import java.util.Map;

public class Insert extends UserCommandClass {
    @Override
    public String format() {
        return "insert|tableName|column1|value1|column2|value2| ... |columnN |valueN";
    }

    @Override
    public String description() {
        return "Inserts one new row in the table <tableName>\n" +
                "\tcolumn1 - name of first column to insert\n" +
                "\tvalue1  - value to insert into first column\n" +
                "\tcolumn2 - name of second column to insert\n" +
                "\tvalue2  - value to insert into second column\n" +
                "\tcolumnN - name of N's column to insert\n" +
                "\tvalueN  - value to insert into N's column\n";
    }

    @Override
    protected void execute(String[] parameters) {
        try {
            String table = parameters[0];
            Map<String, Object> insert = new LinkedHashMap<>();
            for (int i = 1; i < parameters.length; i += 2) {
                insert.put(parameters[i], parameters[i + 1]);
            }
            dbManager.insert(table, insert);
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
