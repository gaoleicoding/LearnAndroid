package com.android.learn.base.mmodel

class BaseListResponse<T:List< BaseData>> {

    /**
     * 0：成功，1：失败
     */
    var errorCode: Int = 0

    lateinit var errorMsg: String

    lateinit var data: T

    companion object {

        val SUCCESS = 0
        val FAIL = 1
    }

}