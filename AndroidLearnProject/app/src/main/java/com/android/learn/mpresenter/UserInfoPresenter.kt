package com.android.learn.mpresenter

import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.retrofit.ApiService
import com.android.learn.base.thirdframe.retrofit.RetrofitProvider
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.mcontract.UserInfoContract

import io.reactivex.Observable

class UserInfoPresenter : BasePresenter<UserInfoContract.View>(), UserInfoContract.Presenter {

    override fun getLogoutData() {

        val observable = RetrofitProvider.instance.createService(ApiService::class.java).logout()
        addSubscribe(observable, object : BaseObserver<BaseData>(false) {
            override fun onNext(baseData: BaseData) {}
        })
    }


}
