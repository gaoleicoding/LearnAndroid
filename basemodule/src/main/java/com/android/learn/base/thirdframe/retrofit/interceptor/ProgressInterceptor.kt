package com.android.learn.base.thirdframe.retrofit.interceptor

import com.android.learn.base.thirdframe.retrofit.interceptor.util.ProgressListener
import com.android.learn.base.thirdframe.retrofit.interceptor.util.ProgressResponseBody

import java.io.IOException
import java.util.HashMap

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * @author guolin
 * @since 2017/11/5
 */
class ProgressInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val url = request.url().toString()
        val body = response.body()
        return response.newBuilder().body(ProgressResponseBody(url, body!!)).build()
    }

    companion object {

        val LISTENER_MAP: MutableMap<String, ProgressListener> = HashMap()

        fun addListener(url: String, listener: ProgressListener) {
            LISTENER_MAP[url] = listener
        }

        fun removeListener(url: String) {
            LISTENER_MAP.remove(url)
        }
    }

}
