package com.wind.me.xskinloader.entity

import android.content.Context
import android.content.SharedPreferences

object SkinConfig {
    /***
     * 支持的命名空间
     */
    val SKIN_XML_NAMESPACE = "http://schemas.android.com/android/skin"

    /**界面元素支持换肤的属性 */
    val ATTR_SKIN_ENABLE = "enable"
    val SUPPORTED_ATTR_SKIN_LIST = "attrs"

    val SKIN_APK_SUFFIX = ".skin"
    val PREF_CUSTOM_SKIN_PATH = "music_skin_custom_path"

    val DEBUG = false


    /**
     * 属性值对应的类型是color
     */
    val RES_TYPE_NAME_COLOR = "color"

    /**
     * 属性值对应的类型是drawable
     */
    val RES_TYPE_NAME_DRAWABLE = "drawable"

    val PREFERENCE_NAME = "music_skin_pref"


    fun getCustomSkinPath(context: Context): String? {
        return getString(context, PREF_CUSTOM_SKIN_PATH, null)
    }

    fun saveSkinPath(context: Context, path: String) {
        putString(context, PREF_CUSTOM_SKIN_PATH, path)
    }

    fun isDefaultSkin(context: Context): Boolean {
        return getCustomSkinPath(context) == null
    }

    fun putString(context: Context, key: String, value: String): Boolean {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun getString(context: Context, key: String, defaultValue: String?): String? {
        val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return settings.getString(key, defaultValue)
    }

    fun isCurrentAttrSpecified(attr: String, attrList: Array<String>?): Boolean {
        if (attrList == null || attrList.size == 0) {
            return true
        }
        for (a in attrList) {
            if (a != null && a.trim { it <= ' ' } == attr) {
                return true
            }
        }
        return false
    }
}
