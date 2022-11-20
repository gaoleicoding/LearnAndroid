package com.android.learn.mcontract

import com.android.base.mmodel.FeedArticleListData
import com.android.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class WechatSubContract {

    interface Presenter {


        fun getWxArtileById(id: Int)


        fun addCollectArticle(position: Int, feedArticleData: FeedArticleData)

        fun cancelCollectArticle(position: Int, feedArticleData: FeedArticleData)
    }

    interface View : BaseView {


        fun showWxArticleById(datas: FeedArticleListData)

        fun showCollectArticleData(position: Int, feedArticleData: FeedArticleData)

        fun showCancelCollectArticleData(position: Int, feedArticleData: FeedArticleData)

        fun showCancelCollectArticleData(id: Int)
    }
}
