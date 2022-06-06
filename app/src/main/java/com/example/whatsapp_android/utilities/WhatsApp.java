package com.example.whatsapp_android.utilities;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class WhatsApp extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
