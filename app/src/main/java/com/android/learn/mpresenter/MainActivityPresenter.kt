package com.android.learn.mpresenter


import com.android.base.mmodel.BaseData
import com.android.base.mmodel.BaseListResponse
import com.android.base.mmodel.HotKeyData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.MainActivityContract


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
