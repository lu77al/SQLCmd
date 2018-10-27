package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;
import ua.kh.lual.sqlcmd.model.JDBCManagerException;

public class Connect extends UserCommandClass {
    @Override
    public String format() {
        return "connect|database|user|password";
    }

    @Override
    public String description() {
        return "Connects to database <database> as user <user> with password <password>";
    }

    @Override
    protected void execute(String[] parameters) {
        try {
            String database = parameters[0];
            String user = parameters[1];
            String password = parameters[2];
            dbManager.connect(database, user, password);
            view.write(String.format("User <%s> successfully connected to database <%s>", user, database));
        } catch (JDBCManagerException e) {
            throw new CommandFailedException("JDBCManager error: " + e.getMessage());
        }
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }

}
