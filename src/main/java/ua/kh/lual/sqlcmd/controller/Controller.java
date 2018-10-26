package ua.kh.lual.sqlcmd.controller;

import ua.kh.lual.sqlcmd.controller.command.*;
import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

public class Controller {

    private View view;

    private UserCommand[] commands;

    public Controller(View view, DatabaseManager dbManager) {
        this.view = view;

        UserCommandClass.setDbManager(dbManager);
        UserCommandClass.setView(view);

        Help help = new Help();
        this.commands = new UserCommand[]{
                new Connect(),
                new Tables(),
                new Find(),
                new Clear(),
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
            if (e.getMessage() != null) {
                view.write(e.getMessage());
            }
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
                try {
                    command.process(userInput);
                } catch (CommandFailedException e) {
                    view.write("Command failed");
                    view.write(e.getMessage());
                }
                return;
            }
        }
        view.write("Unknown command: " + userInput);
    }

}