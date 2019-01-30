package com.android.learn.mpresenter


import com.android.learn.base.mmodel.*
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.HomeContract
import com.android.learn.mcontract.WechatContract

import io.reactivex.Observable


class WechatPresenter : BasePresenter<WechatContract.View>(), WechatContract.Presenter {

    override fun getWxArticle() {
        val observable = mRestService.wxArticle
        addSubscribe(observable, object : BaseObserver<BaseListResponse<List<WxArticle>>>(false) {
            override fun onNext(datas: BaseListResponse<List<WxArticle>>) {
                mView!!.showWxArticle(datas.data)
            }
        })
    }

}
