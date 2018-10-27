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
        if (testId.equals("startupDefaultTest")) {
            startupDefaultTest();
            return;
        }
        if (testId.equals("1")) {
            test_1();
            return;
        }
        view.write("Such test hasn't prepared");
    }

    private void startupDefaultTest() {
        executeCMD(new Connect(), "connect|sqlcmd|postgres|12345");
    }

    private void test_1() {
        executeCMD(new Connect(), "connect|sqlcmd|postgres|12345");
        executeCMD(new Tables(), "tables");
        executeCMD(new Create(), "create|tasks|id|description|deadline");
        executeCMD(new Find(), "find|tasks");
        executeCMD(new Insert(), "insert|tasks|id|1|description|view lecture|deadline|10.10.2018");
        executeCMD(new Insert(), "insert|tasks|id|2|description|finish SQLcmd|deadline|25.12.2018");
        executeCMD(new Insert(), "insert|tasks|id|3|description|get java job|deadline|15.03.2019");
        executeCMD(new Insert(), "insert|tasks|id|4|description|buy a new car|deadline|2.08.2019");
        executeCMD(new Find(), "find|tasks");
        executeCMD(new Update(), "update|tasks|deadline|20.12.2018|description|finish SQLcmd");
        executeCMD(new Delete(), "delete|tasks|description|buy a new car");
        executeCMD(new Find(), "find|tasks");
        executeCMD(new Drop(), "drop|tasks");
        executeCMD(new Tables(), "tables");
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
