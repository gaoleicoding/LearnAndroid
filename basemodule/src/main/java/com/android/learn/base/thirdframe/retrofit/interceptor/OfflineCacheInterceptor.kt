package com.android.learn.base.thirdframe.retrofit.interceptor

import android.util.Log

import com.android.learn.base.application.CustomApplication
import com.android.learn.base.utils.NetUtils

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class OfflineCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetUtils.isConnected) {
            val offlineCacheTime = Integer.MAX_VALUE//离线的时候的缓存的过期时间
            request = request.newBuilder()
                    //                        .cacheControl(new CacheControl
                    //                                .Builder()
                    //                                .maxStale(60, TimeUnit.SECONDS)
                    //                                .onlyIfCached()
                    //                                .build()
                    //                        )
                    //            两种方式结果是一样的，写法不同
                    .header("Cache-Control", "public, only-if-cached, max-stale=$offlineCacheTime")
                    .build()
        }
        val response = chain.proceed(request)
        Log.d("gaolei", "OfflineCacheInterceptor------------response.code():" + response.code())
        if (response.code() == 504) {
            // 缓存已经不可使用
        }
        return response
    }
}