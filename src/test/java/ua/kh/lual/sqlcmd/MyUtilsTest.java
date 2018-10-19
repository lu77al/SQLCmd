package ua.kh.lual.sqlcmd;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MyUtilsTest {
    @Test
    public void testResizeArray() {
        String[] strArr = {"Hello", "Good bye"};
        strArr = MyUtils.resizeArray(strArr, 4);
        assertEquals("[Hello, Good bye, null, null]", Arrays.toString(strArr));

        Object[] objArr = new Object[]{ new Integer(1), new int[] {2, 3 , 4}};
        objArr = MyUtils.resizeArray(objArr, 5);
        assertEquals("[1, [2, 3, 4], null, null, null]", Arrays.deepToString(objArr));

        objArr = MyUtils.resizeArray(objArr, 1);
        assertEquals("[1]", Arrays.deepToString(objArr));
    }
}
