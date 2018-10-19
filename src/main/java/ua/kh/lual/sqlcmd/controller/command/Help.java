package ua.kh.lual.sqlcmd.controller.command;

public class Help extends UserCommand {

    UserCommand[] commandList;

    public Help() {
        format = "help";
        description = "Prints this brief commands summary";
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
            view.write("\t" + cmd.getFormat());
            String[] descriptions = cmd.getDescription().split("!NL");
            for (String description: descriptions) {
                view.write("\t\t" + description);
            }
        }
    }
}
