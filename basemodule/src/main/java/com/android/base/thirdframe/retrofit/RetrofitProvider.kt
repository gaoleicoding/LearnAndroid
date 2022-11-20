package com.android.base.thirdframe.retrofit


import com.android.base.application.CustomApplication
import com.android.base.thirdframe.retrofit.interceptor.HttpLoggingInterceptor
import com.android.base.thirdframe.retrofit.interceptor.OfflineCacheInterceptor
import com.android.base.thirdframe.retrofit.interceptor.OnlineCacheInterceptor
import com.android.base.thirdframe.retrofit.interceptor.RetryIntercepter
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

import java.io.File
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class RetrofitProvider private constructor() {

    private var mRetrofit: Retrofit? = null
    private var mOkHttpClient: OkHttpClient? = null
    private var restService: ApiService? = null
    val BASE_URL = "https://www.wanandroid.com/"
    lateinit var persistentCookieJar: PersistentCookieJar
    lateinit var sharedPrefsCookiePersistor: SharedPrefsCookiePersistor

    val apiService: ApiService?
        get() {
            if (restService == null)
                restService = mRetrofit!!.create(ApiService::class.java)
            return restService
        }


    fun builder(): RetrofitProvider? {
        netCachePath = CustomApplication.context.getExternalFilesDir("net_cache")!!.absolutePath
        if (mOkHttpClient == null) {
            sharedPrefsCookiePersistor = SharedPrefsCookiePersistor(CustomApplication.context)
            persistentCookieJar = PersistentCookieJar(SetCookieCache(), sharedPrefsCookiePersistor)
            mOkHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor())
                    .addNetworkInterceptor(OnlineCacheInterceptor())//有网缓存拦截器
                    .addInterceptor(OfflineCacheInterceptor())//无网缓存拦截器
                    .cache(Cache(File(netCachePath), (50 * 10240 * 1024).toLong()))//缓存路径和空间设置
                    .addInterceptor(RetryIntercepter(4))//重试
                    //                    .addInterceptor(new GzipRequestInterceptor())//开启Gzip压缩
                    //                    .addInterceptor(new DefaultHeaderInterceptor())//请求连接中添加头信息
                    //                    .addInterceptor(new ProgressInterceptor())//请求url的进度
                    //                    .addInterceptor(new TokenInterceptor())//token过期，自动刷新Token
                    //                    .addInterceptor(new SignInterceptor())//所有的接口，默认需要带上sign,timestamp2个参数
                    //                    .addNetworkInterceptor(new ParamsEncryptInterceptor())//参数加密,一般针对表单中的字段和值进行加密，防止中途第三方进行窥探和篡改
                    .cookieJar(persistentCookieJar)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .build()

        }
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                    .client(mOkHttpClient!!)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(CustomizeGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return sInstance
    }

    fun <T> createService(tClass: Class<T>): T {
        return mRetrofit!!.create(tClass)
    }

    companion object {
        @Volatile
        private var sInstance: RetrofitProvider? = null
       lateinit var netCachePath: String

        val instance: RetrofitProvider
            get() {
                if (sInstance == null) {
                    synchronized(RetrofitProvider::class.java) {
                        if (sInstance == null) {
                            sInstance = RetrofitProvider()
                        }
                    }
                }
                return sInstance!!
            }
    }
}