import dlx.*;

public class dlxTest {
    public static void main(String[] args) {
//        int[][] hardest = {
//                {0,0,0,0,8,2,9,5,6},
//                {5,8,0,6,0,0,0,0,4},
//                {0,0,3,1,0,0,2,0,0},
//                {0,4,1,0,7,0,5,0,0},
//                {7,6,0,5,0,1,0,9,2},
//                {0,0,9,0,4,0,1,8,0},
//                {0,0,5,0,0,8,7,0,0},
//                {8,0,0,0,0,5,0,3,9},
//                {9,2,6,7,1,0,0,0,0}
//        };
//        int[][] testFour = {
//                {0,3,1,0},
//                {1,0,0,3},
//                {2,0,3,4},
//                {0,4,2,0}
//        };
//        SudokuDLX sudoku = new SudokuDLX(9, 3);
//
//        if (sudoku.solve(hardest)) {
//            System.out.println(sudoku.solutions);
//            SudokuDLX.printBoard(sudoku.solutionBoard);
//        } else {
//            System.out.println("Invalid sudoku!");
//        }

        GenerateBoard newBoard = new GenerateBoard(4,2);
        // 初始化一个棋盘,最开始随机放置了INITNUM个数字,然后开始解这个棋盘来得到终局,可以通过多次init来得到不同的终局
        newBoard.initBoard();
        // 开始挖洞,level参数为挖掉的数目
        newBoard.run(12);
        // 打印棋盘
        newBoard.printBoard();

        System.out.println(newBoard.actualLevel);
        SudokuDLX sudokuDLX = new SudokuDLX(4,2);
        sudokuDLX.solve(newBoard.board);
        System.out.println(sudokuDLX.solutions);
    }
}
