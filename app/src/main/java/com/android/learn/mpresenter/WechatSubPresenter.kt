package com.android.learn.mpresenter


import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.BaseResponse
import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.WechatSubContract

import io.reactivex.Observable


class WechatSubPresenter : BasePresenter<WechatSubContract.View>(), WechatSubContract.Presenter {

    var num = 0
    override fun getWxArtileById(id: Int) {
        val observable = mRestService.getWxArtileById(id, num)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(true) {

            override fun onNext(listData: BaseResponse<FeedArticleListData>) {
                mView!!.showWxArticleById(listData.data)
            }

        })
        num++
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
