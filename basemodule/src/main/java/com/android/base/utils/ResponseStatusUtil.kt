package com.android.base.utils

import com.android.base.mmodel.BaseData
import com.android.base.mmodel.BaseListResponse
import com.android.base.mmodel.BaseResponse

object ResponseStatusUtil {


    fun handleResponseStatus(baseData: BaseResponse<*>?) {
        if (baseData == null) return
        Utils.showToast(baseData.errorMsg, true)
    }

    fun handleListResponseStatus( baseData: BaseListResponse<List<BaseData>>) {
        if (baseData == null) return
        Utils.showToast(baseData.errorMsg, true)
    }

    fun handleResponseStatus(baseData: BaseData?) {
        if (baseData == null) return
        Utils.showToast(baseData.errorMsg, true)
    }
}