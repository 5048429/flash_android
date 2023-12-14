package com.google.mlkit.vision.demo.java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.mlkit.vision.demo.R;

public class diary_new extends Activity {

    private EditText editText;
    private EditText title; // 声明 title EditText

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_new);
        editText = findViewById(R.id.editText);
        title = findViewById(R.id.title); // 初始化 title EditText
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的标题
                String enteredTitle = title.getText().toString(); // 使用 title EditText

                EditText contentEditText = findViewById(R.id.editText);
                String enteredContent = contentEditText.getText().toString();

                Intent intent = new Intent(diary_new.this, Activity_diary.class);
                intent.putExtra("title", enteredTitle);
                intent.putExtra("content", enteredContent);
                startActivity(intent);
            }
        });
    }
}
