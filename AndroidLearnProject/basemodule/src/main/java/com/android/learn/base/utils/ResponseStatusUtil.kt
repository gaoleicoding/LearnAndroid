package com.android.learn.base.utils

import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.BaseResponse

object ResponseStatusUtil {


    fun handleResponseStatus(baseData: BaseResponse<*>?) {
        if (baseData == null) return
        Utils.showToast(baseData.errorMsg, true)
    }

    fun handleResponseStatus(baseData: BaseData?) {
        if (baseData == null) return
        Utils.showToast(baseData.errorMsg, true)
    }
}