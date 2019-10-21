package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setContentView(R.layout.activity_login);
        LitePal.getDatabase();
        // 用户名和密码
        TextInputEditText userName = findViewById(R.id.userName);
        TextInputEditText password = findViewById(R.id.pw);

        MaterialButton registerBt = findViewById(R.id.registerBt);
        MaterialButton loginBt = findViewById(R.id.loginBt);
        TextView skip = findViewById(R.id.skipLogin);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("currentUser", MODE_PRIVATE).edit();
                editor.putString("name", "Anonymous");
                editor.putString("pass", "Anonymous");
                editor.apply();
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pw = password.getText().toString();
                if (user.length() == 0 || pw.length() == 0) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空哦"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    List<userInfo> result = LitePal.where("userName = ?", user).find(userInfo.class);
                    if (result.size() == 0) {
                        Toast.makeText(getApplicationContext(), "用户不存在哦",
                                Toast.LENGTH_SHORT).show();
                    } else if (result.get(0).getPasswordHash().equals(MD5.getMd5(pw))) {
                        // 如果密码正确则登录成功
                        SharedPreferences.Editor editor = getSharedPreferences("currentUser", MODE_PRIVATE).edit();
                        editor.putString("name", user);
//                        editor.putString("pass", MD5.getMd5(pw));
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "登录成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        password.setText("");
                        password.callOnClick();
                        Toast.makeText(getApplicationContext(), "密码错误哦",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pw = password.getText().toString();
                if (user.length() == 0 || pw.length() == 0) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空哦"
                            , Toast.LENGTH_SHORT).show();
//                    userName.setOutlineAmbientShadowColor(Color.parseColor("#ff0000"));
//                    userName.setOutlineSpotShadowColor(Color.parseColor("#ff0000"));
                } else {
                    List<userInfo> result = LitePal.where("userName = ?", user).find(userInfo.class);
                    if (result.size() != 0) {
                        Toast.makeText(getApplicationContext(), "用户已存在哦，请换个名字吧",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        userInfo userRegister = new userInfo();
                        userRegister.setUserName(user);
                        userRegister.setPasswordHash(MD5.getMd5(pw));
                        userRegister.save();
                        SharedPreferences.Editor editor = getSharedPreferences("currentUser", MODE_PRIVATE).edit();
                        editor.putString("name", user);
//                        editor.putString("pass", MD5.getMd5(pw));
                        editor.apply();
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });


        TextView wipeAll = findViewById(R.id.wipeAll);
        wipeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(userInfo.class);
                LitePal.deleteAll(LastRecord.class);
                LitePal.deleteAll(GameRecord.class);

                Toast.makeText(getApplicationContext(), "所有数据已清除", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
