package com.google.mlkit.vision.demo.java;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int savedNightMode = getSavedNightModeState();
        AppCompatDelegate.setDefaultNightMode(savedNightMode);
    }

    protected void saveNightModeState(int nightMode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("night_mode", nightMode);
        editor.apply();
    }

    protected int getSavedNightModeState() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // 如果未找到键，则默认为 MODE_NIGHT_FOLLOW_SYSTEM
        return preferences.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
