package ua.kh.lual.sqlcmd.controller.command;

import ua.kh.lual.sqlcmd.controller.exceptions.CommandFailedException;

public class Test extends UserCommandClass {
    String testId = "startupDefaultTest";

    @Override
    public String format() {
        return "`|[n]";
    }

    @Override
    public String description() {
        return "Executes some hardcoded sequences. Can be used as additional runtime test tool" +
                "\t<n> - test to start" +
                "\twithout parameter <n> starts previously started test (1 an startup)";
    }

    @Override
    protected void execute(String[] parameters) {
        if (parameters.length != 0) { // connect my test DB
            testId = parameters[0];
        } else {
            view.write("Test to execute:  <" + testId + ">");
        }
        if (testId == "startupDefaultTest") {
            startupDefaultTest();
            return;
        }
        view.write("Such test hasn't prepared");
    }

    private void startupDefaultTest() {
        executeCMD(new Connect(), "connect|sqlcmd|postgres|12345");
    }

    private void executeCMD(UserCommand cmd, String userInput) {
        view.write("\033[0;36m" + userInput + "\033[0;30m");
        cmd.process(userInput);
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
