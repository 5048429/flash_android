package com.google.mlkit.vision.demo.java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.mlkit.vision.demo.R;

public class Activity_diary extends Activity {

    private TextView titleTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        LinearLayout homeNav = findViewById(R.id.home);
        LinearLayout libraryNav = findViewById(R.id.action);
        LinearLayout profileNav = findViewById(R.id.person);
        CalendarView calendarView = findViewById(R.id.calendarView);

        titleTextView = findViewById(R.id.title); // 找到用于显示标题的 TextView
        contentTextView = findViewById(R.id.textview); // 找到用于显示内容的 TextView

        // 接收来自 diary_new 页面的标题和内容
        Intent intent = getIntent();
        if (intent != null) {
            String receivedTitle = intent.getStringExtra("title");
            String receivedContent = intent.getStringExtra("content");

            // 将接收到的标题和内容显示在相应的 TextView 控件中
            titleTextView.setText(receivedTitle);
            contentTextView.setText(receivedContent);
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 在这里处理用户选择日期的操作
                // year, month, 和 dayOfMonth 分别表示用户选择的年、月、和日

                // 创建一个 Intent 来打开新页面
                Intent intent = new Intent(Activity_diary.this, diary_new.class);

                // 传递选定的日期信息到新页面
                intent.putExtra("selected_year", year);
                intent.putExtra("selected_month", month);
                intent.putExtra("selected_day", dayOfMonth);

                // 启动新页面
                startActivity(intent);
            }
        });


        homeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "主页" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_diary.this, MainActivity.class);
                startActivity(intent);
            }
        });

        libraryNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "动作库" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_diary.this, Activity_action.class);
                startActivity(intent);
            }
        });

        profileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "个人" 导航项，启动相应的 Activity
                Intent intent = new Intent(Activity_diary.this, Activity_person.class);
                startActivity(intent);
            }
        });

    }
}
