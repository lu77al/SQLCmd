package ua.kh.lual.sqlcmd.controller.command;

public class Select extends UserCommand {

    public Select() {
        format = "select|table_name";
        description = "Selects table <table_name> for consequent manipulations";
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("select|");
    }

    @Override
    public void process(String command) {
        String[] chunks = command.split("\\|");
        String tableName = chunks[1];
        dbManager.selectTable(tableName);
        view.write(String.format("Table <%s> is selected", tableName));
    }
}
