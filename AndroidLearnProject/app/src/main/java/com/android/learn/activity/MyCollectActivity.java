package com.android.learn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.learn.R;
import com.android.learn.adapter.ArticleQuickAdapter;
import com.android.learn.adapter.DividerItemDecoration;
import com.android.learn.base.activity.BaseMvpActivity;
import com.android.learn.base.event.CancelCollectEvent;
import com.android.learn.base.mmodel.FeedArticleListData;
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData;
import com.android.learn.base.utils.LanguageUtil;
import com.android.learn.mcontract.CollectContract;
import com.android.learn.mpresenter.CollectPresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class MyCollectActivity extends BaseMvpActivity<CollectPresenter> implements CollectContract.View {


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.article_collect_recyclerview)
    RecyclerView article_collect_recyclerview;
    private List<FeedArticleData> articleDataList;
    private ArticleQuickAdapter feedArticleAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyCollectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.collect));
        iv_back.setVisibility(View.VISIBLE);
        initRecyclerView();
    }


    @Override
    public CollectPresenter initPresenter() {
        return new CollectPresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.getCollectList();
    }

    private void initRecyclerView() {
        articleDataList = new ArrayList<>();
        feedArticleAdapter = new ArticleQuickAdapter(this, articleDataList, "MyCollectActivity");
        article_collect_recyclerview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        article_collect_recyclerview.setLayoutManager(new LinearLayoutManager(this));

//        //解决数据加载完成后, 没有停留在顶部的问题
        article_collect_recyclerview.setFocusable(false);
        article_collect_recyclerview.setAdapter(feedArticleAdapter);
        feedArticleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.cancelCollectArticle(position, feedArticleAdapter.getData().get(position));
            }
        });
    }

    @Override
    public void showCollectList(FeedArticleListData feedArticleListData) {
        final List<FeedArticleData> newDataList = feedArticleListData.getDatas();

        feedArticleAdapter.addData(newDataList);
        feedArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyCollectActivity.this, ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", articleDataList.get(position).getLink());
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

    }

    @Override
    public void showCancelCollectArticle(int position, FeedArticleData feedArticleData) {
        feedArticleAdapter.remove(position);
        EventBus.getDefault().post(new CancelCollectEvent(feedArticleData.id));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        //语言切换
        super.attachBaseContext(LanguageUtil.setLocal(newBase));

    }

}
