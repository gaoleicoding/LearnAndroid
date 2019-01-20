package com.wind.me.xskinloader

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.LayoutInflater.Factory
import android.view.View

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.parser.SkinAttributeParser
import com.wind.me.xskinloader.util.ReflectUtils

import java.util.HashMap

class SkinInflaterFactory : Factory {
    private var mViewCreateFactory: Factory? = null

    //因为LayoutInflater的setFactory方法只能调用一次，当框架外需要处理view的创建时，可以调用此方法
    fun setInterceptFactory(factory: Factory) {
        mViewCreateFactory = factory
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        if (SkinConfig.DEBUG) {
            Log.d(TAG, "SkinInflaterFactory onCreateView(), create view name=$name  ")
        }
        var view: View? = null
        if (mViewCreateFactory != null) {
            //给框架外提供创建View的机会
            view = mViewCreateFactory!!.onCreateView(name, context, attrs)
        }
        if (isSupportSkin(attrs)) {
            if (view == null) {
                view = createView(context, name, attrs)
            }
            if (view != null) {
                parseAndSaveSkinAttr(attrs, view)
            }
        }

        return view
    }

    private fun createView(context: Context, name: String, attrs: AttributeSet): View? {
        var view: View? = null
        try {
            val inflater = LayoutInflater.from(context)
            assertInflaterContext(inflater, context)

            if (-1 == name.indexOf('.')) {
                if ("View" == name || "ViewStub" == name || "ViewGroup" == name) {
                    view = inflater.createView(name, "android.view.", attrs)
                }
                if (view == null) {
                    view = inflater.createView(name, "android.widget.", attrs)
                }
                if (view == null) {
                    view = inflater.createView(name, "android.webkit.", attrs)
                }
            } else {
                view = inflater.createView(name, null, attrs)
            }

        } catch (ex: Exception) {
            Log.e(TAG, "createView(), create view failed", ex)
            view = null
        }

        return view
    }

    //只有在xml中设置了View的属性skin:enable，才支持xml属性换肤
    fun isSupportSkin(attrs: AttributeSet): Boolean {
        return attrs.getAttributeBooleanValue(SkinConfig.SKIN_XML_NAMESPACE,
                SkinConfig.ATTR_SKIN_ENABLE, false)
    }

    //获取xml中指定的换肤属性，比如：skin:attrs = "textColor|background", 假如为空，表示支持所有能够支持的换肤属性
    private fun getXmlSpecifiedAttrs(attrs: AttributeSet): String? {
        return attrs.getAttributeValue(SkinConfig.SKIN_XML_NAMESPACE, SkinConfig.SUPPORTED_ATTR_SKIN_LIST)
    }

    private fun parseAndSaveSkinAttr(attrs: AttributeSet, view: View) {
        val specifiedAttrs = getXmlSpecifiedAttrs(attrs)
        var specifiedAttrsList: Array<String>? = null
        if (specifiedAttrs != null && specifiedAttrs.trim { it <= ' ' }.length > 0) {
            specifiedAttrsList = specifiedAttrs.split("\\|".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        }
        val viewAttrs = SkinAttributeParser.parseSkinAttr(attrs, view, specifiedAttrsList)
        if (viewAttrs == null || viewAttrs.size == 0) {
            return
        }

        //设置view的皮肤属性
        SkinManager.get().deployViewSkinAttrs(view, viewAttrs)
        //save view attribute
        SkinManager.get().saveSkinView(view, viewAttrs)
    }

    //在低版本系统中会出inflaterContext为空的问题， 因此需要处理inflaterContext为空的情况
    private fun assertInflaterContext(inflater: LayoutInflater, context: Context) {
        val inflaterContext = inflater.context
        if (inflaterContext == null) {
            ReflectUtils.setField(inflater, "mContext", context)
        }


        //设置mConstructorArgs的第一个参数context
        var constructorArgs = ReflectUtils.getField(inflater, "mConstructorArgs") as Array<Any>
        if (null == constructorArgs || constructorArgs.size < 2) {
            //异常，一般不会发生
            constructorArgs = arrayOfNulls(2)
            ReflectUtils.setField(inflater, "mConstructorArgs", constructorArgs)
        }

        //如果mConstructorArgs的第一个参数为空，则设置为mContext
        if (null == constructorArgs[0]) {
            constructorArgs[0] = inflater.context
        }
    }

    companion object {

        private val TAG = SkinInflaterFactory::class.java!!.getSimpleName()

        fun setFactory(inflater: LayoutInflater) {
            inflater.factory = SkinInflaterFactory()
        }

        fun setFactory(activity: Activity) {
            val inflater = activity.layoutInflater
            val factory = SkinInflaterFactory()
            if (activity is AppCompatActivity) {
                //AppCompatActivity本身包含一个factory,将TextView等转换为AppCompatTextView.java, 参考：AppCompatDelegateImplV9.java
                val delegate = activity.delegate
                factory.setInterceptFactory(Factory { name, context, attrs -> delegate.createView(null, name, context, attrs) })
            }
            inflater.factory = factory
        }
    }
}
