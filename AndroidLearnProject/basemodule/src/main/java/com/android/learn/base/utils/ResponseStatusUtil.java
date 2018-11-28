package com.android.learn.base.utils;

import com.android.learn.base.mmodel.BaseData;
public class ResponseStatusUtil {


    public static void handleResponseStatus(BaseData baseData) {
        if (baseData == null) return;
        Utils.showToast(baseData.errorMsg, true);
    }
}