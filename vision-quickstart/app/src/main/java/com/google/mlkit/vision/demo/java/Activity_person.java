package com.google.mlkit.vision.demo.java;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.mlkit.vision.demo.R;

public class Activity_person extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        LinearLayout homeNav = findViewById(R.id.home);
        LinearLayout diaryNav = findViewById(R.id.diary);
        LinearLayout libraryNav = findViewById(R.id.action);

        homeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "主页" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_person.this, MainActivity.class);
                startActivity(intent);
            }
        });

        diaryNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "日记" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_person.this, Activity_diary.class);
                startActivity(intent);
            }
        });

        libraryNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "动作库" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_person.this, Activity_action.class);
                startActivity(intent);
            }
        });
    }
}
