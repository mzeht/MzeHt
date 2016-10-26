package com.wpmac.mzeht;

import android.app.Application;

import com.wpmac.mzeht.retrofit.RetrofitUtils;

/**
 * Created by wpmac on 16/9/10.
 */
public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化retrofitUtils
        RetrofitUtils.getInstance().initOkHttp(this);
    }
}
