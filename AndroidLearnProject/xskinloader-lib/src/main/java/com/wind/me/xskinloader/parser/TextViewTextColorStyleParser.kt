package com.wind.me.xskinloader.parser

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.SkinResDeployerFactory
import com.wind.me.xskinloader.skinInterface.ISkinStyleParser
import com.wind.me.xskinloader.util.ReflectUtils

/**
 * 解析Xml中的style属性，使支持style中定义的TextView的textColor支持换肤
 * Created by Windy on 2018/1/23.
 */

class TextViewTextColorStyleParser : ISkinStyleParser {

    override fun parseXmlStyle(view: View, attrs: AttributeSet, viewAttrs: MutableMap<String, SkinAttr>, specifiedAttrList: Array<String>) {
        if (!TextView::class.java!!.isAssignableFrom(view.javaClass)) {
            return
        }
        val context = view.context
        val textViewStyleable = textViewStyleableList
        val textViewStyleableTextColor = textViewTextColorStyleableIndex

        val a = context.obtainStyledAttributes(attrs, textViewStyleable, 0, 0)
        if (a != null) {
            val n = a.indexCount
            for (j in 0 until n) {
                val attr = a.getIndex(j)
                if (attr == textViewStyleableTextColor && SkinConfig.isCurrentAttrSpecified(SkinResDeployerFactory.TEXT_COLOR, specifiedAttrList)) {
                    val colorResId = a.getResourceId(attr, -1)
                    val skinAttr = SkinAttributeParser.parseSkinAttr(context, SkinResDeployerFactory.TEXT_COLOR, colorResId)
                    if (skinAttr != null) {
                        viewAttrs[skinAttr.attrName] = skinAttr
                    }
                }
            }
            a.recycle()
        }
    }

    companion object {

        private var sTextViewStyleList: IntArray? = null
        private var sTextViewTextColorStyleIndex: Int = 0

        private val textViewStyleableList: IntArray?
            get() {
                if (sTextViewStyleList == null || sTextViewStyleList!!.size == 0) {
                    sTextViewStyleList = ReflectUtils.getField("com.android.internal.R\$styleable", "TextView") as IntArray
                }
                return sTextViewStyleList
            }

        private val textViewTextColorStyleableIndex: Int
            get() {
                if (sTextViewTextColorStyleIndex == 0) {
                    val o = ReflectUtils.getField("com.android.internal.R\$styleable", "TextView_textColor")
                    if (o != null) {
                        sTextViewTextColorStyleIndex = o as Int
                    }
                }
                return sTextViewTextColorStyleIndex
            }
    }
}
