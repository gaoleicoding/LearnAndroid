package com.android.learn.mpresenter


import com.android.base.mmodel.BaseData
import com.android.base.mmodel.BaseResponse
import com.android.base.mmodel.TodoData
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.base.utils.ResponseStatusUtil
import com.android.learn.mcontract.TodoContract


class TodoPresenter : BasePresenter<TodoContract.View>(), TodoContract.Presenter {
    var notDonePage = 1
    var donePage = 1

    override fun getListNotDone(type: Int) {
        val observable = mRestService.getListNotDone(type, notDonePage)
        addSubscribe(observable, object : BaseObserver<BaseResponse<TodoData>>(true) {

            override fun onNext(data: BaseResponse<TodoData>) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showListNotDone(data.data)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }

        })
        notDonePage++
    }

    override fun getListDone(type: Int) {
        val observable = mRestService.getListDone(type, donePage)
        addSubscribe(observable, object : BaseObserver<BaseResponse<TodoData>>(true) {

            override fun onNext(data: BaseResponse<TodoData>) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showListDone(data.data)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }

        })
        donePage++
    }

    override fun deleteTodo(id: Int) {
        val observable = mRestService.deleteTodo(id)

        addSubscribe(observable, object : BaseObserver<BaseData>(false) {

            override fun onNext(data: BaseData) {
                if (data.errorCode == BaseData.SUCCESS) {
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }
        })
    }

    //status: 0或1，传1代表未完成到已完成
    override fun updateTodoStatus(id: Int, status: Int) {
        val observable = mRestService.updateTodoStatus(id, status)

        addSubscribe(observable, object : BaseObserver<BaseData>(false) {

            override fun onNext(data: BaseData) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView!!.showUpdateTodoStatus(data)
                } else
                    ResponseStatusUtil.handleResponseStatus(data)
            }
        })
    }
}
