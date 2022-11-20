package com.android.base.thirdframe.retrofit.interceptor

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetryIntercepter(var maxRetry: Int//最大重试次数
) : Interceptor {
    private var retryNum = 0//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("retryNum=$retryNum")
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < maxRetry) {
            retryNum++
            println("retryNum=$retryNum")
            response = chain.proceed(request)
        }
        return response
    }
}