package ua.kh.lual.sqlcmd.controller;

import org.junit.Before;
import org.junit.Test;
import ua.kh.lual.sqlcmd.controller.command.*;
import ua.kh.lual.sqlcmd.controller.exceptions.ExitException;

import static junit.framework.TestCase.fail;

public class HelpTest extends ABasicCommandTestClass {

    @Before
    public void setup() {
        setupMocks();
        cmd = new Help();
        UserCommand[] commands = new UserCommand[]{
                new Connect(),
                new Tables(),
                new Find(),
                new Clear(),
                new Insert(),
                new Update(),
                new Delete(),
                new Create(),
                new Drop(),
                new Exit(),
                new ua.kh.lual.sqlcmd.controller.command.Test(),
                cmd
        };
        ((Help) cmd).setCommandList(commands);
    }

    @Test
    public void testHelp() {
        // given
        // when
        cmd.process("help");
        // then
        assertOutput( "" +
                "You can use next commands:\n" +
                "\tconnect|database|user|password\n" +
                "\t\tConnects to database <database> as user <user> with password <password>\n" +
                "\ttables\n" +
                "\t\tPrints tables names of connected database\n" +
                "\tfind|tableName\n" +
                "\t\tPrints content of the table <tableName>\n" +
                "\tclear|tableName\n" +
                "\t\tClears table <tableName>\n" +
                "\tinsert|tableName|column1|value1|column2|value2| ... |columnN |valueN\n" +
                "\t\tInserts one new row in the table <tableName>\n" +
                "\t\t\t<column1> - name of first column to insert\n" +
                "\t\t\t<value1>  - value to insert into first column\n" +
                "\t\t\t<column2> - name of second column to insert\n" +
                "\t\t\t<value2>  - value to insert into second column\n" +
                "\t\t\t<columnN> - name of N's column to insert\n" +
                "\t\t\t<valueN>  - value to insert into N's column\n" +
                "\tupdate|tableName|destColumn|destValue|keyColumn|keyValue\n" +
                "\t\tUpdates value of specified cells in the table <tableName>\t<destColumn> - name of column to update\n" +
                "\t\t\t<destValue>  - new value for column to update\n" +
                "\t\t\t<keyColumn>  - name of column to check before update\n" +
                "\t\t\t<keyValue>   - update occurs if keyValue equals actual value of keyColumn\n" +
                "\t\t\t  * data in several rows could be updated\n" +
                "\tdelete|tableName|column|value\n" +
                "\t\tDeletes rows from table <tableName> in which column <column> has value <value>\n" +
                "\tcreate|tableName|column1|column2|...|columnN\n" +
                "\t\tcreates table <tableName> with specified columns\n" +
                "\tdrop|tableName\n" +
                "\t\tDeletes table <tableName> from database\n" +
                "\texit\n" +
                "\t\tTerminates application\n" +
                "\t`|[n]\n" +
                "\t\tExecutes some hardcoded sequences. Can be used as additional runtime test tool\t<n> - test to start\twithout parameter <n> starts previously started test (1 an startup)\n" +
                "\thelp\n" +
                "\t\tPrints this brief commands summary\n");
    }

}
