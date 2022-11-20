package com.android.base.thirdframe.retrofit


import com.android.base.application.CustomApplication
import com.android.base.thirdframe.retrofit.interceptor.DefaultHeaderInterceptor
import com.android.base.thirdframe.retrofit.interceptor.HttpLoggingInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class RetrofitDynamicProvider
//    public final String BASE_URL = "http://www.xuetangx.com/";

private constructor() {
    private val UTF8 = Charset.forName("UTF-8")
    private var mRetrofit: Retrofit? = null
    private var mOkHttpClient: OkHttpClient? = null
    private val restService: ApiService? = null


    fun builder(BASE_URL: String): RetrofitDynamicProvider? {
        netCachePath = CustomApplication.context.getExternalFilesDir("net_cache")!!.absolutePath
        if (mOkHttpClient == null) {
            mOkHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor())
                    //                    .addNetworkInterceptor(new HandleGzipInterceptor())
                    .addInterceptor(DefaultHeaderInterceptor())//请求连接中添加头信息
                    //                    .addNetworkInterceptor(new OnlineCacheInterceptor())//有网缓存拦截器
                    //                    .addInterceptor(new OfflineCacheInterceptor())//无网缓存拦截器
                    //                    .cache(new Cache(new File(netCachePath), 50 * 10240 * 1024))//缓存路径和空间设置
                    //                    .addInterceptor(new RetryIntercepter(4))//重试
                    //                    .addInterceptor(new GzipRequestInterceptor())//开启Gzip压缩

                    //                    .addInterceptor(new ProgressInterceptor())//请求url的进度
                    //                    .addInterceptor(new TokenInterceptor())//token过期，自动刷新Token
                    //                    .addInterceptor(new SignInterceptor())//所有的接口，默认需要带上sign,timestamp2个参数
                    //                    .addNetworkInterceptor(new ParamsEncryptInterceptor())//参数加密,一般针对表单中的字段和值进行加密，防止中途第三方进行窥探和篡改
                    .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(
                        CustomApplication.context)))
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()

        }
        //        if (mRetrofit == null) {
        mRetrofit = Retrofit.Builder()
                .client(mOkHttpClient!!)
                .baseUrl(BASE_URL)
                .addConverterFactory(CustomizeGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        //        }
        return sInstance
    }

    fun <T> createService(tClass: Class<T>): T {
        return mRetrofit!!.create(tClass)
    }

    companion object {
        @Volatile
        private var sInstance: RetrofitDynamicProvider? = null
       lateinit var netCachePath: String

        val instance: RetrofitDynamicProvider?
            get() {
                if (sInstance == null) {
                    synchronized(RetrofitDynamicProvider::class.java) {
                        if (sInstance == null) {
                            sInstance = RetrofitDynamicProvider()
                        }
                    }
                }
                return sInstance
            }
    }


}