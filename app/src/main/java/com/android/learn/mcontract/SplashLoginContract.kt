package com.android.learn.mcontract

import com.android.base.mmodel.RegisterLoginData
import com.android.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class SplashLoginContract {

    interface Presenter {


        fun login(username: String, password: String)


    }

    interface View : BaseView {

        fun showLoginResData(loginResData: RegisterLoginData)

    }

}
