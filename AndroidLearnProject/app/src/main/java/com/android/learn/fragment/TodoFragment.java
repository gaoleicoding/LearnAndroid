package com.android.learn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.learn.R;
import com.android.learn.activity.ArticleDetailActivity;
import com.android.learn.adapter.DividerItemDecoration;
import com.android.learn.adapter.TodoQuickAdapter;
import com.android.learn.base.fragment.BaseMvpFragment;
import com.android.learn.base.mmodel.TodoData;
import com.android.learn.base.view.CustomProgressDialog;
import com.android.learn.mcontract.TodoContract;
import com.android.learn.mpresenter.TodoPresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class TodoFragment extends BaseMvpFragment<TodoPresenter> implements TodoContract.View {
    @BindView(R.id.article_recyclerview)
    RecyclerView article_recyclerview;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private List<TodoData.DatasBean> todoList;
    private TodoQuickAdapter todoAdapter;

    int position;

    public static TodoFragment newInstance(Bundle bundle) {
        TodoFragment testFragment = new TodoFragment();
        testFragment.setArguments(bundle);
        return testFragment;
    }


    @Override
    public void initData(Bundle bundle) {
        position = bundle.getInt("position", 0);
        initSmartRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_wechat_sub;
    }

    @Override
    public void reload() {

    }

    @Override
    public TodoPresenter initPresenter() {
        return new TodoPresenter();
    }

    @Override
    protected void loadData() {
        CustomProgressDialog.show(getActivity());
        if (position == 0)
            mPresenter.getListNotDone(0);
        if (position == 1)
            mPresenter.getListDone(0);
    }


    private void initRecyclerView() {
        todoList = new ArrayList<>();
        todoAdapter = new TodoQuickAdapter(getActivity(), todoList, position);
        article_recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        article_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        //解决数据加载完成后, 没有停留在顶部的问题
        article_recyclerview.setFocusable(false);
        article_recyclerview.setAdapter(todoAdapter);

        todoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
//                Bundle bundle = new Bundle();
//                intent.putExtras(bundle);
//                startActivity(intent);
            }

        });

    }

    //初始化下拉刷新控件
    private void initSmartRefreshLayout() {
        smartRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (position == 0)
                    mPresenter.getListNotDone(0);
                if (position == 1)
                    mPresenter.getListDone(0);
            }


        });
    }


    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showListNotDone(TodoData todoData) {
        final List<TodoData.DatasBean> newDataList = todoData.getDatas();

        todoAdapter.addData(newDataList);

        smartRefreshLayout.finishLoadMore();

    }

    @Override
    public void showListDone(TodoData todoData) {
        final List<TodoData.DatasBean> newDataList = todoData.getDatas();

        todoAdapter.addData(newDataList);

        smartRefreshLayout.finishLoadMore();
    }
}
