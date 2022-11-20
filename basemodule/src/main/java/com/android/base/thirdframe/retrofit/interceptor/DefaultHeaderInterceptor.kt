package com.android.base.thirdframe.retrofit.interceptor

import java.io.IOException

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class DefaultHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalrequest = chain.request()//原始request
        val headers = Headers.Builder()
                .add("name", "jason")
                .add("age", "27")
                .add("token", "dfedsdfsdfffdd12dsef123sdfef1s2dfe")
                .build()//构造一个Headers
        val request = originalrequest.newBuilder().headers(headers).build()//注意这行代码别写错了
        return chain.proceed(request)
    }
}