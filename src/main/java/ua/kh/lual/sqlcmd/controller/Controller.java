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
                help,
                new Exit(),
                new List(),
                new Select(),
                new Find(),
        };
        help.setCommandList(commands);
    }

    public void run() {
        view.write("Hello. Your are using SQLcmd application");
        while (true) {
            view.write("");
            view.write("Enter command (<help> for commands list)");
            String userInput = view.read();
            executeUserCommand(userInput);
        }
    }

    private void executeUserCommand(String userInput) {
        for (int commandIndex = 0; commandIndex < commands.length; commandIndex++) {
            UserCommand command = commands[commandIndex];
            if (command.canProcess(userInput)) {
                if ((commandIndex <= 2) | dbManager.connected()) {
                    command.process(userInput);
                } else {
                    view.write("Please connect to database before using command " + command);
                    view.write("\tUse command " + new Connect().format());
                }
                return;
            }
        }
        view.write("Unknown command: " + userInput);
    }
}
