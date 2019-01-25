package com.android.learn.mcontract

import com.android.learn.base.mmodel.BannerListData
import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mmodel.WxArticle
import com.android.learn.base.mview.BaseView

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
