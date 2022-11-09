package com.android.learn.mpresenter


import com.android.learn.base.mmodel.*
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.CollectContract
import com.android.learn.mcontract.MainActivityContract

import io.reactivex.Observable


class MainActivityPresenter : BasePresenter<MainActivityContract.View>(), MainActivityContract.Presenter {

    private val mCurrentPage = 0

    override fun getHotKey() {
        val observable = mRestService.hotKey
        addSubscribe(observable, object : BaseObserver<BaseListResponse<List<HotKeyData>>>(true) {

            override fun onNext(datas: BaseListResponse<List<HotKeyData>>) {
                if (datas.errorCode == BaseData.SUCCESS) {
                    mView!!.showHotKey(datas.data)
                } else
                    ResponseStatusUtil.handleListResponseStatus(datas as BaseListResponse<List<BaseData>>)
            }

        })

    }

}
