package com.example.sudoku;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.sudoku.SudokuNine;

public class SudokuGrid extends GridLayout {
    public SudokuGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuGrid(Context context) {

        super(context);
        this.setColumnCount(3);
        this.setRowCount(3);

        LinearLayout.LayoutParams layoutParams = new LinearLayout
                .LayoutParams(SudokuNine.dp2px(12, this.getContext()), SudokuNine.dp2px(12, this.getContext()));
        layoutParams.gravity = Gravity.CENTER;
//        this.setForegroundGravity(Gravity.CENTER);
        for (int i = 1; i <= 9; i++) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(layoutParams);
            textView.setLineSpacing(0, new Float(0.1));
//            textView.setText(Integer.toString(i));
            textView.setText("");
            textView.setTextSize(8);
            textView.setGravity(Gravity.CENTER);

            this.addView(textView);
        }
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.height = LayoutParams.MATCH_PARENT;
//        layoutParams.width = LayoutParams.MATCH_PARENT;
////        layoutParams1.setGravity(Gravity.CENTER);
//
////        LinearLayout layoutParams1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams1);
    }
}
