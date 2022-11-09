package com.android.learn.mcontract

import com.android.learn.base.mmodel.RegisterLoginData
import com.android.learn.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class RegisterLoginContract {

    interface Presenter {

        fun login(account: String, password: String)

        fun register(account: String, password: String, rePassword: String)

    }

    interface View : BaseView {

        fun showRegisterResData(registerResData: RegisterLoginData)

        fun showLoginResData(loginData: RegisterLoginData)
    }
}
