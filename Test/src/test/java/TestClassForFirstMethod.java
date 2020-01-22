import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class TestClassForFirstMethod {
    private int[] testArray;
    private int[] expectedArray;

    private MethodsForTest forTest;

    public TestClassForFirstMethod(int[] testArray, int[] expectedArray) {
        this.testArray = testArray;
        this.expectedArray = expectedArray;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new int[][][]{
                {{1, 2, 3, 4, 5, 6},{5,6}},
                {{2,3,4,4,4,1,1,2},{1,1,2}},
                {{4,4,4,4,4},{}},
                {{1,1,12,4,34,2,1},{34,2,1}},
                {{1,4},{}},
        });
    }


    @Before
    public void init() {
        forTest = new MethodsForTest();
    }

    @Test
    public void massTest() {
        Assert.assertArrayEquals(expectedArray, forTest.numberAfterLastFour(testArray));
    }

    @Test(expected = RuntimeException.class)
    public void notHaveFour(){
        forTest.numberAfterLastFour(new int[]{1});
    }

}
