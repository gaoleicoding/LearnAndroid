package com.gaolei.famousfameproject.mpresenter;



import com.gaolei.famousfameproject.api.RestService;
import com.gaolei.famousfameproject.application.FJDApplication;
import com.gaolei.famousfameproject.net.client.BaseServerResponse;
import com.gaolei.famousfameproject.net.client.RestApiProvider;
import com.gaolei.famousfameproject.net.interceptor.SignInterceptor;

import retrofit2.Call;

/**
 * Created by liuhaiyang on 2017/8/2.
 */

public abstract class BasePresenter<V> {

    public V mView;
    public Call<BaseServerResponse> mCall;
    protected RestService mRestService = RestApiProvider.getInstance().withInterceptor(new SignInterceptor(FJDApplication.getZMContext())).builder().getApiService(RestService.class);

    /**
     * 绑定接口
     *
     * @param view
     */
    public void attach(V view) {
        this.mView = view;
    }

    /**
     * 释放接口
     */
    public void dettach() {
        this.mView = null;
        if (mCall != null) {
            this.mCall.cancel();
        }
    }
}
