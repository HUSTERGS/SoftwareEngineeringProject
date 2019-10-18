package com.example.sudoku.UserAction;

import android.widget.GridLayout;

import com.example.sudoku.SudokuTextView;

public class EraseAction extends UserAction {

    public EraseAction(int column, int row, int originNum, int size) {
        super(0, column, row, originNum, -1, size);
    }

    @Override
    void undoAction(SudokuTextView textView, int[][] currentBoard) {
        textView.setText(Integer.toString(originNum));
        currentBoard[textView.getRow()][textView.getColumn()] = originNum;
    }
}
