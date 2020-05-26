package com.liukun.login;

import com.liukun.arouter.ARouter;
import com.liukun.arouter.IRouter;

/**
 * Author: liukun on 2020/5/26.
 * Mail  : 3266817262@qq.com
 * Description:
 */
public class ActivityUtil implements IRouter
{
    @Override
    public void putActivity() {
        ARouter.getInstance().addActivity("",LoginActivity.class);
    }
}
