package com.android.learn.mcontract

import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class CollectContract {

    interface Presenter {


        fun getCollectList()

        fun cancelCollectArticle(position: Int, id: Int)
    }

    interface View : BaseView {

        fun showCollectList(feedArticleListData: FeedArticleListData)

        fun showCancelCollectArticle(position: Int, id: Int)
    }
}
