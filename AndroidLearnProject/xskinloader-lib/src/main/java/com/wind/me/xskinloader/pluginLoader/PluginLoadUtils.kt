package com.wind.me.xskinloader.pluginLoader

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log

import java.lang.reflect.Method
import java.util.HashMap

import dalvik.system.DexClassLoader

class PluginLoadUtils private constructor(context: Context) {

    private val mContext: Context
    private val mPluginInfoHolder = HashMap<String, PluginInfo>()

    init {
        mContext = context.applicationContext
    }

    fun install(apkPath: String): PluginInfo {
        var pluginInfo: PluginInfo? = mPluginInfoHolder[apkPath]
        if (pluginInfo != null) {
            return pluginInfo
        }

        val dexClassLoader = createDexClassLoader(apkPath)
        val assetManager = createAssetManager(apkPath)
        val resources = createResources(assetManager)
        var theme: Resources.Theme? = null
        if (resources != null) {
            theme = resources.newTheme()
            //            theme.applyStyle(R.style.AppTheme, false);
        }
        val packageInfo = createPackageInfo(apkPath)

        pluginInfo = PluginInfo(apkPath, dexClassLoader, resources, theme, packageInfo)
        mPluginInfoHolder[apkPath] = pluginInfo
        return pluginInfo
    }

    fun getClassLoader(apkPath: String): DexClassLoader {
        val pluginInfo = mPluginInfoHolder[apkPath]
        if (pluginInfo != null) {
            val loader = pluginInfo.classLoader
            if (loader != null) {
                return loader as DexClassLoader
            }
        }
        return createDexClassLoader(apkPath)
    }

    fun getPackageInfo(apkPath: String): PackageInfo? {
        val pluginInfo = mPluginInfoHolder[apkPath]
        if (pluginInfo != null) {
            val info = pluginInfo.packageInfo
            if (info != null) {
                return info
            }
        }
        return createPackageInfo(apkPath)
    }

    fun getPluginResources(apkPath: String): Resources? {
        val pluginInfo = mPluginInfoHolder[apkPath]
        if (pluginInfo != null) {
            val res = pluginInfo.resources
            if (res != null) {
                return res
            }
        }
        return createResources(apkPath)
    }

    fun getPluginAssets(apkPath: String): AssetManager? {
        val pluginInfo = mPluginInfoHolder[apkPath]
        if (pluginInfo != null) {
            val resources = pluginInfo.resources
            if (resources != null) {
                val assetManager = resources.assets
                if (assetManager != null) {
                    return assetManager
                }
            }
        }
        return createAssetManager(apkPath)
    }

    /**
     * 创建插件classloader
     */
    private fun createDexClassLoader(dexPath: String): DexClassLoader {
        return DexClassLoader(dexPath,
                mContext.getDir("dex", Context.MODE_PRIVATE).absolutePath, null,
                mContext.classLoader)
    }

    /**
     * 创建AssetManager对象
     */
    private fun createAssetManager(dexPath: String): AssetManager? {
        try {
            val assetManager = AssetManager::class.java!!.newInstance()
            val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(assetManager, dexPath)
            return assetManager
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 创建Resource对象
     */
    private fun createResources(assetManager: AssetManager?): Resources? {
        if (assetManager == null) {
            Log.e(TAG, " create Resources failed assetManager is NULL !! ")
            return null
        }
        val superRes = mContext.resources
        return Resources(assetManager, superRes.displayMetrics, superRes.configuration)
    }

    private fun createResources(dexPath: String): Resources? {
        val assetManager = createAssetManager(dexPath)
        return if (assetManager != null) {
            createResources(assetManager)
        } else null
    }

    private fun createPackageInfo(apkFilepath: String): PackageInfo? {
        val pm = mContext.packageManager
        var pkgInfo: PackageInfo? = null
        try {
            pkgInfo = pm.getPackageArchiveInfo(apkFilepath,
                    PackageManager.GET_ACTIVITIES or
                            PackageManager.GET_SERVICES or
                            PackageManager.GET_META_DATA)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return pkgInfo
    }

    private fun loadClassByClassLoader(classLoader: ClassLoader, className: String): Class<*>? {
        var clazz: Class<*>? = null
        try {
            clazz = classLoader.loadClass(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return clazz
    }

    companion object {
        var instance: PluginLoadUtils? = null
            private set
        private val TAG = "PluginLoadUtils"

        fun getInstance(context: Context): PluginLoadUtils {
            if (instance == null) {
                synchronized(PluginLoadUtils::class.java) {
                    if (instance == null) {
                        instance = PluginLoadUtils(context)
                    }
                }
            }
            return instance
        }
    }
}
