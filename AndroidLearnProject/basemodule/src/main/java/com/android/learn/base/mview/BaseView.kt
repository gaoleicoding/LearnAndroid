package com.android.learn.base.mview

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun showErrorMsg(errorMsg: String)
}