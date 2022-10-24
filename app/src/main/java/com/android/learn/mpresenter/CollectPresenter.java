package com.android.learn.mpresenter;


import com.android.base.mmodel.BaseData;
import com.android.base.mmodel.BaseResponse;
import com.android.base.mmodel.FeedArticleListData;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.base.utils.ResponseStatusUtil;
import com.android.learn.mcontract.CollectContract;

import io.reactivex.Observable;


public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {

    int page = 0;

    @Override
    public void getCollectList() {

        Observable observable = mRestService.getCollectList(page);
        addSubscribe(observable, new BaseObserver<BaseResponse<FeedArticleListData>>() {

            @Override
            public void onNext(BaseResponse<FeedArticleListData> data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView.showCollectList(data.getData());
                } else ResponseStatusUtil.handleResponseStatus(data);
            }

        });
        page++;
    }

    @Override
    public void cancelCollectArticle(final int position, final int id) {
//        int id = feedArticleData.getId();
        Observable observable = mRestService.cancelCollectArticle(id, -1);
        addSubscribe(observable, new BaseObserver<BaseData>() {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView.showCancelCollectArticle(position, id);
                } else ResponseStatusUtil.handleResponseStatus(data);
            }

        });
    }
}
