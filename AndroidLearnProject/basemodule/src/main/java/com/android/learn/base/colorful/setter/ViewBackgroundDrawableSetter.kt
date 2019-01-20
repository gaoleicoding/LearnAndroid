package com.android.learn.base.colorful.setter

import android.content.res.Resources.Theme
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.view.View

/**
 * View的背景Drawabler Setter
 * @author mrsimple
 */
class ViewBackgroundDrawableSetter : ViewSetter {

    constructor(targetView: View, resId: Int) : super(targetView, resId) {}


    constructor(viewId: Int, resId: Int) : super(viewId, resId) {}

    override fun setValue(newTheme: Theme, themeId: Int) {
        if (mView == null) {
            return
        }
        val a = newTheme.obtainStyledAttributes(themeId,
                intArrayOf(mAttrResId))
        val attributeResourceId = a.getResourceId(0, 0)
        val drawable = mView!!.resources.getDrawable(
                attributeResourceId)
        a.recycle()
        mView!!.setBackgroundDrawable(drawable)
    }

}
