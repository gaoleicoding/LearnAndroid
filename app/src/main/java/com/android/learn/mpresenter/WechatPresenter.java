package com.android.learn.mpresenter;


import com.android.base.mmodel.BaseResponse;
import com.android.base.mmodel.WxArticle;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.learn.mcontract.WechatContract;

import java.util.List;

import io.reactivex.Observable;


public class WechatPresenter extends BasePresenter<WechatContract.View> implements WechatContract.Presenter {

    @Override
    public void getWxArticle() {
        Observable observable = mRestService.getWxArticle();
        addSubscribe(observable, new BaseObserver<BaseResponse<List<WxArticle>>>() {
            @Override
            public void onNext(BaseResponse<List<WxArticle>> datas) {
                mView.showWxArticle(datas.data);
            }
        });
    }

}
