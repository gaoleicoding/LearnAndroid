package com.android.learn.base.utils;

import com.android.learn.base.application.CustomApplication;

public class FontUtil {

    /**
     * @return 获取字体缩放比例
     */
    public static float getFontScale() {
        int currentIndex = (Integer) SPUtils.getParam(CustomApplication.context, "currentIndex", 1);
        return 1 + currentIndex * 0.1f;
    }
}
