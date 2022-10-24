package com.android.learn.mpresenter;


import com.android.base.mmodel.BaseData;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.rxjava.BaseObserver;
import com.android.base.utils.ResponseStatusUtil;
import com.android.learn.mcontract.TodoEditContract;

import java.util.Map;

import io.reactivex.Observable;


public class TodoEditPresenter extends BasePresenter<TodoEditContract.View> implements TodoEditContract.Presenter {

    @Override
    public void updateTodo(int id, Map<String, Object> map) {
        Observable observable = mRestService.updateTodo(id, map);

        addSubscribe(observable, new BaseObserver<BaseData>() {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView.showUpdateTodo(data);
                } else ResponseStatusUtil.handleResponseStatus(data);
            }
        });
    }

}
