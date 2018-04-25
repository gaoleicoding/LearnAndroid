package com.gaolei.famousfameproject.net.client;


import android.util.Log;


import com.gaolei.famousfameproject.BuildConfig;
import com.gaolei.famousfameproject.utils.JsonUtil;
import com.gaolei.famousfameproject.utils.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by liuhaiyang on 2016/9/6.
 */
public abstract class RestBaseCallBack<D> implements Callback<BaseServerResponse> {

    private static final int NET_ERROR_CODE = 445566;
    private static final String NET_ERROR_MSG = "网络出错，请检查网络连接";

    public RestBaseCallBack() {

    }

    @Override
    public void onResponse(Call<BaseServerResponse> call, retrofit2.Response<BaseServerResponse> response) {
        if (response == null) {
            return;
        }

        if (response.code() != HttpURLConnection.HTTP_OK) {
            onFailure(null, NET_ERROR_CODE, NET_ERROR_MSG);
            return;
        }

        if (response.body() == null) {
            return;
        }

        BaseServerResponse baseResponse = response.body();
        if (baseResponse == null) {
            return;
        }

        if (BuildConfig.DEBUG) {
            Log.i("HTTP", "HttpResponse: " + JsonUtil.encode(baseResponse));
        }

        if (baseResponse.code == 0) {
            D d = null;
            try {
                d = JsonUtil.decode(baseResponse.data, getType());
            } catch (Exception e) {
                LogUtils.e("HttpResponse:", e.toString());
            }
            onResponse(d);
        } else {
            onFailure(null, baseResponse.code, baseResponse.msg);
        }

    }

    @Override
    public void onFailure(Call<BaseServerResponse> call, Throwable t) {
        onFailure(t, NET_ERROR_CODE, NET_ERROR_MSG);
    }


    public abstract void onResponse(D data);

    public abstract void onFailure(Throwable error, int code, String msg);

    /**
     * 获取带泛型参数的父类参数类型
     *
     * @return 父类泛型参数
     */
    protected Type getType() {
        Type superclass = getClass().getGenericSuperclass();
        while ((superclass instanceof Class) && !superclass.equals(RestBaseCallBack.class)) {
            superclass = ((Class) superclass).getGenericSuperclass();
        }
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }
}
