package ua.kh.lual.sqlcmd.controller.command;

public class Help extends UserCommand {

    UserCommand[] commandList;

    @Override
    public String format() {
        return "help";
    }

    @Override
    public String description() {
        return "Prints this brief commands summary";
    }

    public void setCommandList(UserCommand[] commandList) {
        this.commandList = commandList;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("You can use next commands:");
        for (UserCommand cmd: commandList) {
            if (cmd == null) break;
            view.write("\t" + cmd.format());
            String[] descriptions = cmd.description().split("!NL");
            for (String description: descriptions) {
                view.write("\t\t" + description);
            }
        }
    }
}
