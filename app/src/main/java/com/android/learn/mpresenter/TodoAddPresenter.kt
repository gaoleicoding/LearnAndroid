package com.android.learn.mpresenter


import com.android.base.mmodel.BaseData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.TodoAddContract


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
