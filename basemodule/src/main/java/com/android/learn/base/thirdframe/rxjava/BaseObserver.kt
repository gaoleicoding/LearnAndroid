package com.android.learn.base.thirdframe.rxjava

import android.app.Activity
import android.app.Dialog
import android.content.Context

import com.android.learn.base.activity.BaseActivity
import com.android.learn.base.application.CustomApplication
import com.android.learn.base.mview.BaseView
import com.android.learn.base.utils.NetUtils
import com.android.learn.base.utils.Utils
import com.android.learn.base.view.CustomProgressDialog

import java.io.IOException

import io.reactivex.observers.ResourceObserver
import retrofit2.HttpException

abstract class BaseObserver<T> : ResourceObserver<T> {

    protected var errMsg = ""
    private var isCancelDialog = true
    private val context: Context? = null
    //    public static Dialog prgressDialog;

    protected constructor(isShowDialog: Boolean) {
        // context在CustomProgressDialog中用到
        //        this.context = context;
        //        if (isShowDialog) {
        //            Activity activity=BaseActivity.context;
        //            CustomProgressDialog.show(activity);
        //        }
    }

    protected constructor(view: BaseView, isShowError: Boolean) {}


    fun setCancelDialog(cancelDialog: Boolean) {
        isCancelDialog = cancelDialog
    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {
        if (isCancelDialog)
            CustomProgressDialog.cancel()
        if (!NetUtils.isConnected) {
            errMsg = "网络连接出错,请检查网络"

        } else if (e is HttpException) {
            errMsg = "服务器访问异常(HttpException)"
        } else if (e is IOException) {
            errMsg = "服务器访问异常(IOException)"
        }
        if ("" != errMsg)
            Utils.showToast(errMsg, true)

    }

    override fun onComplete() {
        if (isCancelDialog)
            CustomProgressDialog.cancel()
    }

}
