package com.example.sudoku.UserAction;

import android.widget.GridLayout;

import com.example.sudoku.SudokuTextView;

public class ReplaceAction extends UserAction {
    public ReplaceAction(int column, int row, int originNum, int currentNum, int size) {
        super(2, column, row, originNum, currentNum, size);
    }

    @Override
    void undoAction(SudokuTextView textView, int[][] currentBoard) {
        textView.setText(Integer.toString(originNum));
        currentBoard[textView.getRow()][textView.getColumn()] = originNum;
    }
}
