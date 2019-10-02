package dlx;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateBoard {

    public int[][] board;
    private static final int INITNUM = 30;
    public int level;
    public int actualLevel;
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

    public void printBoard() {
        printBoard(board);
    }


//    public static void main(String[] args) {
//        GenerateBoard newBoard = new GenerateBoard();
//
//        newBoard.initBoard();
//        newBoard.printBoard();
//        newBoard.run(10);
//
//        newBoard.printBoard();
//    }

    public void run(int level) {
        this.level = level;
        while(actualLevel != level) {
            initBoard();
            dig();
        }
    }

    // 根据初始局解出任意一个终局
    public void initBoard() {
        SudokuDLX sudoku = new SudokuDLX();
        randBorad();
        while(!sudoku.solve(board)) {
            randBorad();
        }
        SudokuDLX.copyBoard(sudoku.solutionBoard, board);
    }
    // 生成一个随机的初始局,随机填入 INITNUM 个数字
    private void randBorad() {
        this.board = new int[9][9];
        int count = INITNUM;
        Random random = new Random();
        while(count > 0) {
            int xPositionRand = random.nextInt(9);
            int yPositionRand = random.nextInt(9);
            int value = random.nextInt(9);
            if (board[xPositionRand][yPositionRand] == 0) {
                board[xPositionRand][yPositionRand] = value + 1;
                if (SudokuDLX.validateSudoku(board)) {
                    count--;
                } else {
                    board[xPositionRand][yPositionRand] = 0;
                }
            }
        }
    }

    private void dig() {
        List<Integer> positions = IntStream.range(0, 81)
                .boxed().collect(Collectors.toList());
        int count = level;
        Random random = new Random();
        SudokuDLX sudoku = new SudokuDLX();
        while(count > 0) {
            if (positions.isEmpty()) {
//                System.out.println("未达标");
                break;
            } else {
//                int targetPosition = random.nextInt(positions.size());
                int targetPosition = 0;
                int xPosition = positions.get(targetPosition) / 9;
                int yPosition = positions.get(targetPosition) % 9;
                int prevValue = board[xPosition][yPosition];
                positions.remove(targetPosition);

                boolean digAble = true;

                for (int i : IntStream.rangeClosed(1, 9).toArray()) {
                    if (i != prevValue) {
                        board[xPosition][yPosition] = i;
                        if (sudoku.solve(board)) {
                            // 如果有解
                            digAble = false;
                            break;
                        }
                    }
                }
                if (digAble) {
                    board[xPosition][yPosition] = 0;
                    count--;
                } else {
                    board[xPosition][yPosition] = prevValue;
                }
            }
        }
        actualLevel = level - count;
    }


}
