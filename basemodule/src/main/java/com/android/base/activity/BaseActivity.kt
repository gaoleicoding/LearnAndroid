package com.android.base.activity

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.android.base.R
import com.android.base.application.CustomApplication
import com.android.base.utils.LogUtil
import com.android.base.utils.SPUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.analytics.MobclickAgent
import com.wind.me.xskinloader.SkinInflaterFactory
import com.wind.me.xskinloader.SkinManager
import com.wind.me.xskinloader.util.AssetFileUtils
import java.io.File


/**
 * Created by gaolei on 2018/4/26.
 */

abstract class BaseActivity : BasePermisssionActivity(), View.OnClickListener, BGASwipeBackHelper.Delegate {

    internal var isNightMode: Boolean? = null

    internal var TAG = "BaseActivity"
    lateinit var mSwipeBackHelper: BGASwipeBackHelper


    open val layoutId = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish()
        super.onCreate(savedInstanceState)

        SkinInflaterFactory.setFactory(this)
        setContentView(layoutId)
        ButterKnife.bind(this)
        setStatusBar()

        val bundle = intent.extras

        initData(bundle)

    }

    open fun initData(bundle: Bundle?) {}

    override fun onClick(v: View) {
        if (v.id == R.id.iv_back) {
            finish()
        }
    }


    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
        //        context = this;
        LogUtil.d(TAG, "BaseActivity ----onResume：" + javaClass.name.toString())
    }


    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "BaseActivity ----onDestroy：" + javaClass.name.toString())
    }

    fun useNightMode(isNight: Boolean) {

        if (isNight) {
            changeSkin()

        } else {

            restoreDefaultSkin()
        }
    }

    //重写字体缩放比例  api>25
    override fun attachBaseContext(newBase: Context) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            val res = newBase.resources
            val config = res.configuration
            config.fontScale = CustomApplication.instance.fontScale//1 设置正常字体大小的倍数
            val newContext = newBase.createConfigurationContext(config)
            super.attachBaseContext(newContext)
        } else {
            super.attachBaseContext(newBase)
        }
    }

    //重写字体缩放比例 api<25
    override fun getResources(): Resources {
        val res = super.getResources()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            val config = res.configuration
            config.fontScale = CustomApplication.instance.fontScale//1 设置正常字体大小的倍数
            res.updateConfiguration(config, res.displayMetrics)
        }
        return res
    }

    private fun changeSkin() {
        //将assets目录下的皮肤文件拷贝到data/data/.../cache目录下
        val saveDir = cacheDir.absolutePath + "/skins"
        val savefileName = "/skin1.skin"
        val asset_dir = "skins/xskinloader-skin-apk-debug.apk"
        val file = File(saveDir + File.separator + savefileName)
        //        if (!file.exists()) {
        AssetFileUtils.copyAssetFile(this, asset_dir, saveDir, savefileName)
        //        }
        SkinManager.get().loadSkin(file.absolutePath)
    }

    private fun restoreDefaultSkin() {
        SkinManager.get().restoreToDefaultSkin()
    }


    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private fun initSwipeBackFinish() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true)
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false)
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true)
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true)
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true)
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f)
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false)
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    override fun isSupportSwipeBack(): Boolean {
        return true
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    override fun onSwipeBackLayoutSlide(slideOffset: Float) {}

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    override fun onSwipeBackLayoutCancel() {}

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward()
    }

    override fun onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
    }

    protected open fun setStatusBar() {

        val isNightMode = SPUtils.getParam(this, "nightMode", false) as Boolean
        if (isNightMode) {
            StatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.app_color_night), 0)
        } else {
            StatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.app_color), 0)
        }
    }
}