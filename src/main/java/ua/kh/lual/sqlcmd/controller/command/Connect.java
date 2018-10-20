package ua.kh.lual.sqlcmd.controller.command;

public class Connect extends UserCommand {
    @Override
    public String format() {
        return "connect|database|user|password";
    }

    @Override
    public String description() {
        return "Connects to database <database> as user <user> with password <password>";
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
        try {
            String[] chunk = command.split("\\|");
            if (chunk.length != 4) {
                throw new IllegalArgumentException("Wrong parameters quantity");
            }
            String database = chunk[1];
            String user = chunk[2];
            String password = chunk[3];
            dbManager.connect(database, user, password);
            view.write(String.format("User <%s> successfully isConnected to database <%s>", user, database));
        } catch (Exception e) {
            view.write("Connection failed");
            view.write("Reason: " +e.getMessage());
            if (e.getCause() != null) {
                view.write("       " + e.getCause().getMessage());
            }
        }
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }

}
