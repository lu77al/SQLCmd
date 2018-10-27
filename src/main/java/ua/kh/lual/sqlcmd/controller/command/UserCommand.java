package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.view.View;

public interface UserCommand {

    String format();

    String description();

    boolean canProcess(String command);

    void process(String command) throws CommandFailedException;

}
