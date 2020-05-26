package com.liukun.componentizatontest;

import android.app.Application;

import com.liukun.arouter.ARouter;

/**
 * Author: liukun on 2020/5/26.
 * Mail  : 3266817262@qq.com
 * Description:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
