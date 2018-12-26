package com.android.learn.base.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;

import com.android.learn.base.utils.LanguageUtil;
import com.android.learn.base.utils.SPUtils;
import com.android.learn.base.xskin.ExtraAttrRegister;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.wind.me.xskinloader.SkinInflaterFactory;
import com.wind.me.xskinloader.SkinManager;


public class CustomApplication extends Application {
    public static ConnectivityManager connectivityManager;
    public static Context context;
    private static CustomApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        context = this;
        instance=this;
//        LeakCanary.install(this);
//        BlockCanary.install(this, new AppContext()).start();
//        LanguageUtil.setApplicationLanguage(this);

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.onEvent(this, "enter", "CustomApplication");//前统计的事件ID
        ExtraAttrRegister.init();
        SkinInflaterFactory.setFactory(LayoutInflater.from(this));  // for skin change
        SkinManager.get().init(this);
        SpeechUtility.createUtility(this, "appid="+ "5c22ed2f");

    }
    public static CustomApplication getInstance() {
        return instance;
    }
//    public class AppContext extends BlockCanaryContext {
//        private static final String TAG = "AppContext";
//
//        @Override
//        public String provideQualifier() {
//            String qualifier = "";
//            try {
//                PackageInfo info = getApplicationContext().getPackageManager()
//                        .getPackageInfo(getApplicationContext().getPackageName(), 0);
//                qualifier += info.versionCode + "_" + info.versionName + "_YYB";
//            } catch (PackageManager.NameNotFoundException e) {
//                Log.e(TAG, "provideQualifier exception", e);
//            }
//            return qualifier;
//        }
//
//        @Override
//        public int provideBlockThreshold() {
//            return 1000;
//        }
//
//        @Override
//        public boolean displayNotification() {
//            return BuildConfig.DEBUG;
//        }
//
//        @Override
//        public boolean stopWhenDebugging() {
//            return false;
//        }
//    }

    /**
     * @return 获取字体缩放比例
     */
    public  float getFontScale() {
        int currentIndex = (Integer) SPUtils.getParam(this, "currentIndex", 1);
        return 1 + currentIndex * 0.1f;
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        //保存系统选择语言
//        LanguageUtil.saveSystemCurrentLanguage(base);
//        super.attachBaseContext(LanguageUtil.setLocal(base));
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        //保存系统选择语言
//        LanguageUtil.onConfigurationChanged(getApplicationContext());
//    }

}
