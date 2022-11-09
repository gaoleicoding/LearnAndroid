package com.android.learn.mcontract

import com.android.learn.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class UserInfoContract {

    interface Presenter {


        fun getLogoutData()

    }

    interface View : BaseView
}
