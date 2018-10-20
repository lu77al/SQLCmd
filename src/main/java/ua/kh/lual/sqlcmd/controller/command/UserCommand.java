package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

public abstract class UserCommand {
    public abstract String format();
    public abstract String description();
    public abstract boolean canProcess(String command);
    public abstract void process(String command);

    static View view;
    static DatabaseManager dbManager;

    public static void setView(View view) {
        UserCommand.view = view;
    }
    public static void setDbManager(DatabaseManager dbManager) {
        UserCommand.dbManager = dbManager;
    }
}
