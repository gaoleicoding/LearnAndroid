package com.android.learn.base.mmodel

/**
 * @author quchao
 * @date 2018/2/12
 */

open class BaseData {

    var errorCode: Int = 0

    lateinit var errorMsg: String

    companion object {

        val SUCCESS = 0
        val FAIL = -1
    }


}
