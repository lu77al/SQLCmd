package ua.kh.lual.sqlcmd.controller;

import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.model.JDBCManager;
import ua.kh.lual.sqlcmd.view.Console;
import ua.kh.lual.sqlcmd.view.View;

import java.util.Arrays;

public class Controller {

    private DatabaseManager dbManager;
    private View view;

    public Controller(View view, DatabaseManager dbManager) {
        this.dbManager = dbManager;
        this.view = view;
    }

    public void run() {
        connectDataBase();

        while (true) {
            view.write("");
            view.write("Enter command (<help> for list of commands)");
            String command = view.read();
            if (command.equals("list")) {
                doList();
            } else if (command.equals("help")) {
                doHelp();
            } else if (command.startsWith("select|")) {
                doSelect(command);
            } else if (command.equals("find") | command.startsWith("find|")) {
                doFind(command);
            } else if (command.equals("exit")) {
                view.write("See you later");
                System.exit(0);
            } else {
                view.write("Unknown command");
            }
        }
    }

    private void doFind(String command) {
        String[] chunks = command.split("\\|");
        if (chunks.length > 1) {
            String tableName = chunks[1];
            selectTable(tableName);
        }
        String[] columnNames = dbManager.getColumnNames();
        view.write(rowToString(columnNames));
        view.write("---------------------------");
        Object[][] tableData = dbManager.getTableData();
        for (Object[] row: tableData) {
            view.write(rowToString(row));
        }
    }

    private String rowToString(Object[] items) {
        StringBuilder result = new StringBuilder("| ");
        for (Object item: items) {
            result.append(item);
            result.append(" |\t");
        }
        return result.toString();
    }


    private void doSelect(String command) {
        String[] chunks = command.split("\\|");
        String tableName = chunks[1];
        selectTable(tableName);
    }

    private void selectTable(String tableName) {
        dbManager.selectTable(tableName);
        view.write(String.format("Table <%s> is selected", tableName));
    }

    private void doHelp() {
        view.write("You can use next commands:");

        view.write("\tlist");
        view.write("\t\t- get tables names of connected database");

        view.write("\tselect|table_name");
        view.write("\t\t- select table for consequent actions");

        view.write("\tfind|[table_name]");
        view.write("\t\t- select table and show it's contents");
        view.write("\t\t- if <table_name> is omitted, content of already selected table is shown");

        view.write("\texit");
        view.write("\t\t- exit application");

        view.write("\thelp");
        view.write("\t\t- show this list in console");
    }

    private void doList() {
        String[] tableNames = dbManager.getTableNames();
        String message = Arrays.toString(tableNames);
        view.write(message);
    }

    private void connectDataBase() {
        view.write("Hello. Your are using SQLcmd application");
        while (true) {
            view.write("Enter database name, user name and password (format: database|user|password)");
            try {
                String userInput = view.read();
                String[] chunk = userInput.split("\\|");
                if (chunk.length != 3) {
                    throw new IllegalArgumentException("Wrong parameters quantity");
                }
                String database = chunk[0];
                String user = chunk[1];
                String password = chunk[2];
                dbManager.connect(database, user, password);
                view.write(String.format("User <%s> successfully connected to database <%s>", user, database));
                break;
            } catch (Exception e) {
                view.write("Connection failed");
                view.write("Reason: " +e.getMessage());
                if (e.getCause() != null) {
                    view.write("       " + e.getCause().getMessage());
                }
                view.write("Try again");
                view.write("");
            }
        }
    }
}
