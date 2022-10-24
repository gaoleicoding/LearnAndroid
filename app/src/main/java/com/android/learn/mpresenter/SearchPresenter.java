package com.android.learn.mpresenter;


import com.android.learn.base.mmodel.BaseData;
import com.android.learn.base.mmodel.BaseResponse;
import com.android.learn.base.mmodel.FeedArticleListData;
import com.android.learn.base.mpresenter.BasePresenter;
import com.android.learn.base.thirdframe.rxjava.BaseObserver;
import com.android.learn.base.utils.ResponseStatusUtil;
import com.android.learn.mcontract.SearchContract;

import io.reactivex.Observable;


public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private int mCurrentPage = 0;

    @Override
    public void getFeedArticleList( String key) {
        Observable observable = mRestService.search(mCurrentPage, key);
        addSubscribe(observable, new BaseObserver<BaseResponse<FeedArticleListData>>() {
            @Override
            public void onNext(BaseResponse<FeedArticleListData> feedArticleListData) {
                mView.showArticleList(feedArticleListData.getData());
            }
        });
    }

    @Override
    public void onLoadMore(String key) {
        ++mCurrentPage;
        Observable observable = mRestService.search(mCurrentPage, key);
        addSubscribe(observable, new BaseObserver<BaseResponse<FeedArticleListData>>() {
            @Override
            public void onNext(BaseResponse<FeedArticleListData> feedArticleListData) {
                mView.showArticleList(feedArticleListData.getData());
            }
        });
    }
    @Override
    public void addCollectArticle(final int position, final FeedArticleListData.FeedArticleData feedArticleData) {
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
    public void cancelCollectArticle(final int position, final FeedArticleListData.FeedArticleData feedArticleData) {
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


}
