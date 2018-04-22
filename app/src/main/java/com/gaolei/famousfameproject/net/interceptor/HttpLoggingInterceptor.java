package com.gaolei.famousfameproject.net.interceptor;

import android.util.Log;


import com.gaolei.famousfameproject.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuhaiyang on 2016/9/6.
 */
public class HttpLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();

        if (BuildConfig.DEBUG) {
            Log.i("HTTP", String.format("----> %s %s on %s %n%s",
                    request.method(), request.url(), chain.connection(), request.headers()));
        }


        Response response = chain.proceed(request);

        long t2 = System.nanoTime();

        if (BuildConfig.DEBUG) {
            Log.i("HTTP", String.format("----> %s %s (%.1fms) %n%s", response.code(),
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        }


        return response;
    }
}