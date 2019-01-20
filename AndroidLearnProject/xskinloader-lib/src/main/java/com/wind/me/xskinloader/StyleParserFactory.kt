package com.wind.me.xskinloader

import android.util.AttributeSet
import android.view.View

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.parser.ProgressBarIndeterminateDrawableStyleParser
import com.wind.me.xskinloader.parser.TextViewTextColorStyleParser
import com.wind.me.xskinloader.skinInterface.ISkinStyleParser

import java.util.ArrayList

/**
 * Created by Windy on 2018/2/10.
 */

object StyleParserFactory {
    private val sStyleParserArray = ArrayList<ISkinStyleParser>()

    init {
        addStyleParser(TextViewTextColorStyleParser())
        addStyleParser(ProgressBarIndeterminateDrawableStyleParser())
    }

    fun addStyleParser(parser: ISkinStyleParser) {
        if (!sStyleParserArray.contains(parser)) {
            sStyleParserArray.add(parser)
        }
    }

    fun parseStyle(view: View, attrs: AttributeSet, viewAttrs: Map<String, SkinAttr>, specifiedAttrList: Array<String>) {
        for (parser in sStyleParserArray) {
            parser.parseXmlStyle(view, attrs, viewAttrs, specifiedAttrList)
        }
    }

}
