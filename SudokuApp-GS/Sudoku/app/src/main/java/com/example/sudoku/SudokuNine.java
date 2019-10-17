package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sudoku.dlx.GenerateBoard;

public class SudokuNine extends AppCompatActivity {
    private static int dp2px(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private TextView selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_nine);
        GenerateBoard board = new GenerateBoard(9, 3);
        board.initBoard();
        board.run(20);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.nine_grid);
        GridLayout options = (GridLayout) findViewById(R.id.nine_options);
        // 插入81个 TextView
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextView textView = new TextView(gridLayout.getContext());
                if (board.board[r][c] != 0) {
                    textView.setText(Integer.toString(board.board[r][c]));
                    textView.setTypeface(null, Typeface.BOLD);
                } else {
                    textView.setTextColor(Color.parseColor("#0066FF"));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 如果用户点击的和当前选中的不是同一个方格
                            if (selected != null && !selected.equals(v)) {
                                selected.setBackgroundResource(R.drawable.border);
                            }
                            v.setBackgroundResource(R.drawable.border_selected);
                            selected = (TextView) v;
                        }
                    });
                    textView.setText("");
                }
                textView.setTextSize(25);
                textView.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams layoutParams = new LinearLayout
                        .LayoutParams(dp2px(35, this)
                        ,dp2px(35, this));

                layoutParams.setMargins(8,8,8,8);
                textView.setLayoutParams(layoutParams);
                textView.setBackgroundResource(R.drawable.border);
                textView.setCursorVisible(false);
                gridLayout.addView(textView);
            }
        }
        // 插入9个选择的
        for (int num = 1; num <= 9; num++) {
            TextView textView = new TextView(gridLayout.getContext());
            textView.setText(Integer.toString(num));
            textView.setTextSize(25);
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout
                    .LayoutParams(dp2px(35, this)
                    ,dp2px(35, this));

            layoutParams.setMargins(8,8,8,8);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundResource(R.drawable.border);
            textView.setCursorVisible(false);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selected != null) {
                        TextView that = (TextView) v;
                        selected.setText(that.getText().toString());
                    }
                }
            });

            options.addView(textView);
        }
    }
}
