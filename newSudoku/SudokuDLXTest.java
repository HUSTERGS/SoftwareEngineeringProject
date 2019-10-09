import dlx.SudokuDLX;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

public class SudokuDLXTest {
    @Test
    public void test() {
        int[][] hardest = {
                {0,0,0,0,8,2,9,5,6},
                {5,8,0,6,0,0,0,0,4},
                {0,0,3,1,0,0,2,0,0},
                {0,4,1,0,7,0,5,0,0},
                {7,6,0,5,0,1,0,9,2},
                {0,0,9,0,4,0,1,8,0},
                {0,0,5,0,0,8,7,0,0},
                {8,0,0,0,0,5,0,3,9},
                {9,2,6,7,1,0,0,0,0}
        };
        int[][] testFour = {
                {0,3,1,0},
                {1,0,0,3},
                {2,0,3,4},
                {0,4,2,0}
        };
        Assert.assertTrue(SudokuDLX.validateSudoku(hardest));
        Assert.assertTrue(SudokuDLX.validateSudoku(testFour));
        Assert.assertTrue(SudokuDLX.validatePosition(testFour, 0, 0, 4));
        Assert.assertFalse(SudokuDLX.validatePosition(testFour,0,0,2));
    }
}
