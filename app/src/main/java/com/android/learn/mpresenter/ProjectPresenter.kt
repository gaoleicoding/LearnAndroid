package com.android.learn.mpresenter


import com.android.learn.base.mmodel.ProjectListData
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.mcontract.ProjectContract

import io.reactivex.Observable


class ProjectPresenter : BasePresenter<ProjectContract.View>(), ProjectContract.Presenter {

    private var mCurrentPage = 1

    override fun getProjectInfo(page: Int, cid: Int) {
        val observable = mRestService.getProjectListData(page, cid)
        addSubscribe(observable, object : BaseObserver<ProjectListData>(true) {
            override fun onNext(projectListData: ProjectListData) {
                mView!!.showProjectList(projectListData, false)
            }
        })
    }

    override fun onRefreshMore(cid: Int) {
        val observable = mRestService.getProjectListData(-1, cid)
        addSubscribe(observable, object : BaseObserver<ProjectListData>(false) {
            override fun onNext(projectListData: ProjectListData) {
                mView!!.showProjectList(projectListData, true)
            }
        })
    }

    override fun onLoadMore(cid: Int) {
        ++mCurrentPage
        val observable = mRestService.getProjectListData(mCurrentPage, cid)
        addSubscribe(observable, object : BaseObserver<ProjectListData>(false) {
            override fun onNext(projectListData: ProjectListData) {
                mView!!.showProjectList(projectListData, false)
            }
        })
    }


}

