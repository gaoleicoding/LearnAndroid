package com.android.learn.mpresenter


import com.android.base.mmodel.BaseListResponse
import com.android.base.mmodel.WxArticle
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.learn.mcontract.WechatContract


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
