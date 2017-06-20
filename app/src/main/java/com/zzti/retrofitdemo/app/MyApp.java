package com.zzti.retrofitdemo.app;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

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

        Logger.init("fyg").logLevel(LogLevel.FULL) ;

    }

    public static MyApp getInstance() {
        return mInstance;
    }
}
