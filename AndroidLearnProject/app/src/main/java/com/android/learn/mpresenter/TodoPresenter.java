package com.android.learn.mpresenter;


import com.android.learn.base.mmodel.BaseData;
import com.android.learn.base.mmodel.BaseResponse;
import com.android.learn.base.mmodel.TodoData;
import com.android.learn.base.mpresenter.BasePresenter;
import com.android.learn.base.thirdframe.rxjava.BaseObserver;
import com.android.learn.base.utils.ResponseStatusUtil;
import com.android.learn.mcontract.TodoContract;

import java.util.Map;

import io.reactivex.Observable;


public class TodoPresenter extends BasePresenter<TodoContract.View> implements TodoContract.Presenter {
    int notDonePage = 1, donePage = 1;

    @Override
    public void getListNotDone(int type) {
        Observable observable = mRestService.getListNotDone(type, notDonePage);
        addSubscribe(observable, new BaseObserver<BaseResponse<TodoData>>(true) {

            @Override
            public void onNext(BaseResponse<TodoData> data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView.showListNotDone(data.getData());
                } else ResponseStatusUtil.handleResponseStatus(data);
            }

        });
        notDonePage++;
    }

    @Override
    public void getListDone(int type) {
        Observable observable = mRestService.getListDone(type, donePage);
        addSubscribe(observable, new BaseObserver<BaseResponse<TodoData>>(true) {

            @Override
            public void onNext(BaseResponse<TodoData> data) {
                if (data.errorCode == BaseData.SUCCESS) {
                    mView.showListDone(data.getData());
                } else ResponseStatusUtil.handleResponseStatus(data);
            }

        });
        donePage++;
    }

    @Override
    public void addTodo(Map<String, String> map) {
        Observable observable = mRestService.addTodo(map);

        addSubscribe(observable, new BaseObserver<BaseData>(false) {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                } else ResponseStatusUtil.handleResponseStatus(data);
            }
        });
    }

    @Override
    public void updateTodo(int id, Map<String, String> map) {
        Observable observable = mRestService.updateTodo(id, map);

        addSubscribe(observable, new BaseObserver<BaseData>(false) {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                } else ResponseStatusUtil.handleResponseStatus(data);
            }
        });
    }

    @Override
    public void deleteTodo(int id) {
        Observable observable = mRestService.deleteTodo(id);

        addSubscribe(observable, new BaseObserver<BaseData>(false) {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                } else ResponseStatusUtil.handleResponseStatus(data);
            }
        });
    }

    @Override
    public void updateTodoStatus(int id, int status) {
        Observable observable = mRestService.updateTodoStatus(id, status);

        addSubscribe(observable, new BaseObserver<BaseData>(false) {

            @Override
            public void onNext(BaseData data) {
                if (data.errorCode == BaseData.SUCCESS) {
                } else ResponseStatusUtil.handleResponseStatus(data);
            }
        });
    }
}
