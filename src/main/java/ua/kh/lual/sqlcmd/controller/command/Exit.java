package ua.kh.lual.sqlcmd.controller.command;

public class Exit extends UserCommand {

    @Override
    public String format() {
        return "exit";
    }

    @Override
    public String description() {
        return "Terminates application";
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write("Bye");
        view.write("See you later ;)");
        System.exit(0);
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }

}
