package com.android.learn.mcontract

import com.android.base.mmodel.ProjectListData
import com.android.base.mview.BaseView

/**
 * Created by gaolei on 2018/6/18.
 */

class ProjectContract {

    interface Presenter {

        fun getProjectInfo(page: Int, cid: Int)

        fun onRefreshMore(cid: Int)

        fun onLoadMore(cid: Int)
    }

    interface View : BaseView {

        fun showProjectList(itemBeans: ProjectListData, isRefresh: Boolean)
    }
}
