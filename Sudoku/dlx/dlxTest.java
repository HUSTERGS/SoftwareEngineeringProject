import dlx.*;

public class dlxTest {
    public static void main(String[] args) {
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
        // 单纯解数独
        // 1. 实例化SudokuDLX();
        // 2. sudoku.solve(int[][] board) 返回是否成功
        // 3. 如果成功则可以通过 sudoku.solutionBoard获得解出来的board,并通过soduku.solutions查看可能的解的个数
        // 4. 如果不成功则sudoku.solutionBoard为null
        SudokuDLX sudoku = new SudokuDLX();

        if (sudoku.solve(hardest)) {
            System.out.println(sudoku.solutions);
            GenerateBoard.printBoard(sudoku.solutionBoard);
        } else {
            System.out.println("Invalid sudoku!");
        }

        // 生成数独
        GenerateBoard newBoard = new GenerateBoard();
        // 初始化一个棋盘,最开始随机放置了INITNUM个数字,然后开始解这个棋盘来得到终局,可以通过多次init来得到不同的终局
        newBoard.initBoard();
        // 开始挖洞,level参数为挖掉的数目
        newBoard.run(50);
        // 打印棋盘
        newBoard.printBoard();
        System.out.println(newBoard.actualLevel);
        SudokuDLX sudokuDLX = new SudokuDLX();
        sudokuDLX.solve(newBoard.board);
        System.out.println(sudokuDLX.solutions);

        // 可用的一些方法
        // GenerateBoard 类方法以及实例方法 printBoard 打印一个棋盘
        // GenerateBoard.printBoard(newBoard.board);
        // 或
        // newBoard.printBoard();
    }
}
