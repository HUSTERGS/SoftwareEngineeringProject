package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudoku.UserAction.EnterAction;
import com.example.sudoku.UserAction.EraseAction;
import com.example.sudoku.UserAction.ReplaceAction;
import com.example.sudoku.UserAction.UserAction;
import com.example.sudoku.dlx.GenerateBoard;
import com.example.sudoku.dlx.SudokuDLX;

import java.util.Stack;


public class SudokuNine extends AppCompatActivity {
    private static int dp2px(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }



    private SudokuTextView selected;
    private boolean showColor;
    private boolean showhint;
    private boolean paused;
    private long timeWhenStopped;
    private int[][] originBoard;
    private int[][] anwserBoard;
    private int[][] currentBoard;

    private Stack<UserAction> userActionStack = new Stack<>();
    // 暂停的时候禁止大部分的按钮
    private void disableAllButton(boolean disable) {
        GridLayout options = findViewById(R.id.nine_options);
        for (int i = 0; i < options.getChildCount(); i++) {
            TextView textView = (TextView) options.getChildAt(i);
            textView.setEnabled(!disable);
        }
        ImageButton eraseButton = findViewById(R.id.erase);
        ImageButton note = findViewById(R.id.note);
        ImageButton undo = findViewById(R.id.restore);
        ImageButton hint = findViewById(R.id.hint);
        int disableColor = Color.argb(255, 120, 120, 120);
        int enanleColor = Color.argb(255, 0, 0, 0);
        undo.setColorFilter(disable ? disableColor : enanleColor);
        eraseButton.setColorFilter(disable ? disableColor : enanleColor);
        note.setColorFilter(disable ? disableColor : enanleColor);
        hint.setColorFilter(disable ? disableColor : enanleColor);

        hint.setEnabled(!disable);
        undo.setEnabled(!disable);
        eraseButton.setEnabled(!disable);
        note.setEnabled(!disable);
    }
    // 检查单个TextView是否正确
    private boolean checkSingle(SudokuTextView textView) {
        int row = selected.getRow();
        int column = selected.getColumn();
        return currentBoard[row][column] == anwserBoard[row][column];
    }
    // 显示错误检测
    private void showWrong(GridLayout gridLayout) {
//        int position;
//        for (int r = 0; r < 9; r++) {
//            for (int c = 0; c < 9; c++) {
//                if (anwserBoard[r][c] != currentBoard[r][c]) {
//                    position = r * 9 + c;
//                    SudokuTextView textView = (SudokuTextView) gridLayout.getChildAt(position);
//                    if (textView.getText().toString() != "") {
//                        gridLayout.getChildAt(position).setBackgroundResource(R.drawable.wrong_box);
//                    }
//                }
//            }
//        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (currentBoard[r][c] != 0 && anwserBoard[r][c] != currentBoard[r][c]) {
                    gridLayout.getChildAt(r * 9 + c).setBackgroundResource(R.drawable.wrong_box);
                }
            }
        }
    }
    // 不显示错误检测
    private void cancelShowWrong(GridLayout gridLayout) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (anwserBoard[r][c] != currentBoard[r][c]) {
                    gridLayout.getChildAt(r * 9 + c).setBackgroundResource(R.drawable.border);
                }
            }
        }
    }

    // 聚焦到某一个方格
    private void focus(int column, int row, GridLayout gridLayout) {
        int positionCol;
        int positionRow;
        if (!showhint) {
            cancelShowWrong(gridLayout);
        }

        int targetStyle = showColor ? R.drawable.focus : R.drawable.border;

        for (int i = 0; i < 9; i++) {
            // 同一列
            positionRow = i * 9 + column;
            // 同一行
            positionCol = i + 9 * row;
            gridLayout.getChildAt(positionRow).setBackgroundResource(targetStyle);
            gridLayout.getChildAt(positionCol).setBackgroundResource(targetStyle);
        }
        // 同一个小方格
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                positionCol = (i + (column / 3) * 3) + (j + (row / 3) * 3) * 9;
                gridLayout.getChildAt(positionCol).setBackgroundResource(targetStyle);
            }
        }
        gridLayout.getChildAt(row * 9 + column).setBackgroundResource(R.drawable.border_selected);

        if (showhint) {
            showWrong(gridLayout);
        }
    }
    // 取消聚焦
    private void unfocus(int column, int row, GridLayout gridLayout) {
        int positionCol;
        int positionRow;
        for (int i = 0; i < 9; i++) {
            // 同一列
            positionRow = i * 9 + column;
            // 同一行
            positionCol = i + 9 * row;
            gridLayout.getChildAt(positionRow).setBackgroundResource(R.drawable.border);
            gridLayout.getChildAt(positionCol).setBackgroundResource(R.drawable.border);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                positionCol = (i + (column / 3) * 3) + (j + (row / 3) * 3) * 9;
                gridLayout.getChildAt(positionCol).setBackgroundResource(R.drawable.border);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_nine);
        GenerateBoard board = new GenerateBoard(9, 3);
        board.initBoard();
        board.run(20);
        // 复制三个Board
        anwserBoard = new int[9][9];
        currentBoard = new int[9][9];
        originBoard = new int[9][9];
        SudokuDLX.copyBoard(board.anwser, anwserBoard);
        SudokuDLX.copyBoard(board.board, originBoard);
        SudokuDLX.copyBoard(board.board, currentBoard);

        GridLayout gridLayout = findViewById(R.id.nine_grid);
        GridLayout options = findViewById(R.id.nine_options);
        // 插入81个 TextView
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                SudokuTextView textView = new SudokuTextView(gridLayout.getContext(), c, r);
                if (board.board[r][c] != 0) {
                    textView.setText(Integer.toString(board.board[r][c]));
                    textView.setTypeface(null, Typeface.BOLD);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 如果用户点击了最开始生成的数字,则将selected设置为null
                            if (selected != null) {
                                unfocus(selected.getColumn(), selected.getRow(), gridLayout);
                            }
                            selected = null;
                        }
                    });

                } else {
                    textView.setTextColor(Color.parseColor("#0066FF"));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 如果用户点击的和当前选中的不是同一个方格
                            if (selected != null && !selected.equals(v)) {
                                unfocus(selected.getColumn(), selected.getRow(), gridLayout);
                            }
                            v.setBackgroundResource(R.drawable.border_selected);
                            selected = (SudokuTextView) v;
                            focus(selected.getColumn(), selected.getRow(), gridLayout);
                        }
                    });
                    textView.setText("");
                }
                textView.setTextSize(25);
                textView.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams layoutParams = new LinearLayout
                        .LayoutParams(dp2px(35, this)
                        , dp2px(35, this));

                layoutParams.setMargins(8, 8, 8, 8);
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
                    , dp2px(35, this));

            layoutParams.setMargins(8, 8, 8, 8);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundResource(R.drawable.option_background);
            textView.setCursorVisible(false);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selected != null) {
                        TextView that = (TextView) v;
                        if (selected.getText().toString() == "") {
                            userActionStack.push(new EnterAction(selected.getColumn(),
                                    selected.getRow(),
                                    Integer.parseInt(that.getText().toString()), 9));
                        } else if (selected.getText().toString() != that.getText().toString()) {
                            // 不重复记录
                            userActionStack.push(new ReplaceAction(selected.getColumn(),
                                    selected.getRow(),
                                    Integer.parseInt(selected.getText().toString()),
                                    Integer.parseInt(that.getText().toString()), 9));
                        }
                        selected.setText(that.getText().toString());
                        currentBoard[selected.getRow()][selected.getColumn()] =
                                Integer.parseInt(selected.getText().toString());
                        selected.callOnClick();
                    }
                }
            });

            options.addView(textView);
        }

        // 添加擦除点击时间监听
        ImageButton eraseButton = findViewById(R.id.erase);
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != null) {
                    if (selected.getText() == "") {
                        Toast.makeText(getApplicationContext(),
                                "选中的方格还没有填写哦",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Animation rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.moveerase);
                        v.startAnimation(rotateAnimation);
                        userActionStack.push(new EraseAction(selected.getColumn(),
                                selected.getRow(),
                                Integer.parseInt(selected.getText().toString()), 9));
                        currentBoard[selected.getRow()][selected.getColumn()] = 0;
                        selected.setText("");
                        selected.callOnClick();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "还没有选中要擦除的方格哦",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Color switch 点击监听
        Switch colorSwitch = findViewById(R.id.colorOn);
        colorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showColor = isChecked;
                if (selected != null) {
                    selected.callOnClick();
                }
            }
        });

        // Correct switch 点击监听
        Switch correctSwitch = findViewById(R.id.correctSwitch);
        correctSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showhint = isChecked;
                if (selected != null) {
                    selected.callOnClick();
                } else {
                    showWrong(gridLayout);
                }
            }
        });

        // undo事件监听
        ImageButton undo = findViewById(R.id.restore);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userActionStack.empty()) {
                    Animation rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.rotateundo);
                    v.startAnimation(rotateAnimation);
                    UserAction action = userActionStack.pop();
                    action.undo(gridLayout, currentBoard);
                    gridLayout.getChildAt(action.getIndex()).callOnClick();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "已经回到最开始了哦",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        Chronometer chronometer = findViewById(R.id.timer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        paused = false;
        // 暂停点击事件
        ImageButton pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paused) {
                    chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    chronometer.start();
                    pause.setImageResource(R.drawable.ic_pause_black_24dp);
                    disableAllButton(false);
                } else {
                    timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.stop();
                    pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                    // 无法填入数字等等
                    disableAllButton(true);
                }
                paused = !paused;
            }
        });

        // 提示点击事件
        ImageButton hint = findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != null) {
                    if (checkSingle(selected)) {
                        Toast.makeText(getApplicationContext(),
                                "这个位置已经是正确的哦", Toast.LENGTH_SHORT).show();
                    } else {
                        options.getChildAt(
                                anwserBoard[selected.getRow()][selected.getColumn()] - 1)
                                .callOnClick();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "还没有选择要提示的位置哦", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
