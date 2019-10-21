package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LitePal.initialize(this);
        setContentView(R.layout.activity_main);
//        LitePal.getDatabase();
        MaterialButton startGame = findViewById(R.id.materialButton4);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseType.class);
                startActivity(intent);
            }
        });

        MaterialButton checkHistory = findViewById(R.id.materialButton3);
        checkHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref = getSharedPreferences("currentUser", MODE_PRIVATE);
        String name = pref.getString("name", "");
        if (name == "Anonymous") {
            Toast.makeText(getApplicationContext(), "你以匿名模式登录" + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "欢迎来到数独世界~  " + name, Toast.LENGTH_SHORT).show();
        }
    }
}
