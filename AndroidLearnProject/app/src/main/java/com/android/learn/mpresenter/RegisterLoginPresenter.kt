package com.android.learn.mpresenter


import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.RegisterLoginData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.retrofit.ApiService
import com.android.learn.base.thirdframe.retrofit.RetrofitProvider
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.base.utils.Utils
import com.android.learn.mcontract.RegisterLoginContract

import io.reactivex.Observable


class RegisterLoginPresenter : BasePresenter<RegisterLoginContract.View>(), RegisterLoginContract.Presenter {
    override fun login(account: String, password: String) {
        val observable = RetrofitProvider.getInstance().createService(ApiService::class.java).login(account, password)
        addSubscribe(observable, object : BaseObserver<RegisterLoginData>(false) {
            override fun onNext(data: RegisterLoginData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showLoginResData(data)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)

            }
        })
    }

    override fun register(account: String, password: String, repassword: String) {
        val observable = RetrofitProvider.getInstance().createService(ApiService::class.java).register(account, password, repassword)
        addSubscribe(observable, object : BaseObserver<RegisterLoginData>(false) {
            override fun onNext(data: RegisterLoginData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    Utils.showToast("注册成功", true)
                    mView!!.showRegisterResData(data)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)

            }
        })
    }
}
