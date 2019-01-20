package com.android.learn.mcontract

import com.android.learn.base.mmodel.BaseData

/**
 * Created by gaolei on 2018/6/18.
 */

class TodoAddContract {

    interface Presenter {

        fun addTodo(map: Map<String, Any>)

    }

    interface View {


        fun showAddTodo(todoData: BaseData)
    }
}
