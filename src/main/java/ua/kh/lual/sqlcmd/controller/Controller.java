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
            } else {
                view.write("Unknown command");
            }
        }
    }

    private void doHelp() {
        view.write("You can use next commands:");
        view.write("\tlist");
        view.write("\t\tget tables names of connected database");
        view.write("\thelp");
        view.write("\t\tshow this list in console");
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
                view.write(String.format("User [%s] successfully connected to database [%s]", user, database));
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
