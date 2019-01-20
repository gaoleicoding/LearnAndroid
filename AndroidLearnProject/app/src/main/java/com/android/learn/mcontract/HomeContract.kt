package com.android.learn.mcontract

import com.android.learn.base.mmodel.BannerListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mmodel.FeedArticleListData

/**
 * Created by gaolei on 2018/6/18.
 */

class HomeContract {

    interface Presenter {

        fun getBannerInfo()

        fun getFeedArticleList(num: Int)

        fun onLoadMore()

        fun onRefreshMore()

        fun addCollectArticle(position: Int, feedArticleData: FeedArticleData)

        fun cancelCollectArticle(position: Int, feedArticleData: FeedArticleData)
    }

    interface View {

        fun showArticleList(itemBeans: FeedArticleListData, isRefresh: Boolean)

        fun showBannerList(itemBeans: BannerListData)

        fun showCollectArticleData(position: Int, feedArticleData: FeedArticleData)

        fun showCancelCollectArticleData(position: Int, feedArticleData: FeedArticleData)
        fun showCancelCollectArticleData(id: Int)
    }
}
