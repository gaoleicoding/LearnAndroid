package com.android.learn.mpresenter;


import com.android.base.mmodel.BaseData;
import com.android.base.mmodel.BaseResponse;
import com.android.base.mmodel.HotKeyData;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.base.utils.ResponseStatusUtil;
import com.android.learn.mcontract.MainActivityContract;

import java.util.List;

import io.reactivex.Observable;


public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter {

    private int mCurrentPage = 0;

    @Override
    public void getHotKey() {
        Observable observable = mRestService.getHotKey();
        addSubscribe(observable, new BaseObserver<BaseResponse<List<HotKeyData>>>() {

            @Override
            public void onNext(BaseResponse<List<HotKeyData>> datas) {
                if (datas.errorCode == BaseData.SUCCESS) {
                    mView.showHotKey(datas.data);
                } else ResponseStatusUtil.handleResponseStatus(datas);
            }

        });

    }

}
