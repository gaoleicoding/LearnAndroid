package com.wind.me.xskinloader.parser

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import com.wind.me.xskinloader.StyleParserFactory
import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.SkinResDeployerFactory

import java.util.HashMap


/**
 * 解析xml中的换肤属性
 * Created by Windy on 2018/1/11.
 */

object SkinAttributeParser {

    private val TAG = SkinAttributeParser::class.java!!.getSimpleName()

    fun parseSkinAttr(attrs: AttributeSet, view: View?, specifiedAttrList: Array<String>): HashMap<String, SkinAttr>? {
        if (view == null) {
            return null
        }
        //使用hashmap避免属性重复添加
        val viewAttrs = HashMap<String, SkinAttr>()
        val context = view.context

        //先处理style类型, 避免布局中定义的属性被style中定义的属性覆盖
        for (i in 0 until attrs.attributeCount) {
            val attrName = attrs.getAttributeName(i)
            //处理控件中设置的style属性
            if ("style" == attrName) {
                StyleParserFactory.parseStyle(view, attrs, viewAttrs, specifiedAttrList)
            }
        }

        for (i in 0 until attrs.attributeCount) {
            val attrName = attrs.getAttributeName(i)
            val attrValue = attrs.getAttributeValue(i)
            if (SkinConfig.DEBUG) {
                Log.d(TAG, " parseSkinAttr attrName=" + attrName + " attrValue=" + attrValue + " view=" + view.javaClass.getSimpleName())
            }

            if (!SkinResDeployerFactory.isSupportedAttr(attrName)) {
                continue
            }
            //当前属性是否在xml中使用skin:attr="textColor|background"方式来指定，指定了，只替换指定属性，未指定替换全部
            if (!SkinConfig.isCurrentAttrSpecified(attrName, specifiedAttrList)) {
                continue
            }

            //attrName=textColor attrValue=@2131492918 view=TextView
            if (!attrValue.startsWith("@")) {
                continue
            }

            var skinAttr: SkinAttr? = null
            try {
                skinAttr = getSkinAttrFromId(context, attrName, attrValue)
            } catch (ex: NumberFormatException) {
                Log.e(TAG, "parseSkinAttr() error happened", ex)
                skinAttr = getSkinAttrBySplit(context, attrName, attrValue)
            } catch (ex: Resources.NotFoundException) {
                Log.e(TAG, "parseSkinAttr() error happened", ex)
            }

            if (skinAttr != null) {
                viewAttrs[skinAttr.attrName] = skinAttr
            }
        }
        return viewAttrs
    }

    fun parseSkinAttr(context: Context?, attrName: String, resId: Int): SkinAttr? {
        if (context == null) {
            return null
        }
        var skinAttr: SkinAttr? = null
        try {
            val attrValueName = context.resources.getResourceEntryName(resId)
            val attrValueType = context.resources.getResourceTypeName(resId)
            skinAttr = SkinAttr(attrName, resId, attrValueName, attrValueType)
        } catch (ex: Exception) {
            Log.e(TAG, " parseSkinAttr--- error happened ", ex)
        }

        return skinAttr
    }

    private fun getSkinAttrBySplit(context: Context, attrName: String, attrValue: String): SkinAttr? {
        try {
            val dividerIndex = attrValue.indexOf("/")
            val entryName = attrValue.substring(dividerIndex + 1, attrValue.length)
            val typeName = attrValue.substring(1, dividerIndex)
            val id = context.resources.getIdentifier(entryName, typeName, context.packageName)
            return SkinAttr(attrName, id, entryName, typeName)
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "getSkinAttrBySplit error happened", e)
        }

        return null
    }

    private fun getSkinAttrFromId(context: Context, attrName: String, attrValue: String): SkinAttr? {
        val id = Integer.parseInt(attrValue.substring(1))
        return if (id == 0) {
            null
        } else parseSkinAttr(context, attrName, id)
    }
}
