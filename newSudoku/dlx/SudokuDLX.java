package dlx;

import java.util.*;

public class SudokuDLX {

    public final int S;
    public final int side; // 一个格子有多长
    public int solutions; // 一共有多少个解
    public int[][] solutionBoard;

    public static void printBoard(int[][] solutionBoard) {
        System.out.println("--------------------------");
        for (int[] row : solutionBoard) {
            for (int item : row) {
                System.out.print(item + " ");
            }
            System.out.println("");
        }
        System.out.println("--------------------------");
    }

    public SudokuDLX(int gridLength, int side) {
        S = gridLength;
        this.side = side;
    }

    public static void copyBoard(int[][] board, int[][] newBoard) {
        int S = board[0].length;
        for (int i = 0; i < S; i++) {
            for (int j = 0; j < S; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
    }


    // sudoku has numbers 1-9. A 0 indicates an empty cell that we will need to
    // fill in.
    private int[][] makeExactCoverGrid(int[][] sudoku) {
        int[][] R = sudokuExactCover();
        for (int i = 1; i <= S; i++) {
            for (int j = 1; j <= S; j++) {
                int n = sudoku[i - 1][j - 1];
                // 遍历每一个位置,如果已经填了一个数字,那么就对grid进行相应的修改
                if (n != 0) { // zero out in the constraint board
                    for (int num = 1; num <= S; num++) {
                        if (num != n) {
                            // 其他的行都不需要再选了, 选出所有其他数组所对应的行,然后进行删除
                            Arrays.fill(R[getIdx(i, j, num)], 0);
                        }
                    }
                }
            }
        }
        return R;
    }

    // Returns the base exact cover grid for a SUDOKU puzzle
    private int[][] sudokuExactCover() {
        // 每一行代表在ith jth column 填写 k, 每一列分别代表四个限制
        int[][] R = new int[S * S * S][S * S * 4];

        int hBase = 0;

        // row-column constraints
        for (int r = 1; r <= S; r++) {
            for (int c = 1; c <= S; c++, hBase++) {
                for (int n = 1; n <= S; n++) {
                    R[getIdx(r, c, n)][hBase] = 1;
                }
            }
        }

        // row-number constraints
        for (int r = 1; r <= S; r++) {
            for (int n = 1; n <= S; n++, hBase++) {
                for (int c1 = 1; c1 <= S; c1++) {
                    R[getIdx(r, c1, n)][hBase] = 1;
                }
            }
        }

        // column-number constraints

        for (int c = 1; c <= S; c++) {
            for (int n = 1; n <= S; n++, hBase++) {
                for (int r1 = 1; r1 <= S; r1++) {
                    R[getIdx(r1, c, n)][hBase] = 1;
                }
            }
        }

        // box-number constraints

        for (int br = 1; br <= S; br += side) {
            for (int bc = 1; bc <= S; bc += side) {
                for (int n = 1; n <= S; n++, hBase++) {
                    for (int rDelta = 0; rDelta < side; rDelta++) {
                        for (int cDelta = 0; cDelta < side; cDelta++) {
                            R[getIdx(br + rDelta, bc + cDelta, n)][hBase] = 1;
                        }
                    }
                }
            }
        }

        return R;
    }

    // row [1,S], col [1,S], num [1,S] ,拿到对应的行数
    private int getIdx(int row, int col, int num) {
        return (row - 1) * S * S + (col - 1) * S + (num - 1);
    }


    public void runSolver(int[][] sudoku) {
        int[][] cover = makeExactCoverGrid(sudoku);
        DancingLinks dlx = new DancingLinks(cover, new SudokuHandler(S));
        dlx.runSolver();
        if (dlx.solutionBoard != null) {
            this.solutionBoard = new int[S][S];
            copyBoard(dlx.solutionBoard, this.solutionBoard);
        }else  {
            this.solutionBoard = null;
        }
        solutions = dlx.solutions;
    }

    public boolean solve(int[][] sudoku){
        if(!validateSudoku(sudoku)){
//            System.out.println("Error: Invalid sudoku. Aborting....");
            return false;
        }
//        S = sudoku.length;
//        side = (int)Math.sqrt(S);

        runSolver(sudoku);
        if (solutionBoard != null) {
            return true;
        }
        return false;
    }


    // 检查某一个board是否是合法的
    public static boolean validateSudoku(int[][] board){
        int S = board[0].length;
        if (S == 9 || S == 4) {
            int side = (int) Math.sqrt(S);
            // 每一行
            boolean[] filter = new boolean[S+1];
            Arrays.fill(filter, false);
            // 检查每一列
            for (int i = 0; i < S; i++) {
                // 每一列
                for (int j = 0; j < S; j++) {
                    if (board[j][i] != 0) {
                        if (filter[board[j][i]]) {
                            // 如果已经填了，说明有问题
                            return false;
                        } else {
                            // 填上
                            filter[board[j][i]] = true;
                        }
                    }

                }
                // 重新置0，准备检查下一列
                Arrays.fill(filter, false);
            }

            // 检查每一行
            for (int i = 0; i < S; i++) {
                for (int j = 0; j < S; j++) {
                    if (board[i][j] != 0) {
                        if (filter[board[i][j]]) {
                            return false;
                        } else {
                            filter[board[i][j]] = true;
                        }
                    }
                }
                Arrays.fill(filter, false);
            }

            // 检查每一个block
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    // i, j 分别为block起始点的位置，左上角
                    for (int m = i * side; m < i * side + side; m++) {
                        for (int n = j * side; n < j * side + side; n++) {
                            if (board[m][n] != 0) {
                                if (filter[board[m][n]]) {
                                    return false;
                                } else {
                                    filter[board[m][n]] = true;
                                }
                            }
                        }
                    }
                    Arrays.fill(filter, false);
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static boolean validatePosition(int[][] board, int yPos, int xPos, int value) {
        int S = board[0].length;
        if (value <= 0 || value > S) {
            return false;
        }
        if (S == 4 || S == 9) {
            int side = (int) Math.sqrt(S);
            boolean[] filter = new boolean[S+1];
            Arrays.fill(filter,false);
            // 检查填入后的行
            for (int i = 0; i < S; i++) {
                if (board[yPos][i] == value) {
                    return false;
                }
            }

            Arrays.fill(filter, false);
            for (int i = 0; i < S; i++) {
                if (board[i][xPos] == value) {
                    return false;
                }
            }

            Arrays.fill(filter, false);
            // 算出所在的block
            int xBlock = xPos / side;
            int yBlock = yPos / side;
            for (int m = yBlock * side; m < yBlock * side + side; m++) {
                for (int n = xBlock * side; n < xBlock * side + side; n++) {
                    if (board[m][n] == value) {
                        return false;
                    }
                }
            }

        } else {
            return false;
        }
        return true;
    }
}
