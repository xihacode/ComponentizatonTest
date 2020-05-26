package com.liukun.arouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import dalvik.system.DexFile;

/**
 * Author: liukun on 2020/5/26.
 * Mail  : 3266817262@qq.com
 * Description:
 */
public class ARouter {

    private static ARouter mARouter = new ARouter();
    private Map<String, Class<? extends Activity>> mMap;
    private Context mContext;

    private ARouter() {
        mMap = new HashMap<>();
    }

    public void init(Context context) {
        mContext = context;
        List<String> classNames = getClassName("com.liukun.util");
        for (String name:classNames){
            try {
                Class<?> clazz = Class.forName(name);
                if (IRouter.class.isAssignableFrom(clazz)){
                    IRouter iRouter = (IRouter) clazz.newInstance();
                    iRouter.putActivity();
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public static ARouter getInstance() {
        return mARouter;
    }

    public void addActivity(String key, Class<? extends Activity> clazz) {
        if (key != null && clazz != null && !mMap.containsKey(key)) {
            mMap.put(key, clazz);

        }
    }

    public void jumpActivity(String key, Bundle bundle) {
        Class<? extends Activity> activity = mMap.get(key);
        if (activity != null) {
            Intent intent = new Intent(mContext, activity);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    /**
     * 通过包名获取类名
     * @param packageName
     * @return
     */

    private List<String> getClassName(String packageName){
        List<String> classList =  new ArrayList<>();
        String path = null;

        try {
            path = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(),0).sourceDir;
            //根据apk的完整路径得到编译后的dex的文件目录
            DexFile dexFile = new DexFile(path);
            //获取dex文件中的所有class
            Enumeration enumeration = dexFile.entries();
            while (enumeration.hasMoreElements()){
                String name = (String) enumeration.nextElement();
                if (name.contains(packageName)){
                    classList.add(name);
                }
            }
        } catch (PackageManager.NameNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return classList;
    }

}
