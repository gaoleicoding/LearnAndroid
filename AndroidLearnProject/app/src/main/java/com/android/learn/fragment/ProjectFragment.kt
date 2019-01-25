package com.android.learn.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.android.learn.R
import com.android.learn.activity.ArticleDetailActivity
import com.android.learn.adapter.DividerItemDecoration
import com.android.learn.adapter.ProjectQuickAdapter
import com.android.learn.base.fragment.BaseMvpFragment
import com.android.learn.base.mmodel.ProjectListData
import com.android.learn.base.view.CustomProgressDialog
import com.android.learn.mcontract.MainActivityContract
import com.android.learn.mcontract.ProjectContract
import com.android.learn.mpresenter.ProjectPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

import java.util.ArrayList

import butterknife.BindView


class ProjectFragment : BaseMvpFragment<ProjectPresenter, ProjectContract.View>(), ProjectContract.View {

    @BindView(R.id.project_recyclerview)
    lateinit var project_recyclerview: RecyclerView
    @BindView(R.id.smartRefreshLayout_home)
    lateinit var smartRefreshLayout: SmartRefreshLayout
    lateinit var projectAdapter: ProjectQuickAdapter
    lateinit var projectDataList: List<ProjectListData.ProjectData>

    override fun initData(bundle: Bundle?) {

    }

    override fun initView(view: View) {
        initSmartRefreshLayout()
        initRecyclerView()
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_project
    }

    override fun reload() {
        mPresenter!!.getProjectInfo(1, 294)
    }

    override fun initPresenter(): ProjectPresenter {
        return ProjectPresenter()
    }

    override fun loadData() {
        CustomProgressDialog.show(activity)
        mPresenter!!.getProjectInfo(1, 294)
    }


    override fun showProjectList(listData: ProjectListData, isRefresh: Boolean) {
        val newDataList = listData.data.getDatas()
        if (newDataList == null || newDataList.size == 0) {
            smartRefreshLayout!!.finishLoadMoreWithNoMoreData()
            return
        }
        smartRefreshLayout!!.finishLoadMore()
        projectAdapter.addData(newDataList)

    }

    private fun initRecyclerView() {
        projectDataList = ArrayList<ProjectListData.ProjectData>()
        projectAdapter = ProjectQuickAdapter(activity, projectDataList)
        project_recyclerview!!.addItemDecoration(DividerItemDecoration(activity!!,
                DividerItemDecoration.VERTICAL_LIST))
        project_recyclerview!!.layoutManager = LinearLayoutManager(activity)
        project_recyclerview!!.adapter = projectAdapter

        projectAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, ArticleDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString("url", projectDataList!![position].getLink())
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    //初始化下拉刷新控件
    private fun initSmartRefreshLayout() {
        smartRefreshLayout!!.isEnableLoadMore = true
        smartRefreshLayout!!.isEnableRefresh = false
        smartRefreshLayout!!.isEnableScrollContentWhenLoaded = true//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout!!.setEnableFooterFollowWhenLoadFinished(true)
        smartRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPresenter!!.onLoadMore(294)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPresenter!!.onRefreshMore(294)
            }
        })
    }

    fun scrollToTop() {
        project_recyclerview!!.scrollToPosition(0)
    }
}
