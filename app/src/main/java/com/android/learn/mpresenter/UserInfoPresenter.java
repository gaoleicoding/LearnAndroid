package com.android.learn.mpresenter;

import com.android.base.mmodel.BaseData;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.retrofit.ApiService;
import com.android.base.thirdframe.retrofit.RetrofitProvider;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.learn.mcontract.UserInfoContract;

import io.reactivex.Observable;

public class UserInfoPresenter extends BasePresenter<UserInfoContract.View> implements UserInfoContract.Presenter {

    @Override
    public void getLogoutData() {

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).logout();
        addSubscribe(observable, new BaseObserver<BaseData>() {
            @Override
            public void onNext(BaseData baseData) {
            }
        });
    }


}
