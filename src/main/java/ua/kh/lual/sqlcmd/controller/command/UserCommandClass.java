package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

import java.util.Arrays;

public abstract class UserCommandClass implements UserCommand{
    static View view;
    static DatabaseManager dbManager;

    @Override
    public abstract String format();
    @Override
    public abstract String description();
    @Override
    public abstract void process(String command) throws CommandFailedException;

    @Override
    public boolean canProcess(String command) {
        String[] estimated = format().split("\\|");
        String[] entered = command.split("\\|");
        if (entered.length == 0) {
            return false;
        }
        return estimated[0].equals(entered[0]);
    }

    protected String[] extractParameters(String command) {
        if (requestsConnection()) {
            throw new CommandFailedException("Please connect to database before using command " + command +
                                             "\n\tUse command <" + new Connect().format());
        }
        String[] result = command.split("\\|");
        checkParametersCount(result.length - 1);
        return Arrays.copyOfRange(result, 1, result.length);
    }

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

    @Override
    public boolean requestsConnection() {
        return !dbManager.isConnected();
    }

    public static void setView(View view) {
        UserCommandClass.view = view;
    }

    public static void setDbManager(DatabaseManager dbManager) {
        UserCommandClass.dbManager = dbManager;
    }
}
