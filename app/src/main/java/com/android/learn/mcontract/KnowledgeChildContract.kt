package com.android.learn.mcontract


import com.android.base.mmodel.FeedArticleListData
import com.android.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.base.mview.BaseView


class KnowledgeChildContract {

    interface Presenter {
        fun getKnowledgeArticleList(num: Int, cid: Int)

        fun onRefreshMore(cid: Int)

        fun onLoadMore(cid: Int)

        fun addCollectArticle(position: Int, feedArticleData: FeedArticleData)

        fun cancelCollectArticle(position: Int, feedArticleData: FeedArticleData)
    }

    interface View : BaseView {
        fun showArticleList(itemBeans: FeedArticleListData, isRefresh: Boolean)
        fun showCollectArticleData(position: Int, feedArticleData: FeedArticleData)

        fun showCancelCollectArticleData(id: Int, feedArticleData: FeedArticleData)
    }

}
