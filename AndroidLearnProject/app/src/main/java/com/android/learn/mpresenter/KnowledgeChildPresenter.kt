package com.android.learn.mpresenter

import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.BaseResponse
import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.KnowledgeChildContract

import io.reactivex.Observable


class KnowledgeChildPresenter : BasePresenter<KnowledgeChildContract.View>(), KnowledgeChildContract.Presenter {
    private var mCurrentPage = 0

    override fun getKnowledgeArticleList(num: Int, cid: Int) {
        val observable = mRestService.getKnowledgeArticleList(num, cid)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(true) {
            override fun onNext(feedArticleListData: BaseResponse<FeedArticleListData>) {
                mView!!.showArticleList(feedArticleListData.data, false)
            }
        })
    }

    override fun onRefreshMore(cid: Int) {
        val observable = mRestService.getKnowledgeArticleList(-1, cid)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(false) {
            override fun onNext(feedArticleListData: BaseResponse<FeedArticleListData>) {
                mView!!.showArticleList(feedArticleListData.data, true)
            }
        })


    }

    override fun onLoadMore(cid: Int) {
        ++mCurrentPage
        val observable = mRestService.getKnowledgeArticleList(mCurrentPage, cid)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(false) {
            override fun onNext(feedArticleListData: BaseResponse<FeedArticleListData>) {
                mView!!.showArticleList(feedArticleListData.data, false)
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


}
