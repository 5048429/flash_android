package com.google.mlkit.vision.demo.java;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.ai.BmobAI;

public class BmobApp extends Application {
    public static BmobAI bmobAI;
    @Override
    public void onCreate(){
        super.onCreate();
        Bmob.initialize(this, "1fbb5b590cd6be5883cb6899c40479ff");
        bmobAI = new BmobAI();
    }
}
