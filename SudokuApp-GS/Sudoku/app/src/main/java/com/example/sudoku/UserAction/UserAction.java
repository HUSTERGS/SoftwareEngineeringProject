package com.example.sudoku.UserAction;

import android.widget.GridLayout;

import com.example.sudoku.SudokuTextView;

abstract public class UserAction {
    // 几种动作，擦除，在空的位置输入，在非空位置输入
    protected final int ERASE = 0;
    protected final int ENTER = 1;
    protected final int REPLACE = 2;

    protected int column;
    protected int row;
    protected int originNum;
    protected int currentNum;
    protected int actionType;
    protected int size;

    public int getIndex() {
        return row * size + column;
    }

    public UserAction(int type, int column, int row, int originNum, int currentNum, int size) {
        this.column = column;
        this.row = row;
        this.originNum = originNum;
        this.currentNum = currentNum;
        this.actionType = type;
        this.size = size;
    }
    public void undo(GridLayout gridLayout, int[][] currentBoard){
        undoAction((SudokuTextView) gridLayout.getChildAt(row * size + column), currentBoard);
    }
    abstract void undoAction(SudokuTextView textView, int[][] currentBoard);
}
