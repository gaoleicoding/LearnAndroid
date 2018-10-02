package com.android.learn.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.android.learn.R;
import com.android.learn.activity.ArticleDetailActivity;
import com.android.learn.adapter.DividerItemDecoration;
import com.android.learn.adapter.ArticleListAdapter;
import com.android.learn.base.fragment.BaseMvpFragment;
import com.android.learn.base.mmodel.BannerListData;
import com.android.learn.base.mmodel.ArticleListData;
import com.android.learn.base.mmodel.ArticleListData.FeedArticleData;
import com.android.learn.mcontract.HomeContract;
import com.android.learn.mpresenter.HomePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.project_recyclerview)
    RecyclerView project_recyclerview;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.smartRefreshLayout_home)
    SmartRefreshLayout smartRefreshLayout;
    private List<FeedArticleData> articleDataList;
    private ArticleListAdapter feedArticleAdapter;

    @Override
    public void initData(Bundle bundle) {

        Debug.startMethodTracing("traceview");

        Debug.stopMethodTracing();
    }

    @Override
    public void initView() {
        initSmartRefreshLayout();
        initRecyclerView();
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void reload() {
        mPresenter.getFeedArticleList(0);
        mPresenter.getBannerInfo();
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.getFeedArticleList(0);
        mPresenter.getBannerInfo();

    }


    @Override
    public void showArticleList(ArticleListData listData, boolean isRefresh) {
        final List<FeedArticleData> newDataList = listData.data.getDatas();
        if (isRefresh) {
//            mAdapter.replaceData(feedArticleListData.getDatas());
            smartRefreshLayout.finishRefresh(true);
        } else {
            articleDataList.addAll(newDataList);
            feedArticleAdapter.notifyItemRangeInserted(articleDataList.size() - newDataList.size(), newDataList.size());
            feedArticleAdapter.notifyDataSetChanged();
            smartRefreshLayout.finishLoadMore();
        }

        feedArticleAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", articleDataList.get(position).getLink());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBannerList(BannerListData itemBeans) {

        final List<String> linkList = new ArrayList<String>();
        List imageList = new ArrayList();
        List titleList = new ArrayList();
        int size = itemBeans.data.size();

        for (int i = 0; i < size; i++) {
            imageList.add(itemBeans.data.get(i).getImagePath());
            titleList.add(itemBeans.data.get(i).getTitle());
            linkList.add(itemBeans.data.get(i).getUrl());
        }
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(getActivity()).load(path).into(imageView);
            }
        });

        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        //设置banner动画效果
//        Tansformer.CubeIn
//        Transformer.CubeOut
//        Transformer.DepthPage
//        Transformer.FlipHorizontal
//        Transformer.FlipVertical
        banner.setBannerAnimation(Transformer.FlipHorizontal);
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(3000);//设置轮播时间
        banner.setImages(imageList);//设置图片源
        banner.setBannerTitles(titleList);//设置标题源

        banner.start();


        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", linkList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        articleDataList = new ArrayList<>();
        feedArticleAdapter = new ArticleListAdapter(getActivity(), articleDataList);
        project_recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        project_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        project_recyclerview.setAdapter(feedArticleAdapter);
    }

    //初始化下拉刷新控件
    private void initSmartRefreshLayout() {
//        smartRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
//        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.onLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.onRefreshMore();
            }
        });
    }
    public void scrollToTop(){
        project_recyclerview.scrollToPosition(0);
    }

    public void onResume() {
        super.onResume();

    }

    public void onDestroy() {
        super.onDestroy();
    }
}
