/**
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 */
package com.android.base.utils

import android.support.multidex.BuildConfig
import android.util.Log



object LogUtil {

    private var isDebug = BuildConfig.DEBUG
    private val TAG = "zmy"

    fun init(isPrintable: Boolean) {
        isDebug = isPrintable
    }

    fun d(msg: String) {
        if (!isDebug) {
            return
        }
        Log.e(TAG, msg)
    }

    fun v(tag: String, msg: String) {
        if (!isDebug) {
            return
        }
        Log.v(tag, msg)
    }


    fun d(tag: String, msg: String) {
        if (!isDebug) {
            return
        }
        Log.d(tag, msg)
    }


    fun i(tag: String, msg: String) {
        if (!isDebug) {
            return
        }
        Log.i(tag, msg)
    }


    fun w(tag: String, msg: String) {
        if (!isDebug) {
            return
        }
        Log.w(tag, msg)
    }


    fun e(tag: String, msg: String) {
        if (!isDebug) {
            return
        }
        Log.e(tag, msg)
    }


}
