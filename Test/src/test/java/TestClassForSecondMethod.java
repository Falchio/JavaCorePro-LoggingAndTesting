import org.junit.Assert;
import org.junit.Test;

public class TestClassForSecondMethod {
    MethodsForTest forTest = new MethodsForTest();

    @Test
    public void test(){
        Assert.assertFalse(forTest.checkNumberOneAndFour(new int[]{1,1,1,1}));
        Assert.assertFalse(forTest.checkNumberOneAndFour(new int[]{4,4,4,4}));
        Assert.assertFalse(forTest.checkNumberOneAndFour(new int[]{1,1,3,4,4}));
        Assert.assertFalse(forTest.checkNumberOneAndFour(new int[]{1}));
        Assert.assertTrue(forTest.checkNumberOneAndFour(new int[]{1,4,1,1,4,4}));
        Assert.assertTrue(forTest.checkNumberOneAndFour(new int[]{1,4}));
    }


}
