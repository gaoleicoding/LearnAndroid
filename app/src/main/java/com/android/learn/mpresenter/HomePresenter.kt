package com.android.learn.mpresenter


import com.android.base.mmodel.BannerListData
import com.android.base.mmodel.BaseData
import com.android.base.mmodel.BaseResponse
import com.android.base.mmodel.FeedArticleListData
import com.android.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.HomeContract


class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private val isRefresh = true
    var mCurrentPage = 0

    override fun onRefreshMore() {
        val observable = mRestService.getFeedArticleList(-1)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(false) {
            override fun onNext(feedArticleListData: BaseResponse<FeedArticleListData>) {
                mView!!.showArticleList(feedArticleListData.data, true)
            }
        })


    }

    override fun onLoadMore() {
        ++mCurrentPage
        val observable = mRestService.getFeedArticleList(mCurrentPage)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(false) {
            override fun onNext(feedArticleListData: BaseResponse<FeedArticleListData>) {
                mView!!.showArticleList(feedArticleListData.data, false)
            }
        })
    }

    override fun getFeedArticleList(num: Int) {
        val observable = mRestService.getFeedArticleList(num)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(true) {
            override fun onNext(feedArticleListData: BaseResponse<FeedArticleListData>) {
                mView!!.showArticleList(feedArticleListData.data, false)
            }
        })
    }

    override fun getBannerInfo() {
        val observable = mRestService.bannerListData
        addSubscribe(observable, object : BaseObserver<BannerListData>(true) {

            override fun onNext(bannerListData: BannerListData) {
                mView!!.showBannerList(bannerListData)
            }

        })


    }


    override fun addCollectArticle(position: Int, feedArticleData: FeedArticleData) {
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

    override fun cancelCollectArticle(position: Int, feedArticleData: FeedArticleData) {
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

    fun cancelCollectArticle(id: Int) {
        val observable = mRestService.cancelCollectArticle(id, -1)
        addSubscribe(observable, object : BaseObserver<BaseData>(true) {

            override fun onNext(data: BaseData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showCancelCollectArticleData(id)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }

        })
    }

}
