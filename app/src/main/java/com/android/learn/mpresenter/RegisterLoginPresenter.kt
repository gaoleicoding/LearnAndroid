package com.android.learn.mpresenter


import com.android.base.mmodel.BaseData
import com.android.base.mmodel.RegisterLoginData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.retrofit.ApiService
import com.android.base.thirdframe.retrofit.RetrofitProvider
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.base.utils.ResponseStatusUtil
import com.android.base.utils.Utils
import com.android.learn.mcontract.RegisterLoginContract


class RegisterLoginPresenter : BasePresenter<RegisterLoginContract.View>(), RegisterLoginContract.Presenter {
    override fun login(account: String, password: String) {
        val observable = RetrofitProvider.instance.createService(ApiService::class.java).login(account, password)
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
        val observable = RetrofitProvider.instance.createService(ApiService::class.java).register(account, password, repassword)
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
