package com.android.base.application

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.support.multidex.MultiDex
import android.view.LayoutInflater

import com.android.base.utils.SPUtils
import com.android.base.xskin.ExtraAttrRegister
import com.iflytek.cloud.SpeechUtility
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.umeng.commonsdk.UMConfigure
import com.wind.me.xskinloader.SkinInflaterFactory
import com.wind.me.xskinloader.SkinManager

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper


class CustomApplication : Application() {

    /**
     * @return 获取字体缩放比例
     */
    val fontScale: Float
        get() {
            val currentIndex = SPUtils.getParam(this, "currentIndex", 1) as Int
            return 1 + currentIndex * 0.1f
        }

    override fun onCreate() {
        super.onCreate()
        connectivityManager = applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        context = this
        instance = this
        // LeakCanary.install(this);
        // BlockCanary.install(this, new AppContext()).start();

        //初始化友盟
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "")
        //初始化换肤
        SkinInflaterFactory.setFactory(LayoutInflater.from(this))  // for skin change
        SkinManager.get().init(this)
        //扩展换肤属性和style中的换肤属性
        ExtraAttrRegister.init()
        //初始化讯飞语言识别
        SpeechUtility.createUtility(this, "appid=" + "5c22ed2f")
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        //
        BGASwipeBackHelper.init(this, null)
        MultiDex.install(this)

//       CaocConfig.Builder.create()
//        .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
//        .enabled(false) //default: true
//        .showErrorDetails(true) //default: true
//        .showRestartButton(true) //default: true
//        .logErrorOnRestart(true) //default: true
//        .trackActivities(true) //default: false
//        .minTimeBetweenCrashesMs(2000) //default: 3000
//        .errorDrawable(R.drawable.customactivityoncrash_error_image) //default: bug image
//        .restartActivity(YourCustomActivity.class) //default: null (your app's launch activity)
//        .errorActivity(YourCustomErrorActivity.class) //default: null (default error activity)
//        .eventListener(new YourCustomEventListener()) //default: null
//        .apply()

    }


    companion object {
        lateinit var connectivityManager: ConnectivityManager
        lateinit var context: Context
        lateinit var instance: CustomApplication

        //static 代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                // layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                val header = ClassicsHeader(context)//指定为经典Header，默认是 贝塞尔雷达Header
                header.setTextSizeTitle(14f)//设置标题文字大小（sp单位）
                header//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                val footer = ClassicsFooter(context) //指定为经典Footer，默认是 BallPulseFooter
                footer.setTextSizeTitle(14f)//设置标题文字大小（sp单位）

                footer
            }
        }
    }


}
