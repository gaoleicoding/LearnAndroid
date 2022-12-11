package com.android.learn.fragment

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import com.android.learn.R
import com.android.learn.activity.TodoEditActivity
import com.android.learn.adapter.DividerItemDecoration
import com.android.learn.adapter.TodoQuickAdapter
import com.android.base.event.UpdateDoneEvent
import com.android.base.event.UpdateTodoEvent
import com.android.base.fragment.BaseMvpFragment
import com.android.base.mmodel.BaseData
import com.android.base.mmodel.TodoData
import com.android.base.mmodel.TodoData.DatasBean
import com.android.base.view.CustomProgressDialog
import com.android.learn.mcontract.TodoContract
import com.android.learn.mpresenter.TodoPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class TodoFragment : BaseMvpFragment<TodoPresenter, TodoContract.View>(), TodoContract.View {
    @BindView(R.id.article_recyclerview)
    lateinit var article_recyclerview: RecyclerView
    @BindView(R.id.smartRefreshLayout)
    lateinit var smartRefreshLayout: SmartRefreshLayout
    @BindView(R.id.tv_empty_todo)
    lateinit var tv_empty_todo: TextView
    lateinit var todoList: List<TodoData.DatasBean>
    private var todoAdapter: TodoQuickAdapter? = null

    internal var fragmentPosition: Int = 0
    internal var clickId: Int = 0


    override fun initData(bundle: Bundle?) {
        fragmentPosition = bundle!!.getInt("position", 0)
        initSmartRefreshLayout()
        initRecyclerView()
    }

    override fun initView(view: View) {
        EventBus.getDefault().register(this)
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_todo
    }

    override fun reload() {

    }

    override fun initPresenter(): TodoPresenter {
        return TodoPresenter()
    }

    override fun loadData() {
        CustomProgressDialog.show(activity)
        if (fragmentPosition == 0)
            mPresenter!!.getListNotDone(0)
        if (fragmentPosition == 1)
            mPresenter!!.getListDone(0)
    }


    private fun initRecyclerView() {
        todoList = ArrayList()
        todoAdapter = TodoQuickAdapter(activity, todoList, fragmentPosition)
        article_recyclerview.addItemDecoration(DividerItemDecoration(activity!!,
                DividerItemDecoration.VERTICAL_LIST))
        article_recyclerview.layoutManager = LinearLayoutManager(activity)

        //解决数据加载完成后, 没有停留在顶部的问题
        article_recyclerview.isFocusable = false
        article_recyclerview.adapter = todoAdapter

        todoAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var datasBean: DatasBean? = null
            if (fragmentPosition == 0) {
                datasBean = todoAdapter!!.data[position]
                val bundle = Bundle()
                bundle.putSerializable("todo_item", datasBean)
                TodoEditActivity.startActivity(activity!!, bundle)
            }
        }
        todoAdapter!!.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val datasBean = todoAdapter!!.data[position]
            when (view.id) {
                R.id.iv_item_todo -> {
                    if (fragmentPosition == 0) {
                        clickId = datasBean.id
                        mPresenter!!.updateTodoStatus(clickId, 1)
                        todoAdapter!!.remove(position)
                    }
                    if (fragmentPosition == 1) {
                        clickId = datasBean.id
                        mPresenter!!.updateTodoStatus(clickId, 0)
                        todoAdapter!!.remove(position)
                    }
                }
                R.id.iv_item_delete -> {
                    mPresenter!!.deleteTodo(datasBean.id)
                    todoAdapter!!.remove(position)
                }
            }
        }
    }

    //初始化下拉刷新控件
    private fun initSmartRefreshLayout() {
        smartRefreshLayout.isEnableLoadMore = true
        smartRefreshLayout.isEnableRefresh = false
        smartRefreshLayout.isEnableScrollContentWhenLoaded = true//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true)
        smartRefreshLayout.setOnLoadMoreListener {
            if (fragmentPosition == 0)
                mPresenter!!.getListNotDone(0)
            if (fragmentPosition == 1)
                mPresenter!!.getListDone(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun showListNotDone(todoData: TodoData) {
        val newDataList = todoData.datas
        if (newDataList.size == 0) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData()
        }
        smartRefreshLayout.finishLoadMore()
        todoAdapter!!.addData(newDataList)

        if (todoAdapter!!.data.size == 0) {
            tv_empty_todo.visibility = View.VISIBLE

        } else
            tv_empty_todo.visibility = View.GONE
    }

    override fun showListDone(todoData: TodoData) {
        val newDataList = todoData.datas
        if (newDataList.size == 0) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData()
        }
        smartRefreshLayout.finishLoadMore()

        todoAdapter!!.addData(newDataList)

        if (todoAdapter!!.data.size == 0) {
            tv_empty_todo.visibility = View.VISIBLE
            tv_empty_todo.text = getString(R.string.empty_done)
        } else
            tv_empty_todo.visibility = View.GONE


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UpdateDoneEvent) {
        if (fragmentPosition == 1) {
            todoAdapter!!.data.clear()
            mPresenter!!.donePage = 1
            mPresenter!!.getListDone(0)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UpdateTodoEvent) {
        if (fragmentPosition == 0) {
            todoAdapter!!.data.clear()
            mPresenter!!.notDonePage = 1
            mPresenter!!.getListNotDone(0)
        }
    }

    override fun showUpdateTodoStatus(todoData: BaseData) {
        if (fragmentPosition == 0) {
            EventBus.getDefault().post(UpdateDoneEvent())
        }
        if (fragmentPosition == 1) {
            EventBus.getDefault().post(UpdateTodoEvent())
        }
    }

    companion object {

        fun newInstance(bundle: Bundle): TodoFragment {
            val testFragment = TodoFragment()
            testFragment.arguments = bundle
            return testFragment
        }
    }
}
