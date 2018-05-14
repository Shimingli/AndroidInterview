package com.android.interview;

import android.app.Application;
import android.content.Context;

/**
 * author： Created by shiming on 2018/5/14 17:51
 * mailbox：lamshiming@sina.com
 */

public class MyApplication extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this.getApplicationContext();
    }
    public static Context getContext(){
        return mApplicationContext;
    }
}
