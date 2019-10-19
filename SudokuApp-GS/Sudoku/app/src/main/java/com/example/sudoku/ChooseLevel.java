package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ChooseLevel extends AppCompatActivity {


    private void disableButtons() {
        LinearLayout easy = (LinearLayout) findViewById(R.id.easylevel);
        LinearLayout medium = (LinearLayout) findViewById(R.id.mediumlevel);
        LinearLayout hard = (LinearLayout) findViewById(R.id.hardlevel);
        LinearLayout devil = (LinearLayout) findViewById(R.id.devil_level);
        easy.setClickable(false);
        easy.setFocusable(false);
        medium.setClickable(false);
        medium.setFocusable(false);
        hard.setClickable(false);
        hard.setFocusable(false);
        devil.setClickable(false);
        devil.setFocusable(false);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        Intent intent = getIntent();
        int gridLength = intent.getIntExtra("gridLength", 9);

        LinearLayout easy = findViewById(R.id.easylevel);
        LinearLayout medium = findViewById(R.id.mediumlevel);
        LinearLayout hard = findViewById(R.id.hardlevel);
        LinearLayout devil = findViewById(R.id.devil_level);

        LinearLayout[] layouts = new LinearLayout[]{easy, medium, hard, devil};
        for (int i = 0; i < layouts.length; i++) {
            final int value = gridLength == 9? i * 10 + 20 : i * 3 + 3;
            layouts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disableButtons();
                    Intent intent = new Intent(ChooseLevel.this, SudokuNine.class);
                    intent.putExtra("level", value);
                    intent.putExtra("gridLength", gridLength);
                    startActivity(intent);
                }
            });
            LinearLayout subLinear = (LinearLayout) layouts[i].getChildAt(1);
            for (int t = 0; t < subLinear.getChildCount(); t++) {
                ImageButton imageButton = (ImageButton) subLinear.getChildAt(t);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        disableButtons();
                        Intent intent = new Intent(ChooseLevel.this, SudokuNine.class);
                        intent.putExtra("level", value);
                        intent.putExtra("gridLength", gridLength);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
