package com.example.test;

import android.app.Application;

import com.example.routerapi.Router;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);
    }
}
