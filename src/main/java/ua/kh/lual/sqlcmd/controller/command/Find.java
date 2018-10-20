package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.MyUtils;

public class Find extends UserCommand {

    @Override
    public String format() {
        return "find|[table_name]";
    }

    @Override
    public String description() {
        return  "Prints content of selected table" + "!NL" +
                "if <table_name> is specified, it becomes selected at first";
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("find") | command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] chunks = command.split("\\|");
        if (chunks.length > 1) {
            String tableName = chunks[1];
            new Select().process("select|" + tableName);
        }
        String[] columnNames = dbManager.getColumnNames();
        view.write(MyUtils.rowToString(columnNames));
        view.write("---------------------------");
        Object[][] tableData = dbManager.getTableData();
        for (Object[] row: tableData) {
            view.write(MyUtils.rowToString(row));
        }
    }
}
