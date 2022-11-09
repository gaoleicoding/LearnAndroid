package com.android.learn.base.thirdframe.retrofit

import com.android.learn.base.utils.LogUtil
import com.google.gson.Gson
import com.google.gson.TypeAdapter

import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * @param <T>
 * @author lqx
</T> */
class CustomizeGsonResponseBodyConverter<T> internal constructor(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {
    internal var TAG = "CustomizeGsonResponseBodyConverter"

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {

        val responseBodyStr = value.string()
        LogUtil.d(TAG, "responseBodyStr---------------$responseBodyStr")


        return adapter.fromJson(responseBodyStr)
    }
}
