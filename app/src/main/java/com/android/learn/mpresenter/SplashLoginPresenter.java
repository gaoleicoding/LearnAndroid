package com.android.learn.mpresenter;

import com.android.base.mmodel.RegisterLoginData;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.retrofit.ApiService;
import com.android.base.thirdframe.retrofit.RetrofitProvider;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.learn.mcontract.SplashLoginContract;

import io.reactivex.Observable;


public class SplashLoginPresenter extends BasePresenter<SplashLoginContract.View> implements SplashLoginContract.Presenter {

    @Override
    public void login(String account, String password) {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).login(account, password);
        addSubscribe(observable, new BaseObserver<RegisterLoginData>() {
            @Override
            public void onNext(RegisterLoginData data) {
                    mView.showLoginResData(data);

            }
        });
    }
}
