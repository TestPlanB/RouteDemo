package com.example.myapplication;

import android.app.Application;

import com.example.route.Router;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);

    }
}
