package ua.kh.lual.sqlcmd.controller;

import ua.kh.lual.sqlcmd.controller.command.*;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

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
                new Connect(),
                new List(),
                new Select(),
                new Find(),
                new Exit(),
                help
        };
        help.setCommandList(commands);
    }

    public void run() {
        view.write("Hello. Your are using SQLcmd application");
        try {
            mainLoop();
        } catch (Exception e) {
            // Nothing
        }
    }

    private void mainLoop() {
        while (true) {
            view.write("");
            view.write("Enter command (<help> for commands list)");
            String userInput = view.read();
            if (userInput.length() > 0) {
                executeUserCommand(userInput);
            }
        }
    }

    private void executeUserCommand(String userInput) {
        for (UserCommand command : commands) {
            if (command.canProcess(userInput)) {
                if (command.requestsConnection()) {
                    view.write("Please connect to database before using command " + command);
                    view.write("\tUse command <" + new Connect().format());
                } else {
                    command.process(userInput);
                }
                return;
            }
        }
        view.write("Unknown command: " + userInput);
    }
}
