package com.android.learn.mpresenter

import com.android.base.mmodel.BaseData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.retrofit.ApiService
import com.android.base.thirdframe.retrofit.RetrofitProvider
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.learn.mcontract.UserInfoContract

class UserInfoPresenter : BasePresenter<UserInfoContract.View>(), UserInfoContract.Presenter {

    override fun getLogoutData() {

        val observable = RetrofitProvider.instance.createService(ApiService::class.java).logout()
        addSubscribe(observable, object : BaseObserver<BaseData>(false) {
            override fun onNext(baseData: BaseData) {}
        })
    }


}
