package com.google.mlkit.vision.demo.java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.mlkit.vision.demo.R;

public class Activity_action extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        LinearLayout homeNav = findViewById(R.id.home);
        LinearLayout diaryNav = findViewById(R.id.diary);
        LinearLayout profileNav = findViewById(R.id.person);

        homeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "主页" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_action.this, MainActivity.class);
                startActivity(intent);
            }
        });

        diaryNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "日记" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_action.this, Activity_diary.class);
                startActivity(intent);
            }
        });

        profileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "个人" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_action.this, Activity_person.class);
                startActivity(intent);
            }
        });

    }
}
