package com.android.learn.base.colorful.setter

import android.content.res.Resources.Theme
import android.widget.TextView

/**
 * TextView 文本颜色Setter
 * @author mrsimple
 */
class TextColorSetter : ViewSetter {

    constructor(textView: TextView, resId: Int) : super(textView, resId) {}

    constructor(viewId: Int, resId: Int) : super(viewId, resId) {}

    override fun setValue(newTheme: Theme, themeId: Int) {
        if (mView == null) {
            return
        }
        (mView as TextView).setTextColor(getColor(newTheme))
    }

}
