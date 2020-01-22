import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodsForTest {
    public static void main(String[] args) {
//        int[] a = {1,2,3,4,1,5,3,1,4,5,6,6,7,1};
//        int[] a = {1,2,3,1,5,3,1,5,6,6,7,1};
//        int[] a = {2,2,2,2,2,2,3};
//        int[] a = {1, 1, 4, 4};
//        int [] a = {4,4,4,4};
//        int [] a = {1,1,1,1,1,1};
//        int [] a = {4,1,4,3};

//        try {
//            numberAfterLastFour(a);
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }

//        System.out.println(checkNumberOneAndFour(a));

    }


    public int[] numberAfterLastFour(int[] array) throws RuntimeException {
        int[] result = null;
        int indexOfLastFour = -1;

        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == 4) {
                indexOfLastFour = i + 1;
                break;
            }
        }

        if (indexOfLastFour > -1) {
            result = new int[array.length - indexOfLastFour];
            System.arraycopy(array, indexOfLastFour, result, 0, result.length);
        } else throw new RuntimeException();

        System.out.println(Arrays.toString(result));

        return result;
    }

    public boolean checkNumberOneAndFour(int[] array) {
        boolean result = true;
        List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());

        if ((list.indexOf(1) != -1) && (list.indexOf(4) != -1)) {
            for (int i : array) {
                if ((i != 1) && (i != 4)) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }

        return result;
    }

}
