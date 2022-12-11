package com.android.base.thirdframe.retrofit.interceptor

import android.content.Context
import android.util.ArrayMap
import android.util.Log
import android.util.Pair
import androidx.multidex.BuildConfig
import com.android.base.utils.LogUtil
import com.android.base.utils.Utils
import okhttp3.*
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


class SignInterceptor : Interceptor {
    private val mContext: Context? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        //header add version
        val requestBuilder = request.newBuilder()
        requestBuilder.addHeader("version", BuildConfig.VERSION_NAME)
        requestBuilder.addHeader("platform", "ANDROID")

        if (request.method() != "POST") {
            return chain.proceed(request)
        }

        var bodyJson: JSONObject? = null
        val params = ArrayList<Pair<String, String>>()
        val arrayMap = ArrayMap<String, String>()

        var body: FormBody? = null
        try {
            body = request.body() as FormBody?
        } catch (c: ClassCastException) {
        }

        if (body != null) {

            val size = body.size()
            if (size > 0) {
                for (i in 0 until size) {
                    params.add(Pair(body.name(i), body.value(i)))
                }
            }
        } else {
            val requestBody = request.body()

            if (requestBody != null) {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                var charset: Charset? = Charset.forName("UTF-8")
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                }
                val paramsStr = buffer.readString(charset!!)
                if (BuildConfig.DEBUG) {
                    Log.i("HTTP", "request = $paramsStr")
                }

                try {
                    bodyJson = JSONObject(paramsStr)
                } catch (e: JSONException) {
                    LogUtil.e("JSON", "JsonObject err")
                }

                if (bodyJson != null) {
                    val it = bodyJson.keys()
                    while (it.hasNext()) {
                        val key = it.next()
                        var value: Any? = null
                        try {
                            value = bodyJson.get(key)
                        } catch (e: JSONException) {
                            LogUtil.e("JSON", "JsonObject get err")
                        }

                        if (key == "token") {
                            arrayMap["token"] = value.toString()
                            arrayMap["timestamp"] = (System.currentTimeMillis() / 1000).toString()

                            for (strKey in arrayMap.keys) {
                                params.add(Pair<String, String>(strKey, arrayMap[strKey]))
                            }
                        } else {
                            params.add(Pair(key, value.toString()))
                        }

                    }
                }

            }
        }

        params.add(Pair("MD5Key", MD5KEY))
        Collections.sort(params) { lhs, rhs -> lhs.second.compareTo(rhs.second) }

        val sb = StringBuilder()
        for (i in params.indices) {
            sb.append(params[i].second)
            if (i < params.size - 1) {
                sb.append("")
            }
        }

        val sign = Utils.md5Encode(sb.toString())
        params.add(Pair("sign", sign))

        for (pair in params) {
            if ((arrayMap.containsKey(pair.first) || pair.first == "sign") && bodyJson != null) {
                try {
                    bodyJson.put(pair.first, pair.second)
                } catch (e: JSONException) {
                    LogUtil.e("JSON", "JsonObject put err")
                }

            }
        }

        val postBodyString = bodyJson!!.toString()
        request = requestBuilder
                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                        postBodyString))
                .build()

        return chain.proceed(request)
    }

    companion object {

        private val MD5KEY = "base64:FgBvWS7+m3BPRnKemuoOwXEf7kvldmM+VOmJS5Iccxs="
    }

}
