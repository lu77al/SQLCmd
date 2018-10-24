package ua.kh.lual.sqlcmd.controller.command;

public class Help extends UserCommandClass {

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
    public void process(String command) {
        String[] parameters = extractParameters(command);
        if (parameters == null) return;
        view.write("You can use next commands:");
        for (UserCommand cmd: commandList) {
            view.write("\t" + cmd.format());
            String[] descriptions = cmd.description().split("\n");
            for (String description: descriptions) {
                view.write("\t\t" + description);
            }
        }
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }

}
