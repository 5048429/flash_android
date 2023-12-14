package com.google.mlkit.vision.demo.java;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.demo.R;

public class ShowFoodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food);

        getSupportActionBar().hide();
        // 获取传递过来的图片 ID
        Intent intent = getIntent();
        if (intent != null) {
            int imageId = intent.getIntExtra("imageId", -1);
            if (imageId != -1) {
                // 设置图片
                ImageView imageView = findViewById(R.id.imageViewFood);
                imageView.setImageResource(getImageResourceById(imageId));

                // 设置介绍文本
                TextView descriptionTextView = findViewById(R.id.textViewDescription);
                descriptionTextView.setText(getImageDescriptionById(imageId));

                TextView titleTextview = findViewById(R.id.textViewTitle);
                titleTextview.setText(getImageTitleById(imageId));

                TextView WebTextview = findViewById(R.id.Website);
                WebTextview.setText(getImageWebById(imageId));
            }
        }
    }

    // 根据图片 ID 获取资源 ID
    private int getImageResourceById(int imageId) {
        TypedArray imageResources = getResources().obtainTypedArray(R.array.images);
        try {
            if (imageId >= 0 && imageId < imageResources.length()) {
                return imageResources.getResourceId(imageId, 0);
            } else {
                return R.drawable.image1_placeholder;
            }
        } finally {
            imageResources.recycle();
        }
    }

    // 根据图片 ID 获取介绍文本
    private String getImageDescriptionById(int imageId) {
        String[] imageDescriptions = getResources().getStringArray(R.array.image_descriptions);

        if (imageId >= 0 && imageId < imageDescriptions.length) {
            return imageDescriptions[imageId];
        } else {
            return "默认介绍文本";
        }
    }

    private String getImageTitleById(int imageId) {
        String[] imageDescriptions = getResources().getStringArray(R.array.image_name);

        if (imageId >= 0 && imageId < imageDescriptions.length) {
            return imageDescriptions[imageId];
        } else {
            return "默认介绍文本";
        }
    }

    private String getImageWebById(int imageId) {
        String[] imageDescriptions = getResources().getStringArray(R.array.image_web);

        if (imageId >= 0 && imageId < imageDescriptions.length) {
            return imageDescriptions[imageId];
        } else {
            return "默认介绍文本";
        }
    }
}





