package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;

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
    public void process(String command) {
        String[] parameters = extractParameters(command);
        try {
            String database = parameters[0];
            String user = parameters[1];
            String password = parameters[2];
            dbManager.connect(database, user, password);
            view.write(String.format("User <%s> successfully connected to database <%s>", user, database));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (e.getCause() != null) {
                errorMessage +=  System.lineSeparator() + "\t" + e.getCause().getMessage();
            }
            throw new CommandFailedException(errorMessage);
        }
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }

}
