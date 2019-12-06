package com.zoe.diary.app;

import android.app.Application;


/**
 * author zoe
 * created 2019/12/5 14:33
 */

public class DiaryApplication extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }

}
