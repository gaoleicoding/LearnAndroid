package com.android.learn.mpresenter


import com.android.base.mmodel.BaseData
import com.android.base.mmodel.BaseResponse
import com.android.base.mmodel.FeedArticleListData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.SearchContract


class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {

    private var mCurrentPage = 0

    override fun getFeedArticleList(key: String) {
        val observable = mRestService.search(mCurrentPage, key)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(true) {
            override fun onNext(feedArticleListData: BaseResponse<FeedArticleListData>) {
                mView!!.showArticleList(feedArticleListData.data)
            }
        })
    }

    override fun onLoadMore(key: String) {
        ++mCurrentPage
        val observable = mRestService.search(mCurrentPage, key)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(true) {
            override fun onNext(feedArticleListData: BaseResponse<FeedArticleListData>) {
                mView!!.showArticleList(feedArticleListData.data)
            }
        })
    }

    override fun addCollectArticle(position: Int, feedArticleData: FeedArticleListData.FeedArticleData) {
        val observable = mRestService.addCollectArticle(feedArticleData.id)
        addSubscribe(observable, object : BaseObserver<BaseData>(true) {

            override fun onNext(data: BaseData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    feedArticleData.isCollect = true
                    mView!!.showCollectArticleData(position, feedArticleData)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }

        })

    }

    override fun cancelCollectArticle(position: Int, feedArticleData: FeedArticleListData.FeedArticleData) {
        val observable = mRestService.cancelCollectArticle(feedArticleData.id, -1)
        addSubscribe(observable, object : BaseObserver<BaseData>(true) {

            override fun onNext(data: BaseData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    feedArticleData.isCollect = false
                    mView!!.showCancelCollectArticleData(position, feedArticleData)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }

        })
    }


}
