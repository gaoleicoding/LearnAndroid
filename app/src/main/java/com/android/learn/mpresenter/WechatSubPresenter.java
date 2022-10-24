package com.android.learn.mpresenter;


import com.android.base.mmodel.BaseData;
import com.android.base.mmodel.BaseResponse;
import com.android.base.mmodel.FeedArticleListData;
import com.android.base.mmodel.FeedArticleListData.FeedArticleData;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.base.utils.ResponseStatusUtil;
import com.android.learn.mcontract.WechatSubContract;

import io.reactivex.Observable;


public class WechatSubPresenter extends BasePresenter<WechatSubContract.View> implements WechatSubContract.Presenter {

    public int num=0;
    @Override
    public void getWxArtileById(int id) {
        Observable observable = mRestService.getWxArtileById(id, num);
        addSubscribe(observable, new BaseObserver<BaseResponse<FeedArticleListData>>() {

            @Override
            public void onNext(BaseResponse<FeedArticleListData> listData) {
                mView.showWxArticleById(listData.getData());
            }

        });
        num++;
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
