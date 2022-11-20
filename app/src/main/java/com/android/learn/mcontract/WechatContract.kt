package com.android.learn.mcontract

import com.android.base.mmodel.WxArticle
import com.android.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class WechatContract {

    interface Presenter {

        fun getWxArticle()

    }

    interface View : BaseView {

        fun showWxArticle(list: List<WxArticle>)


    }
}
