package com.android.learn.mcontract

import com.android.base.mmodel.BaseData
import com.android.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class TodoEditContract {

    interface Presenter {

        fun updateTodo(id: Int, map: Map<String, Any>)

    }

    interface View : BaseView {


        fun showUpdateTodo(todoData: BaseData)
    }
}
