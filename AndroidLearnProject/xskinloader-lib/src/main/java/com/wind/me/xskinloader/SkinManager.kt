package com.wind.me.xskinloader

import android.content.Context
import android.content.pm.PackageInfo
import android.content.res.Resources
import android.support.annotation.ColorRes
import android.support.annotation.MainThread
import android.text.TextUtils
import android.view.View
import android.view.Window

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.impl.SkinResourceManagerImpl
import com.wind.me.xskinloader.parser.SkinAttributeParser
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager
import com.wind.me.xskinloader.pluginLoader.PluginLoadUtils

import java.io.File
import java.util.HashMap
import java.util.WeakHashMap

class SkinManager {
    private var mContext: Context? = null
    var currentSkinPath: String? = null
        private set
    private var mSkinResourceManager: ISkinResourceManager? = null

    //使用这个map保存所有需要换肤的view和其对应的换肤属性及资源
    //使用WeakHashMap两个作用，1.避免内存泄漏，2.避免重复的view被添加
    //使用HashMap存SkinAttr，为了避免同一个属性值存了两次
    private val mSkinAttrMap = WeakHashMap<View, HashMap<String, SkinAttr>>()

    val currentSkinPackageName: String
        get() = mSkinResourceManager!!.pkgName

    val plugintResources: Resources?
        get() = mSkinResourceManager!!.pluginResource

    val isUsingDefaultSkin: Boolean
        get() = plugintResources == null

    val skinViewMapSize: Int
        get() = mSkinAttrMap.size

    //在Application的onCreate中初始化
    @MainThread
    fun init(context: Context) {
        mContext = context.applicationContext
        mSkinResourceManager = SkinResourceManagerImpl(mContext!!, null, null)
        load()
    }

    fun restoreToDefaultSkin() {
        SkinConfig.saveSkinPath(mContext, null)
        mSkinResourceManager!!.setPluginResourcesAndPkgName(null, null)
        notifySkinChanged()
    }

    /**
     * 加载已经用户默认设置的皮肤资源
     */
    fun load() {
        val skinApkPath = SkinConfig.getCustomSkinPath(mContext)
        if (TextUtils.isEmpty(skinApkPath)) {
            restoreToDefaultSkin()
        } else {
            loadNewSkin(skinApkPath)
        }
    }

    /**
     * 加载新皮肤
     *
     * @param skinApkPath 新皮肤路径
     * @return true 加载新皮肤成功 false 加载失败
     */
    fun loadNewSkin(skinApkPath: String): Boolean {
        return doNewSkinLoad(skinApkPath)
    }

    fun setTextViewColor(view: View, resId: Int) {
        setSkinViewResource(view, SkinResDeployerFactory.TEXT_COLOR, resId)
    }

    fun setHintTextColor(view: View, resId: Int) {
        setSkinViewResource(view, SkinResDeployerFactory.TEXT_COLOR_HINT, resId)
    }

    fun setViewBackground(view: View, resId: Int) {
        setSkinViewResource(view, SkinResDeployerFactory.BACKGROUND, resId)
    }

    fun setImageDrawable(view: View, resId: Int) {
        setSkinViewResource(view, SkinResDeployerFactory.IMAGE_SRC, resId)
    }

    fun setListViewSelector(view: View, resId: Int) {
        setSkinViewResource(view, SkinResDeployerFactory.LIST_SELECTOR, resId)
    }

    fun setListViewDivider(view: View, resId: Int) {
        setSkinViewResource(view, SkinResDeployerFactory.DIVIDER, resId)
    }

    fun setWindowStatusBarColor(window: Window, @ColorRes resId: Int) {
        val decorView = window.decorView
        setSkinViewResource(decorView, SkinResDeployerFactory.ACTIVITY_STATUS_BAR_COLOR, resId)
    }

    fun setProgressBarIndeterminateDrawable(view: View, resId: Int) {
        setSkinViewResource(view, SkinResDeployerFactory.PROGRESSBAR_INDETERMINATE_DRAWABLE, resId)
    }

