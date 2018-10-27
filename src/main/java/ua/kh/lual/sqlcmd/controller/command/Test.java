package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;

public class Test extends UserCommandClass {
    @Override
    public String format() {
        return "`|[n]";
    }

    @Override
    public String description() {
        return "Executes some hardcoded sequences. Can be used as additional test tool" +
                "\t<n> - test to start" +
                "\twithout parameter <n> starts previously started test (1 an startup)";
    }

    @Override
    public void process(String command) throws CommandFailedException {
        String[] parameters = extractParameters(command);
        view.write("Test'll be here soon");
    }

    @Override
    protected void checkParametersCount(int actualCount) {
        if (actualCount > 1) {
            throw new CommandFailedException("Too many parameters\nPlease use format: <" + format() + ">");
        }
    }

    @Override
    public boolean requestsConnection() {
        return false;
    }


}
