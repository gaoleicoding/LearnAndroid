package com.android.learn.base.thirdframe.retrofit.interceptor

import android.util.Log

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class OnlineCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val onlineCacheTime = 10//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
        val newResponse = response.newBuilder()
                .header("Cache-Control", "public, max-age=$onlineCacheTime")
                .removeHeader("Pragma")
                .build()
        Log.d("gaolei", "OnlineCacheInterceptor------------newResponse.code():" + newResponse.code())

        return newResponse
    }
}