package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SudokuGrid extends GridLayout {
    private int side;
    private int S;

    public SudokuGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuGrid(Context context, int S) {
        super(context);
//        this.setColumnCount(3);
//        this.setRowCount(3);
        this.S = S;
        if (S == 9) {
            this.side = 3;
        } else if (S == 4) {
            this.side = 2;
        }
        this.setColumnCount(side);
        this.setRowCount(side);
        LinearLayout.LayoutParams layoutParams = new LinearLayout
                .LayoutParams(SudokuNine.dp2px(S == 9 ? 12 : 36, this.getContext()), SudokuNine.dp2px(S == 9 ? 12 : 36, this.getContext()));
        layoutParams.gravity = Gravity.CENTER;
//        this.setForegroundGravity(Gravity.CENTER);
        for (int i = 1; i <= S; i++) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(layoutParams);
            textView.setLineSpacing(0, new Float(0.1));
//            textView.setText(Integer.toString(i));
            textView.setText("");
            textView.setTextSize(S == 9 ? 8 : 24);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#669999"));
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
