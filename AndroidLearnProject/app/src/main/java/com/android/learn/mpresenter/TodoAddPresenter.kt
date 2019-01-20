package com.android.learn.mpresenter


import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.TodoAddContract
import com.android.learn.mcontract.TodoEditContract

import io.reactivex.Observable


class TodoAddPresenter : BasePresenter<TodoAddContract.View>(), TodoAddContract.Presenter {

    override fun addTodo(map: Map<String, Any>) {
        val observable = mRestService.addTodo(map)

        addSubscribe(observable, object : BaseObserver<BaseData>(false) {

            override fun onNext(data: BaseData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showAddTodo(data)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }
        })
    }

}
