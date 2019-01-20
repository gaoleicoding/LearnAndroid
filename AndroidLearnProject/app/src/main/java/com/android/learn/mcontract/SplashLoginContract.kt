package com.android.learn.mcontract

import com.android.learn.base.mmodel.RegisterLoginData

/**
 * Created by gaolei on 2018/6/18.
 */

class SplashLoginContract {

    interface Presenter {


        fun login(username: String, password: String)


    }

    interface View {

        fun showLoginResData(loginResData: RegisterLoginData)

    }

}
