package com.android.learn.mpresenter;


import com.android.base.mmodel.BaseData;
import com.android.base.mmodel.RegisterLoginData;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.retrofit.ApiService;
import com.android.base.thirdframe.retrofit.RetrofitProvider;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.base.utils.ResponseStatusUtil;
import com.android.base.utils.Utils;
import com.android.learn.mcontract.RegisterLoginContract;

import io.reactivex.Observable;


public class RegisterLoginPresenter extends BasePresenter<RegisterLoginContract.View> implements RegisterLoginContract.Presenter {
    @Override
    public void login(String account, String password) {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).login(account, password);
        addSubscribe(observable, new BaseObserver<RegisterLoginData>() {
            @Override
            public void onNext(RegisterLoginData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView.showLoginResData(data);
                } else ResponseStatusUtil.handleResponseStatus(data);

            }
        });
    }

    @Override
    public void register(String account, String password, String repassword) {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).register(account, password, repassword);
        addSubscribe(observable, new BaseObserver<RegisterLoginData>() {
            @Override
            public void onNext(RegisterLoginData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    Utils.showToast("注册成功", true);
                    mView.showRegisterResData(data);
                } else ResponseStatusUtil.handleResponseStatus(data);

            }
        });
    }
}
