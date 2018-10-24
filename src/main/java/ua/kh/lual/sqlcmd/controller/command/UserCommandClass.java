package ua.kh.lual.sqlcmd.controller.command;

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
    public abstract void process(String command);

    @Override
    public boolean canProcess(String command) {
        int delimiterIndex = format().indexOf('|');
        if (delimiterIndex == -1) {
            return format().equals(command);
        } else {
            return command.startsWith(format().substring(0,delimiterIndex));
        }
    }

    protected String[] extractParameters(String command) {
        int chunksExpected = format().split("\\|").length;
        String[] result = command.split("\\|");
        if (result.length == chunksExpected) {
            return Arrays.copyOfRange(result,1, result.length);
        } else {
            if (result.length < chunksExpected) {
                view.write("Not enough parameters");
            } else {
                view.write("Too many parameters");
            }
            view.write("Please use format: <" + format() + ">");
            return null;
        }
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
