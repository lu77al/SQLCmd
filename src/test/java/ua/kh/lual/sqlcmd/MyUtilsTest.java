package ua.kh.lual.sqlcmd;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MyUtilsTest {
    @Test
    public void testExpandArray() {
        String[] strArr = {"Hello", "Good bye"};
        strArr = MyUtils.expandArray(strArr, 2);
        assertEquals("[Hello, Good bye, null, null]", Arrays.toString(strArr));

        Object[] objArr = new Object[]{ new Integer(1), new int[] {2, 3 , 4}};
        objArr = MyUtils.expandArray(objArr, 3);
        assertEquals("[1, [2, 3, 4], null, null, null]", Arrays.deepToString(objArr));
    }
}
