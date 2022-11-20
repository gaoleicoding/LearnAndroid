package com.android.learn.base.thirdframe.retrofit.interceptor.util

/**
 * @author guolin
 * @since 2017/11/5
 */
interface ProgressListener {

    /**
     * 当下载进度发生变化时，会回调此方法。
     * @param progress
     * 当前的下载进度，参数值的范围是0-100。
     */
    fun onProgress(progress: Int)

}