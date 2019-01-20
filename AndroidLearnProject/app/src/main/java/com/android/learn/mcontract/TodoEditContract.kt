package com.android.learn.mcontract

import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.TodoData

/**
 * Created by gaolei on 2018/6/18.
 */

class TodoEditContract {

    interface Presenter {

        fun updateTodo(id: Int, map: Map<String, Any>)

    }

    interface View {


        fun showUpdateTodo(todoData: BaseData)
    }
}
