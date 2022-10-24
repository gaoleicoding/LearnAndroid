package com.android.learn.mpresenter;


import com.android.base.mmodel.BannerListData;
import com.android.base.mmodel.BaseData;
import com.android.base.mmodel.BaseResponse;
import com.android.base.mmodel.FeedArticleListData.FeedArticleData;
import com.android.base.mmodel.FeedArticleListData;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.base.utils.ResponseStatusUtil;
import com.android.learn.mcontract.HomeContract;

import io.reactivex.Observable;


public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private boolean isRefresh = true;
    public int mCurrentPage = 0;

    @Override
    public void onRefreshMore() {
        Observable observable = mRestService.getFeedArticleList(-1);
        addSubscribe(observable, new BaseObserver<BaseResponse<FeedArticleListData>>() {
            @Override
            public void onNext(BaseResponse<FeedArticleListData> feedArticleListData) {
                mView.showArticleList(feedArticleListData.getData(), true);
            }
        });


    }

    @Override
    public void onLoadMore() {
        ++mCurrentPage;
        Observable observable = mRestService.getFeedArticleList(mCurrentPage);
        addSubscribe(observable, new BaseObserver<BaseResponse<FeedArticleListData>>() {
            @Override
            public void onNext(BaseResponse<FeedArticleListData> feedArticleListData) {
                mView.showArticleList(feedArticleListData.getData(), false);
            }
        });
    }

    @Override
    public void getFeedArticleList(int num) {
        Observable observable = mRestService.getFeedArticleList(num);
        addSubscribe(observable, new BaseObserver<BaseResponse<FeedArticleListData>>() {
            @Override
            public void onNext(BaseResponse<FeedArticleListData> feedArticleListData) {
                mView.showArticleList(feedArticleListData.getData(), false);
            }
        });
    }

    @Override
    public void getBannerInfo() {
        Observable observable = mRestService.getBannerListData();
        addSubscribe(observable, new BaseObserver<BannerListData>() {

            @Override
            public void onNext(BannerListData bannerListData) {
                mView.showBannerList(bannerListData);
            }

        });


    }


    @Override
    public void addCollectArticle(final int position, final FeedArticleData feedArticleData) {
        Observable observable = mRestService.addCollectArticle(feedArticleData.getId());
        addSubscribe(observable, new BaseObserver<BaseData>() {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    feedArticleData.setCollect(true);
                    mView.showCollectArticleData(position, feedArticleData);
                } else ResponseStatusUtil.handleResponseStatus(data);
            }

        });

    }

    @Override
    public void cancelCollectArticle(final int position, final FeedArticleData feedArticleData) {
        Observable observable = mRestService.cancelCollectArticle(feedArticleData.getId(), -1);
        addSubscribe(observable, new BaseObserver<BaseData>() {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    feedArticleData.setCollect(false);
                    mView.showCancelCollectArticleData(position, feedArticleData);
                } else ResponseStatusUtil.handleResponseStatus(data);
            }

        });
    }

    public void cancelCollectArticle(final int id) {
        Observable observable = mRestService.cancelCollectArticle(id, -1);
        addSubscribe(observable, new BaseObserver<BaseData>() {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView.showCancelCollectArticleData(id);
                } else ResponseStatusUtil.handleResponseStatus(data);
            }

        });
    }

}
