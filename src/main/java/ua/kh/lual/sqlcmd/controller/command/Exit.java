package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.ExitException;

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
    public void process(String command) {
        String[] parameters = extractParameters(command);
        if (parameters == null) return;
        view.write("Bye");
        view.write("See you later ;)");
        throw new ExitException();
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }

}
