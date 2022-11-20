package com.android.learn.mcontract

import com.android.base.mmodel.HotKeyData
import com.android.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class MainActivityContract {

    interface Presenter {

        fun getHotKey()

    }

    interface View : BaseView {

        fun showHotKey(list: List<HotKeyData>)

    }
}
