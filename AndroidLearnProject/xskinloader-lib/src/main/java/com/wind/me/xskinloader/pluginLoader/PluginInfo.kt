package com.wind.me.xskinloader.pluginLoader

import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.Resources

class PluginInfo {

    var filePath: String? = null
    @Transient
    var classLoader: ClassLoader? = null
    @Transient
    var resources: Resources? = null
    @Transient
    var packageInfo: PackageInfo? = null
    @Transient
    private var theme: Resources.Theme? = null

    val packageName: String?
        get() = if (packageInfo == null) {
            null
        } else packageInfo!!.packageName

    constructor(localPath: String, pluginClassLoader: ClassLoader, pluginRes: Resources,
                pluginTheme: Resources.Theme, packageInfo: PackageInfo) {
        this.classLoader = pluginClassLoader
        this.resources = pluginRes
        this.theme = pluginTheme
        this.filePath = localPath
        this.packageInfo = packageInfo
    }

    constructor() {}

    constructor(localPath: String) {
        this.filePath = localPath
    }

    fun setTheme(themeId: Int) {
        val theme = resources!!.newTheme()
        theme.applyStyle(themeId, true)
        setTheme(theme)
    }

    fun setTheme(theme: Resources.Theme) {
        this.theme = theme
    }

    fun getTheme(): Resources.Theme? {
        return theme
    }


    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + if (filePath == null) 0 else filePath!!.hashCode()
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj)
            return true
        if (obj == null)
            return false
        if (javaClass != obj.javaClass)
            return false
        val other = obj as PluginInfo?
        if (filePath == null) {
            if (other!!.filePath != null)
                return false
        } else if (filePath != other!!.filePath)
            return false
        return true
    }

    override fun toString(): String {
        return (super.toString() + "[ filePath=" + filePath + ", pkg=" + packageName
                + " ]")
    }

}