package com.jara.retrofit_rxjava_okhttp_demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by jara on 2017-6-22.
 */

public class MyApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }
}
