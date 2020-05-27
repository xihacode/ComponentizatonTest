package com.liukun.componentizatontest;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.liukun.arouter.ARouter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Author: liukun on 2020/5/26.
 * Mail  : 3266817262@qq.com
 * Description:
 */
public class MyApplication extends Application {
    private boolean isBackGround;

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
        //监听应用的切换到前后台
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                if (isBackGround){
                    isBackGround=false;
                    Log.d("MyApplicationTAG", "onTrimMemory: "+"App is ForeGround");
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level==TRIM_MEMORY_UI_HIDDEN){
            isBackGround = true;
            Log.d("MyApplicationTAG", "onTrimMemory: "+"App is BackGround");
        }
    }
}
