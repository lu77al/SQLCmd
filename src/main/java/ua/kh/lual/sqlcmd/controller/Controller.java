package ua.kh.lual.sqlcmd.controller;

import ua.kh.lual.sqlcmd.controller.command.*;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.model.JDBCManager;
import ua.kh.lual.sqlcmd.view.Console;
import ua.kh.lual.sqlcmd.view.View;

import java.util.Arrays;

public class Controller {

    private DatabaseManager dbManager;
    private View view;

    UserCommand[] commands;

    public Controller(View view, DatabaseManager dbManager) {
        this.dbManager = dbManager;
        this.view = view;

        Help.setDbManager(dbManager);
        Help.setView(view);

        Help help = new Help();
        this.commands = new UserCommand[]{
                new Exit(),
                new List(),
                new Select(),
                new Find(),
                help,
                null
        };
        help.setCommandList(commands);
    }

    public void run() {
        connectDataBase();

        while (true) {
            view.write("");
            view.write("Enter command (<help> for list of command)");
            String userInput = view.read();
            for (UserCommand command: commands) {
                if (command == null) {
                    view.write("Unknown command: " + userInput);
                    break;
                }
                if (command.canProcess(userInput)) {
                    command.process(userInput);
                    break;
                }
            }
        }
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
