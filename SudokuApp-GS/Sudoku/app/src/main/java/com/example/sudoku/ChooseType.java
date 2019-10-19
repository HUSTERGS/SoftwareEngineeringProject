package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class ChooseType extends AppCompatActivity {

    private LastRecord lastRecord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);

        MaterialButton typeFour = (MaterialButton) findViewById(R.id.typeFour);
        MaterialButton typeNine = (MaterialButton) findViewById(R.id.typeNine);
        typeFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseType.this, ChooseLevel.class);
                intent.putExtra("gridLength", 4);
                startActivity(intent);
            }
        });

        typeNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseType.this, ChooseLevel.class);
                intent.putExtra("gridLength", 9);
                startActivity(intent);
            }
        });

        MaterialButton resume = (MaterialButton) findViewById(R.id.resume);


        List<LastRecord> record = LitePal.findAll(LastRecord.class);
        if (record.isEmpty()) {
            resume.setClickable(false);
        } else {
            this.lastRecord = record.get(0);
        }

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseType.this, SudokuNine.class);
                intent.putExtra("resume", true);
                intent.putExtra("gridLength", lastRecord.getType());
                startActivity(intent);
            }
        });
    }
}
