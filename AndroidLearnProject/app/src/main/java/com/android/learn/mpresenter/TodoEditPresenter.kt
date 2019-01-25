package com.android.learn.mpresenter


import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.BaseResponse
import com.android.learn.base.mmodel.TodoData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.TodoContract
import com.android.learn.mcontract.TodoEditContract

import io.reactivex.Observable


class TodoEditPresenter : BasePresenter<TodoEditContract.View>(), TodoEditContract.Presenter {

    override fun updateTodo(id: Int, map: Map<String, Any>) {
        val observable = mRestService.updateTodo(id, map)

        addSubscribe(observable, object : BaseObserver<BaseData>(false) {

            override fun onNext(data: BaseData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showUpdateTodo(data)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }
        })
    }

}
