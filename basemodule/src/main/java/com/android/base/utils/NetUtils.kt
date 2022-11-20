package com.android.base.utils


import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

import com.android.base.application.CustomApplication

/**
 * 跟网络相关的工具类
 *
 *
 *
 */
class NetUtils private constructor() {
    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {


        val isConnected: Boolean
            get() {

                val connectivity = CustomApplication.context
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                if (null != connectivity) {

                    val info = connectivity.activeNetworkInfo
                    if (null != info && info.isConnected) {
                        if (info.state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                }
                return false
            }

        /**
         * 判断是否是wifi连接
         */
        val isWifi: Boolean
            get() {
                val cm = CustomApplication.context
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                        ?: return false

                return cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI

            }

        /**
         * 打开网络设置界面
         */
        fun openSetting(activity: Activity) {
            val intent = Intent("/")
            val cm = ComponentName("com.android.settings",
                    "com.android.settings.WirelessSettings")
            intent.component = cm
            intent.action = "android.intent.action.VIEW"
            activity.startActivityForResult(intent, 0)
        }
    }

}
