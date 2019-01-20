package com.android.learn.base.colorful.setter

import android.content.res.Resources.Theme
import android.util.TypedValue
import android.view.View

/**
 * ViewSetter，用于通过{@see #mAttrResId}
 * 设置View的某个属性值,例如背景Drawable、背景色、文本颜色等。如需修改其他属性,可以自行扩展ViewSetter.
 *
 * @author mrsimple
 */
abstract class ViewSetter {

    /**
     * 目标View
     */
    var mView: View? = null
    /**
     * 目标view id,有时在初始化时还未构建该视图,比如ListView的Item View中的某个控件
     */
    var mViewId: Int = 0
    /**
     * 目标View要的特定属性id
     */
    protected var mAttrResId: Int = 0

    /**
     * 获取视图的Id
     *
     * @return
     */
    val viewId: Int
        get() = if (mView != null) mView!!.id else -1

    protected val isViewNotFound: Boolean
        get() = mView == null

    constructor(targetView: View, resId: Int) {
        mView = targetView
        mAttrResId = resId
    }

    constructor(viewId: Int, resId: Int) {
        mViewId = viewId
        mAttrResId = resId
    }

    /**
     *
     * @param newTheme
     * @param themeId
     */
    abstract fun setValue(newTheme: Theme, themeId: Int)

    /**
     *
     * @param newTheme
     * @param resId
     * @return
     */
    protected fun getColor(newTheme: Theme): Int {
        val typedValue = TypedValue()
        newTheme.resolveAttribute(mAttrResId, typedValue, true)
        return typedValue.data
    }
}
