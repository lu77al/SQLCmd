package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class UserCommandClass implements UserCommand{
    static View view;
    static DatabaseManager dbManager;

    public static void setView(View view) {
        UserCommandClass.view = view;
    }

    public static void setDbManager(DatabaseManager dbManager) {
        UserCommandClass.dbManager = dbManager;
    }

    @Override
    public abstract String format();

    @Override
    public abstract String description();

    @Override
    public void process(String command) {
        if (requestsConnection()) {
            throw new CommandFailedException("Please connect to database before using command " + command +
                    "\n\tUse command <" + new Connect().format());
        }
        List<String> chunks = new ArrayList<>(Arrays.asList(command.split("\\|")));
        checkParametersCount(chunks.size() - 1);
        execute(chunks.subList(1, chunks.size()).toArray(new String[0]));
//        execute(Arrays.copyOfRange(result, 1, result.length));
    };

    @Override
    public boolean canProcess(String command) {
        String[] estimated = format().split("\\|");
        String[] entered = command.split("\\|");
        if (entered.length == 0) {
            return false;
        }
        return estimated[0].equals(entered[0]);
    }

    protected abstract void execute(String[] parameters);

    protected void checkParametersCount(int actualCount) {
        int expectedCount = format().split("\\|").length - 1;
        if (expectedCount == actualCount) return;
        String errorMessage;
        if (actualCount < expectedCount) {
            errorMessage = "Not enough";
        } else {
            errorMessage = "Too many";
        }
        errorMessage += " parameters\nPlease use format: <" + format() + ">";
        throw new CommandFailedException(errorMessage);
    }

    protected boolean requestsConnection() {
        return !dbManager.isConnected();
    }
}
