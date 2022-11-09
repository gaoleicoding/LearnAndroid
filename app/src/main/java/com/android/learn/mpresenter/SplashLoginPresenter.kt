package com.android.learn.mpresenter

import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.RegisterLoginData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.retrofit.ApiService
import com.android.learn.base.thirdframe.retrofit.RetrofitProvider
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.SplashLoginContract

import io.reactivex.Observable


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
