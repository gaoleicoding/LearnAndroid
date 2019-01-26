package com.android.learn.base.utils.account

import com.android.learn.base.event.LoginEvent
import com.android.learn.base.mmodel.RegisterLoginData

import org.greenrobot.eventbus.EventBus

object UserUtil {

    var isLogined = false
    var userInfo: RegisterLoginData? = null

    fun gainUserInfo(): RegisterLoginData? {
        return userInfo
    }

    fun assignUserInfo(userInfo: RegisterLoginData?) {
        UserUtil.userInfo = userInfo
        if (userInfo != null) {
            isLogined = true
        } else
            isLogined = false
        val accountEvent = LoginEvent()
        EventBus.getDefault().post(accountEvent)

    }
}
