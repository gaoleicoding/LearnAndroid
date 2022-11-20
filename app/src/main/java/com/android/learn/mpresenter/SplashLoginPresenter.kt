package com.android.learn.mpresenter

import com.android.base.mmodel.RegisterLoginData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.retrofit.ApiService
import com.android.base.thirdframe.retrofit.RetrofitProvider
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.learn.mcontract.SplashLoginContract


class SplashLoginPresenter : BasePresenter<SplashLoginContract.View>(), SplashLoginContract.Presenter {

    override fun login(account: String, password: String) {
        val observable = RetrofitProvider.instance.createService(ApiService::class.java).login(account, password)
        addSubscribe(observable, object : BaseObserver<RegisterLoginData>(false) {
            override fun onNext(data: RegisterLoginData) {
                mView!!.showLoginResData(data)

            }
        })
    }
}
