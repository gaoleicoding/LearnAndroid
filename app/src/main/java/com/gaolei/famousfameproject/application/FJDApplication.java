package com.gaolei.famousfameproject.application;

import android.app.Application;
import android.content.Context;


/**
 * @aythor: lilei
 * time: 2017/8/15  下午2:45
 * function:
 */

public class FJDApplication extends Application {

    private static Context sContext;
    private static FJDApplication sApplication;


    /**
     * 单利获取application
     *
     * @return
     */
    public static FJDApplication getZMApplication() {
        if (sApplication == null) {
            sApplication = new FJDApplication();
        }
        return sApplication;
    }

    /**
     * 全局context
     *
     * @return
     */
    public static Context getZMContext() {
        if (sContext == null) {
            sContext = sApplication.getApplicationContext();
        }
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sContext = getApplicationContext();


    }

}
