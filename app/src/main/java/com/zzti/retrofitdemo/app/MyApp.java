package com.zzti.retrofitdemo.app;

import android.app.Application;

/**
 * @author fengyonggge
 * @date 2017/2/7
 */
public class MyApp extends Application{
    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApp getInstance() {
        return mInstance;
    }
}
