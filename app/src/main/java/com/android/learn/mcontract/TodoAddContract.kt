package com.android.learn.mcontract

import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class TodoAddContract {

    interface Presenter {

        fun addTodo(map: Map<String, Any>)

    }

    interface View : BaseView {


        fun showAddTodo(todoData: BaseData)
    }
}
