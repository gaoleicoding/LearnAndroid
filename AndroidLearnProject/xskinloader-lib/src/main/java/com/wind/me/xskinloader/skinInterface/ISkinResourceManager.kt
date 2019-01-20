package com.wind.me.xskinloader.skinInterface

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes

/**
 * Created by xiawanli on 2018/1/10.
 *
 * 换肤功能，替换资源管理接口
 */

interface ISkinResourceManager {

    val pkgName: String

    val pluginResource: Resources

    fun setPluginResourcesAndPkgName(resources: Resources, pkgName: String)

    @Throws(Resources.NotFoundException::class)
    fun getColor(@ColorRes resId: Int): Int

    @Throws(Resources.NotFoundException::class)
    fun getColorStateList(@ColorRes resId: Int): ColorStateList

    @Throws(Resources.NotFoundException::class)
    fun getDrawable(@DrawableRes resId: Int): Drawable

}
