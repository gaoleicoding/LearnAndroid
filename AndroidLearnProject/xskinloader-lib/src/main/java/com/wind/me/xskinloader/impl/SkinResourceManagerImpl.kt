package com.wind.me.xskinloader.impl

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log

import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager


/**
 * Created by Windy on 2018/1/10.
 */

class SkinResourceManagerImpl(context: Context, pkgName: String, resources: Resources) : ISkinResourceManager {

    private val mDefaultResources: Resources
    override var pkgName: String? = null
        private set
    override var pluginResource: Resources? = null
        private set

    init {
        mDefaultResources = context.resources
        this.pkgName = pkgName
        pluginResource = resources
    }

    override fun setPluginResourcesAndPkgName(resources: Resources, pkgName: String) {
        pluginResource = resources
        this.pkgName = pkgName
    }

    @Throws(Resources.NotFoundException::class)
    override fun getColor(resId: Int): Int {
        val originColor = mDefaultResources.getColor(resId)
        if (pluginResource == null) {
            return originColor
        }

        val resName = mDefaultResources.getResourceEntryName(resId)

        val trueResId = pluginResource!!.getIdentifier(resName, SkinConfig.RES_TYPE_NAME_COLOR, pkgName)
        var trueColor = 0

        try {
            trueColor = pluginResource!!.getColor(trueResId)
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
            trueColor = originColor
        }

        return trueColor
    }

    @Throws(Resources.NotFoundException::class)
    override fun getColorStateList(resId: Int): ColorStateList {
        var isExtendSkin = true

        if (pluginResource == null) {
            isExtendSkin = false
        }

        val resName = mDefaultResources.getResourceEntryName(resId)
        if (isExtendSkin) {
            val trueResId = pluginResource!!.getIdentifier(resName, SkinConfig.RES_TYPE_NAME_COLOR, pkgName)
            var trueColorList: ColorStateList? = null
            if (trueResId == 0) { // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList
                try {
                    return mDefaultResources.getColorStateList(resId)
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                    if (SkinConfig.DEBUG) {
                        Log.d(TAG, "resName = " + resName + " NotFoundException : " + e.message)
                    }
                }

            } else {
                try {
                    trueColorList = pluginResource!!.getColorStateList(trueResId)
                    if (SkinConfig.DEBUG) {
                        Log.d(TAG, "getColorStateList the trueColorList is = " + trueColorList!!)
                    }
                    return trueColorList
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                    Log.e(TAG, "resName = " + resName + " NotFoundException :" + e.message)
                }

            }
        } else {
            try {
                return mDefaultResources.getColorStateList(resId)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                Log.e(TAG, "resName = " + resName + " NotFoundException :" + e.message)
            }

        }


        val states = Array(1) { IntArray(1) }
        return ColorStateList(states, intArrayOf(mDefaultResources.getColor(resId)))
    }

    @Throws(Resources.NotFoundException::class)
    override fun getDrawable(resId: Int): Drawable {
        val originDrawable = mDefaultResources.getDrawable(resId)
        if (pluginResource == null) {
            return originDrawable
        }
        val resName = mDefaultResources.getResourceEntryName(resId)

        val trueResId = pluginResource!!.getIdentifier(resName, SkinConfig.RES_TYPE_NAME_DRAWABLE, pkgName)

        var trueDrawable: Drawable
        try {
            if (android.os.Build.VERSION.SDK_INT < 22) {
                trueDrawable = pluginResource!!.getDrawable(trueResId)
            } else {
                trueDrawable = pluginResource!!.getDrawable(trueResId, null)
            }
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
            trueDrawable = originDrawable
        }

        return trueDrawable
    }

    companion object {

        private val TAG = "SkinResourceManagerImpl"
    }
}
