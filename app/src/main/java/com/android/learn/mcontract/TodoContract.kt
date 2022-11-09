package com.android.learn.mcontract

import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.TodoData
import com.android.learn.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class TodoContract {

    interface Presenter {


        fun getListNotDone(type: Int)

        fun getListDone(type: Int)

        fun deleteTodo(id: Int)

        fun updateTodoStatus(id: Int, status: Int)

    }

    interface View : BaseView {

        fun showListNotDone(todoData: TodoData)

        fun showListDone(todoData: TodoData)
        fun showUpdateTodoStatus(todoData: BaseData)
    }
}
