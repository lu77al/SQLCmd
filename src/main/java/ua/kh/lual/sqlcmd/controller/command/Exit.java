package ua.kh.lual.sqlcmd.controller.command;

public class Exit extends UserCommand {

    public Exit() {
        format = "exit";
        description = "Terminates application";
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
}
