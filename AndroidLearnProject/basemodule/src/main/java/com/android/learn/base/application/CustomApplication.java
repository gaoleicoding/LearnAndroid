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

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


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
        //初始化讯飞语言识别
        SpeechUtility.createUtility(this, "appid="+ "5c22ed2f");
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);

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
