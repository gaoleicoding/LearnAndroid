package com.android.base.thirdframe.rxjava;

import com.android.base.utils.NetUtils;
import com.android.base.utils.Utils;
import com.android.base.view.CustomProgressDialog;

import java.io.IOException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private String errMsg = "";
    private boolean isCancelDialog = true;

    protected BaseObserver() {

    }

    public void setCancelDialog(boolean cancelDialog) {
        isCancelDialog = cancelDialog;
    }

    @Override

    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if(isCancelDialog)
        CustomProgressDialog.cancel();
        if (!NetUtils.isConnected()) {
            errMsg = "网络连接出错,请检查网络";

        } else if (e instanceof HttpException) {
            errMsg = "服务器访问异常(HttpException)";
        } else if (e instanceof IOException) {
            errMsg = "服务器访问异常(IOException)";
        }
        if (!"".equals(errMsg))
            Utils.showToast(errMsg, true);

    }

    @Override
    public void onComplete() {
        if(isCancelDialog)
        CustomProgressDialog.cancel();
    }

}
