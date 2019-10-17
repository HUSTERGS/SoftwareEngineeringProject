package com.example.sudoku.dlx;

import android.annotation.TargetApi;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateBoard {
    public int[][] board;
    public int[][] anwser;
    private final int INITNUM;
    public int level;
    public int actualLevel;
    private int S;
    private int side;

    public GenerateBoard(int gridLength, int side) {
        this.S = gridLength;
        this.side = side;
        if (S == 9) {
            INITNUM = 30;
        } else {
            INITNUM = 5;
        }
    }

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


    public void run(int level) {
        if (level > S * S) {
            System.out.println("臣妾做不到啊~");
            return;
        }
        this.level = level;
        while(actualLevel != level) {
            initBoard();
            dig();
        }
    }

    // 根据初始局解出任意一个终局
    public void initBoard() {
        SudokuDLX sudoku = new SudokuDLX(S, side);
        randBorad();
        while(!sudoku.solve(board)) {
            randBorad();
        }
        SudokuDLX.copyBoard(sudoku.solutionBoard, board);
        anwser = new int[S][S];
        SudokuDLX.copyBoard(sudoku.solutionBoard, anwser);
    }

    // 生成一个随机的初始局,随机填入 INITNUM 个数字
    private void randBorad() {
        this.board = new int[S][S];
        int count = INITNUM;
        Random random = new Random();
        while(count > 0) {
            int xPositionRand = random.nextInt(S);
            int yPositionRand = random.nextInt(S);
            int value = random.nextInt(S);
            if (board[yPositionRand][xPositionRand] == 0) {
                if (SudokuDLX.validatePosition(board, yPositionRand, xPositionRand, value + 1)) {
                    board[yPositionRand][xPositionRand] = value + 1;
                    count--;
                }
            }
        }
    }
    @TargetApi(24)
    private void dig() {
        List<Integer> positions = IntStream.range(0, S * S)
                .boxed().collect(Collectors.toList());
        int count = level;
        Random random = new Random();
        SudokuDLX sudoku = new SudokuDLX(S, side);
        while(count > 0) {
            if (positions.isEmpty()) {
//                System.out.println("未达标");
                break;
            } else {
                int targetPosition = random.nextInt(positions.size());
//                int targetPosition = 0;
                int xPosition = positions.get(targetPosition) / S;
                int yPosition = positions.get(targetPosition) % S;
                int prevValue = board[xPosition][yPosition];
                positions.remove(targetPosition);

                boolean digAble = true;

                for (int i : IntStream.rangeClosed(1, S).toArray()) {
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
