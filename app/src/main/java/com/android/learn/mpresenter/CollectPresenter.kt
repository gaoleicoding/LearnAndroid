package com.android.learn.mpresenter


import com.android.base.mmodel.BaseData
import com.android.base.mmodel.BaseResponse
import com.android.base.mmodel.FeedArticleListData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.CollectContract


class CollectPresenter : BasePresenter<CollectContract.View>(), CollectContract.Presenter {

    internal var page = 0

    override fun getCollectList() {

        val observable = mRestService.getCollectList(page)
        addSubscribe(observable, object : BaseObserver<BaseResponse<FeedArticleListData>>(true) {

            override fun onNext(data: BaseResponse<FeedArticleListData>) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showCollectList(data.data)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }

        })
        page++
    }

    override fun cancelCollectArticle(position: Int, id: Int) {
        //        int id = feedArticleData.getId();
        val observable = mRestService.cancelCollectArticle(id, -1)
        addSubscribe(observable, object : BaseObserver<BaseData>(true) {

            override fun onNext(data: BaseData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showCancelCollectArticle(position, id)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }

        })
    }
}
