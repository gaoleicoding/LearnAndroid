package com.wind.me.xskinloader.parser

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar

import com.wind.me.xskinloader.SkinResDeployerFactory
import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.skinInterface.ISkinStyleParser
import com.wind.me.xskinloader.util.ReflectUtils

/**
 * 解析Xml中的style属性，使支持style中定义的ProgressBar的indeterminateDrawable属性支持换肤
 * Created by Windy on 2018/1/23.
 */

class ProgressBarIndeterminateDrawableStyleParser : ISkinStyleParser {

    override fun parseXmlStyle(view: View, attrs: AttributeSet, viewAttrs: MutableMap<String, SkinAttr>, specifiedAttrList: Array<String>) {
        if (!ProgressBar::class.java!!.isAssignableFrom(view.javaClass)) {
            return
        }
        val context = view.context
        val progressBarStyleList = progressBarStyleable
        val progressBarIndeterminateDrawableIndex = progressBarIndeterminateDrawableIndex

        val a = context.obtainStyledAttributes(attrs, progressBarStyleList, 0, 0)

        if (a != null) {
            val n = a.indexCount
            for (j in 0 until n) {
                val attr = a.getIndex(j)
                if (attr == progressBarIndeterminateDrawableIndex && SkinConfig.isCurrentAttrSpecified(SkinResDeployerFactory.PROGRESSBAR_INDETERMINATE_DRAWABLE, specifiedAttrList)) {
                    val drawableResId = a.getResourceId(attr, -1)
                    val skinAttr = SkinAttributeParser.parseSkinAttr(context, SkinResDeployerFactory.PROGRESSBAR_INDETERMINATE_DRAWABLE, drawableResId)
                    if (skinAttr != null) {
                        viewAttrs[skinAttr.attrName] = skinAttr
                    }
                }
            }
            a.recycle()
        }
    }

    companion object {

        private var sProgressBarStyleList: IntArray? = null
        private var sProgressBarIndeterminateDrawableIndex: Int = 0

        private val progressBarStyleable: IntArray?
            get() {
                if (sProgressBarStyleList == null || sProgressBarStyleList!!.size == 0) {
                    sProgressBarStyleList = ReflectUtils.getField("com.android.internal.R\$styleable", "ProgressBar") as IntArray
                }
                return sProgressBarStyleList
            }

        private val progressBarIndeterminateDrawableIndex: Int
            get() {
                if (sProgressBarIndeterminateDrawableIndex == 0) {
                    val o = ReflectUtils.getField("com.android.internal.R\$styleable", "ProgressBar_indeterminateDrawable")
                    if (o != null) {
                        sProgressBarIndeterminateDrawableIndex = o as Int
                    }
                }
                return sProgressBarIndeterminateDrawableIndex
            }
    }
}
