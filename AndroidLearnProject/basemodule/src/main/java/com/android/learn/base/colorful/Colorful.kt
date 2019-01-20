package com.android.learn.base.colorful

import java.util.HashSet

import android.app.Activity
import android.content.res.Resources.Theme
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView

import com.android.learn.base.colorful.setter.TextColorSetter
import com.android.learn.base.colorful.setter.ViewBackgroundColorSetter
import com.android.learn.base.colorful.setter.ViewBackgroundDrawableSetter
import com.android.learn.base.colorful.setter.ViewSetter


/**
 * 主题切换控制类
 *
 * @author mrsimple
 */
class Colorful
/**
 * private constructor
 *
 * @param builder
 */
private constructor(
        /**
         * Colorful Builder
         */
        internal var mBuilder: Builder) {

    /**
     * 设置新的主题
     *
     * @param newTheme
     */
    fun setTheme(newTheme: Int) {
        mBuilder.setTheme(newTheme)
    }

    /**
     *
     * 构建Colorful的Builder对象
     *
     * @author mrsimple
     */
    class Builder {
        /**
         * 存储了视图和属性资源id的关系表
         */
        internal var mElements: MutableSet<ViewSetter> = HashSet()
        /**
         * 目标Activity
         */
        internal var mActivity: Activity? = null

        /**
         * @param activity
         */
        constructor(activity: Activity) {
            mActivity = activity
        }

        /**
         *
         * @param fragment
         */
        constructor(fragment: Fragment) {
            mActivity = fragment.activity
        }

        private fun findViewById(viewId: Int): View {
            return mActivity!!.findViewById(viewId)
        }

        /**
         * 将View id与存储该view背景色的属性进行绑定
         *
         * @param viewId
         * 控件id
         * @param colorId
         * 颜色属性id
         * @return
         */
        fun backgroundColor(viewId: Int, colorId: Int): Builder {
            mElements.add(ViewBackgroundColorSetter(findViewById(viewId),
                    colorId))
            return this
        }


        fun backgroundDrawable(viewId: Int, drawableId: Int): Builder {
            mElements.add(ViewBackgroundDrawableSetter(
                    findViewById(viewId), drawableId))
            return this
        }

        /**
         * 将TextView id与存储该TextView文本颜色的属性进行绑定
         *
         * @param viewId
         * TextView或者TextView子类控件的id
         * @param colorId
         * 颜色属性id
         * @return
         */
        fun textColor(viewId: Int, colorId: Int): Builder {
            val textView = findViewById(viewId) as TextView
            mElements.add(TextColorSetter(textView, colorId))
            return this
        }

        /**
         * 用户手动构造并且添加Setter
         *
         * @param setter
         * 用户自定义的Setter
         * @return
         */
        fun setter(setter: ViewSetter): Builder {
            mElements.add(setter)
            return this
        }

        /**
         * 设置新的主题
         *
         * @param newTheme
         */
        fun setTheme(newTheme: Int) {
            mActivity!!.setTheme(newTheme)
            makeChange(newTheme)
        }

        /**
         * 修改各个视图绑定的属性
         */
        private fun makeChange(themeId: Int) {
            val curTheme = mActivity!!.theme
            for (setter in mElements) {
                setter.setValue(curTheme, themeId)
            }
        }

        /**
         * 创建Colorful对象
         *
         * @return
         */
        fun create(): Colorful {
            return Colorful(this)
        }
    }
}
