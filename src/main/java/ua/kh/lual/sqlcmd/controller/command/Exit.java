package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.controller.exceptions.ExitException;

public class Exit extends UserCommandClass {

    @Override
    public String format() {
        return "exit";
    }

    @Override
    public String description() {
        return "Terminates application";
    }

    @Override
    protected void execute(String[] parameters) {
        view.write("Bye");
        view.write("See you later ;)");
        throw new ExitException();
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }

}
