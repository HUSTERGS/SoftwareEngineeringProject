package com.example.sudoku;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.solver.GoalRow;

import org.w3c.dom.Text;
import com.example.sudoku.SudokuNine;

public class SudokuTextView extends AppCompatTextView {
    private int column;
    private int row;

    public int getColumn(){
        return column;
    }

    public int getRow() {
        return row;
    }

    public SudokuTextView(Context context, AttributeSet attrs) {
        super(context,attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SudokuTextViewCustomAttr);

        int column = ta.getInteger(R.styleable.SudokuTextViewCustomAttr_column, -1);
        int row = ta.getInteger(R.styleable.SudokuTextViewCustomAttr_row, -1);
        this.column = column;
        this.row = row;
    }

    public SudokuTextView(Context context, int column, int row) {
        super(context);
        this.column = column;
        this.row = row;
    }
}
