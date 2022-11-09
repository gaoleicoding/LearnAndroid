package com.android.learn.base.thirdframe.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * @author lqx
 * 自定义GsonConverterFactory
 */
class CustomizeGsonConverterFactory private constructor(private val gson: Gson?) : Converter.Factory() {

    init {
        if (gson == null) {
            throw NullPointerException("gson == null")
        }
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?,
                                       retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val adapter = gson!!.getAdapter(TypeToken.get(type!!))
        return CustomizeGsonResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        val adapter = gson!!.getAdapter(TypeToken.get(type!!))
        return CustomizeGsonRequestBodyConverter(gson, adapter)
    }

    companion object {

        @JvmOverloads
        fun create(gson: Gson = Gson()): CustomizeGsonConverterFactory {
            return CustomizeGsonConverterFactory(gson)
        }
    }

}
