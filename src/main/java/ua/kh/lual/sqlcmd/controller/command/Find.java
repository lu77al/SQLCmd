package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.MyUtils;

public class Find extends UserCommandClass {

    @Override
    public String format() {
        return "find|[table_name]";
    }

    @Override
    public String description() {
        return  "Prints content of selected table";
    }

    @Override
    public void process(String command) {
        String[] parameters = extractParameters(command);
        if (parameters == null) return;
        dbManager.selectTable(parameters[0]);
        String[] columnNames = dbManager.getColumnNames();
        view.write(MyUtils.rowToString(columnNames));
        view.write("---------------------------");
        Object[][] tableData = dbManager.getTableData();
        for (Object[] row: tableData) {
            view.write(MyUtils.rowToString(row));
        }
    }
}
