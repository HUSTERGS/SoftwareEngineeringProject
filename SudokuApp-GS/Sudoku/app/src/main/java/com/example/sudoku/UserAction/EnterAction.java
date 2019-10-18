package com.example.sudoku.UserAction;

import android.widget.GridLayout;

import com.example.sudoku.SudokuTextView;

public class EnterAction extends UserAction {
    public EnterAction(int column, int row, int currentNum, int size) {
        super(1, column, row, -1, currentNum, size);
    }

    @Override
    void undoAction(SudokuTextView textView, int[][] currentBoard) {
        textView.setText("");
        currentBoard[textView.getRow()][textView.getColumn()] = 0;
    }
}
