package com.android.learn.base.colorful.setter

import android.content.res.Resources.Theme
import android.view.View

/**
 * View的背景色Setter
 * @author mrsimple
 */
class ViewBackgroundColorSetter : ViewSetter {

    constructor(target: View, resId: Int) : super(target, resId) {}

    constructor(viewId: Int, resId: Int) : super(viewId, resId) {}

    override fun setValue(newTheme: Theme, themeId: Int) {
        if (mView != null) {
            mView!!.setBackgroundColor(getColor(newTheme))
        }
    }

}
