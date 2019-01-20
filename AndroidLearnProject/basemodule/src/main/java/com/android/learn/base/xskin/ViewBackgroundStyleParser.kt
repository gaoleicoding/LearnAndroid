package com.android.learn.base.xskin

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View

import com.wind.me.xskinloader.SkinResDeployerFactory
import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.parser.SkinAttributeParser
import com.wind.me.xskinloader.skinInterface.ISkinStyleParser
import com.wind.me.xskinloader.util.ReflectUtils

/**
 * 解析Xml中的style属性，使支持style中定义的View的background支持换肤
 * Created by Windy on 2018/1/23.
 */
class ViewBackgroundStyleParser : ISkinStyleParser {

    override fun parseXmlStyle(view: View, attrs: AttributeSet, viewAttrs: MutableMap<String, SkinAttr>, specifiedAttrList: Array<String>) {
        val context = view.context
        val viewStyleable = textViewStyleableList
        val viewStyleableBackground = textViewTextColorStyleableIndex

        val a = context.obtainStyledAttributes(attrs, viewStyleable, 0, 0)
        if (a != null) {
            val n = a.indexCount
            for (j in 0 until n) {
                val attr = a.getIndex(j)
                if (attr == viewStyleableBackground && SkinConfig.isCurrentAttrSpecified(SkinResDeployerFactory.BACKGROUND, specifiedAttrList)) {
                    val drawableResId = a.getResourceId(attr, -1)
                    val skinAttr = SkinAttributeParser.parseSkinAttr(context, SkinResDeployerFactory.BACKGROUND, drawableResId)
                    if (skinAttr != null) {
                        viewAttrs[skinAttr.attrName] = skinAttr
                    }
                }
            }
            a.recycle()
        }
    }

    companion object {

        private var sViewStyleList: IntArray? = null
        private var sViewBackgroundStyleIndex: Int = 0

        private val textViewStyleableList: IntArray?
            get() {
                if (sViewStyleList == null || sViewStyleList!!.size == 0) {
                    sViewStyleList = ReflectUtils.getField("com.android.internal.R\$styleable", "View") as IntArray
                }
                return sViewStyleList
            }

        private val textViewTextColorStyleableIndex: Int
            get() {
                if (sViewBackgroundStyleIndex == 0) {
                    val o = ReflectUtils.getField("com.android.internal.R\$styleable", "View_background")
                    if (o != null) {
                        sViewBackgroundStyleIndex = o as Int
                    }
                }
                return sViewBackgroundStyleIndex
            }
    }
}
