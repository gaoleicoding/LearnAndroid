package com.android.learn.base.mmodel

class BaseResponse<T> {

    /**
     * 0：成功，1：失败
     */
    var errorCode: Int = 0

    var errorMsg: String

    var data: T

    companion object {

        val SUCCESS = 0
        val FAIL = 1
    }

}