    /**
     * 设置可以换肤的view的属性
     *
     * @param view     设置的view
     * @param attrName 这个取值只能是 [SkinResDeployerFactory.BACKGROUND] [SkinResDeployerFactory.DIVIDER] [SkinResDeployerFactory.TEXT_COLOR]
     * [SkinResDeployerFactory.LIST_SELECTOR] [SkinResDeployerFactory.IMAGE_SRC] 等等
     * @param resId    资源id
     */
    @MainThread
    fun setSkinViewResource(view: View, attrName: String, resId: Int) {
        if (TextUtils.isEmpty(attrName)) {
            return
        }

        val attr = SkinAttributeParser.parseSkinAttr(view.context, attrName, resId)
        if (attr != null) {
            doSkinAttrsDeploying(view, attr)
            saveSkinView(view, attr)
        }
    }

    private fun doNewSkinLoad(skinApkPath: String): Boolean {
        if (TextUtils.isEmpty(skinApkPath)) {
            return false
        }

        val file = File(skinApkPath)
        if (!file.exists()) {
            return false
        }

        val packageInfo = PluginLoadUtils.getInstance(mContext).getPackageInfo(skinApkPath)
        val pluginResources = PluginLoadUtils.getInstance(mContext).getPluginResources(skinApkPath)
        if (packageInfo == null || pluginResources == null) {
            return false
        }
        val skinPackageName = packageInfo.packageName

        if (TextUtils.isEmpty(skinPackageName)) {
            return false
        }

        mSkinResourceManager!!.setPluginResourcesAndPkgName(pluginResources, skinPackageName)

        SkinConfig.saveSkinPath(mContext, skinApkPath)

        currentSkinPath = skinApkPath

        notifySkinChanged()
        return true
    }

    //将View保存到被监听的view列表中,使得在换肤时能够及时被更新
    internal fun saveSkinView(view: View?, viewAttrs: HashMap<String, SkinAttr>?) {
        if (view == null || viewAttrs == null || viewAttrs.size == 0) {
            return
        }
        val originalSkinAttr = mSkinAttrMap[view]
        if (originalSkinAttr != null && originalSkinAttr.size > 0) {
            originalSkinAttr.putAll(viewAttrs)
            mSkinAttrMap[view] = originalSkinAttr
        } else {
            mSkinAttrMap[view] = viewAttrs
        }
    }

    private fun saveSkinView(view: View?, viewAttr: SkinAttr?) {
        if (view == null || viewAttr == null) {
            return
        }
        val viewAttrs = HashMap<String, SkinAttr>()
        viewAttrs[viewAttr.attrName] = viewAttr
        saveSkinView(view, viewAttrs)
    }

    fun removeObservableView(view: View) {
        mSkinAttrMap.remove(view)
    }

    fun clear() {
        mSkinAttrMap.clear()
    }

    //更换皮肤时，通知view更换资源
    private fun notifySkinChanged() {
        var view: View?
        var viewAttrs: HashMap<String, SkinAttr>
        val iter = mSkinAttrMap.entries.iterator()
        while (iter.hasNext()) {
            val entry = iter.next() as Entry<*, *>
            view = entry.key
            viewAttrs = entry.value
            if (view != null) {
                deployViewSkinAttrs(view, viewAttrs)
            }
        }
    }

    internal fun deployViewSkinAttrs(view: View?, viewAttrs: HashMap<String, SkinAttr>?) {
        if (view == null || viewAttrs == null || viewAttrs.size == 0) {
            return
        }
        val iter = viewAttrs.entries.iterator()
        while (iter.hasNext()) {
            val entry = iter.next() as Entry<*, *>
            val attr = entry.value as SkinAttr
            doSkinAttrsDeploying(view, attr)
        }
    }

    //将新皮肤的属性部署到view上
    private fun doSkinAttrsDeploying(view: View?, skinAttr: SkinAttr?) {
        val deployer = SkinResDeployerFactory.of(skinAttr)
        deployer?.deploy(view, skinAttr, mSkinResourceManager)
    }

    companion object {

        private val TAG = SkinManager::class.java!!.getSimpleName()
        private var sInstance: SkinManager? = null

        @MainThread
        fun get(): SkinManager {
            if (sInstance == null) {
                sInstance = SkinManager()
            }
            return sInstance
        }
    }

}