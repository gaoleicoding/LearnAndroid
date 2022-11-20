package com.android.learn.mpresenter


import com.android.base.mmodel.BaseData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.TodoEditContract


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
