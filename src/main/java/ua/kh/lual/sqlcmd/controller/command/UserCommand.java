package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

public abstract class UserCommand {
    static View view;
    static DatabaseManager dbManager;

    String description;
    String format;

    public static void setView(View view) {
        UserCommand.view = view;
    }

    public static void setDbManager(DatabaseManager dbManager) {
        UserCommand.dbManager = dbManager;
    }

    public String getDescription() {
        return description;
    }

    public String getFormat() {
        return format;
    }

    public abstract boolean canProcess(String command);
    public abstract void process(String command);
}
