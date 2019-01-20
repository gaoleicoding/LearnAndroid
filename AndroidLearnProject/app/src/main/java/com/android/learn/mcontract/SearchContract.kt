package com.android.learn.mcontract

import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.HotKeyData

/**
 * Created by gaolei on 2018/6/18.
 */

class SearchContract {

    interface Presenter {


        fun getFeedArticleList(key: String)

        fun onLoadMore(key: String)

        fun addCollectArticle(position: Int, feedArticleData: FeedArticleListData.FeedArticleData)

        fun cancelCollectArticle(position: Int, feedArticleData: FeedArticleListData.FeedArticleData)
    }

    interface View {


        fun showArticleList(itemBeans: FeedArticleListData)

        fun showCollectArticleData(position: Int, feedArticleData: FeedArticleListData.FeedArticleData)

        fun showCancelCollectArticleData(id: Int, feedArticleData: FeedArticleListData.FeedArticleData)
    }
}
