package com.android.learn.base.thirdframe.retrofit.interceptor.util

import android.util.Log

import com.android.learn.base.thirdframe.retrofit.interceptor.ProgressInterceptor

import java.io.IOException

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import okio.Source

/**
 * @author guolin
 * @since 2017/11/5
 */
class ProgressResponseBody(url: String, private val responseBody: ResponseBody) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    private var listener: ProgressListener? = null

    init {
        listener = ProgressInterceptor.LISTENER_MAP[url]
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(ProgressSource(responseBody.source()))
        }
        return bufferedSource
    }

    private inner class ProgressSource internal constructor(source: Source) : ForwardingSource(source) {

        internal var totalBytesRead: Long = 0

        internal var currentProgress: Int = 0

        @Throws(IOException::class)
        override fun read(sink: Buffer, byteCount: Long): Long {
            val  bytesRead = super.read(sink, byteCount)
            val fullLength = responseBody.contentLength()
            if (bytesRead == -1.toLong()) {
                totalBytesRead = fullLength
            } else {
                totalBytesRead += bytesRead
            }
            val progress = (100f * totalBytesRead / fullLength).toInt()
            Log.d(TAG, "download progress is $progress")
            if (listener != null && progress != currentProgress) {
                listener!!.onProgress(progress)
            }
            if (listener != null && totalBytesRead == fullLength) {
                listener = null
            }
            currentProgress = progress
            return bytesRead
        }
    }

    companion object {

        private val TAG = "ProgressResponseBody"
    }

}
