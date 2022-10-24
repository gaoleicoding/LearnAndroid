package com.android.base.utils;

import com.android.base.mmodel.BaseData;
import com.android.base.mmodel.BaseResponse;

public class ResponseStatusUtil {


    public static void handleResponseStatus(BaseResponse baseData) {
        if (baseData == null) return;
        Utils.showToast(baseData.errorMsg, true);
    }
    public static void handleResponseStatus(BaseData baseData) {
        if (baseData == null) return;
        Utils.showToast(baseData.errorMsg, true);
    }
}