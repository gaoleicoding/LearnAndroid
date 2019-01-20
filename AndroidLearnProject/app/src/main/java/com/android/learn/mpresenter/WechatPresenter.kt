package com.android.learn.mpresenter


import com.android.learn.base.mmodel.BannerListData
import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.BaseResponse
import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mmodel.WxArticle
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.HomeContract
import com.android.learn.mcontract.WechatContract

import io.reactivex.Observable


class WechatPresenter : BasePresenter<WechatContract.View>(), WechatContract.Presenter {

    override fun getWxArticle() {
        val observable = mRestService.wxArticle
        addSubscribe(observable, object : BaseObserver<BaseResponse<List<WxArticle>>>(false) {
            override fun onNext(datas: BaseResponse<List<WxArticle>>) {
                mView!!.showWxArticle(datas.data)
            }
        })
    }

}
