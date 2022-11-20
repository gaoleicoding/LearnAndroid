package com.android.base.thirdframe.glide

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


class ImageLoader private constructor() {

    /**
     * 使用Glide加载圆形ImageView(如头像)时，不要使用占位图
     *
     * @param context context
     * @param url     image url
     * @param iv      imageView
     */
    fun load(context: Context, url: String, iv: ImageView) {

        Glide.with(context).load(url).apply(options).transition(DrawableTransitionOptions().crossFade(500))
                .into(iv)
    }

    companion object {
        /*单例*/
        @Volatile
        private
        var INSTANCE: ImageLoader? = null
        lateinit var options: RequestOptions
        /*获取单例*/
        val instance: ImageLoader
            get() {
                if (INSTANCE == null) {
                    synchronized(ImageLoader::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = ImageLoader()
                            options = RequestOptions()
                                    //                .placeholder(R.drawable.ic_launcher)// 正在加载中的图片
                                    //                .error(R.drawable.video_error) // 加载失败的图片
                                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 磁盘缓存策略
                        }
                    }
                }
                return INSTANCE!!
            }

    }
}