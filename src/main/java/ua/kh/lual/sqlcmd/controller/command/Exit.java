package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.ExitException;

public class Exit extends UserCommand {

    @Override
    public String format() {
        return "exit";
    }

    @Override
    public String description() {
        return "Terminates application";
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write("Bye");
        view.write("See you later ;)");
        throw new ExitException();
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }

}
