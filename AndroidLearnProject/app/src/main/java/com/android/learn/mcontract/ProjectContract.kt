package com.android.learn.mcontract

import com.android.learn.base.mmodel.ProjectListData

/**
 * Created by gaolei on 2018/6/18.
 */

class ProjectContract {

    interface Presenter {

        fun getProjectInfo(page: Int, cid: Int)

        fun onRefreshMore(cid: Int)

        fun onLoadMore(cid: Int)
    }

    interface View {

        fun showProjectList(itemBeans: ProjectListData, isRefresh: Boolean)
    }
}
