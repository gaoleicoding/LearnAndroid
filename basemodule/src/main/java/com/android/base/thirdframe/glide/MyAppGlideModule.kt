package com.android.base.thirdframe.glide

import android.content.Context
import android.os.Environment

import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.android.base.application.CustomApplication

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        //    return super.isManifestParsingEnabled();
        return false
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val diskCacheSizeBytes = 1024 * 1024 * 100 // 50 MB
        glideCachePath = CustomApplication.context.getExternalFilesDir("glide_cache")!!.absolutePath
        builder.setDiskCache(
                DiskLruCacheFactory(glideCachePath, diskCacheSizeBytes.toLong())
        )
        val maxMemory = Runtime.getRuntime().maxMemory().toInt()//获取系统分配给应用的总内存大小
        val memoryCacheSize = maxMemory / 8//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(LruResourceCache(memoryCacheSize.toLong()))
        //设置BitmapPool缓存内存大小
        builder.setBitmapPool(LruBitmapPool(memoryCacheSize.toLong()))

    }

    private fun getStorageDirectory(context: Context): String {
        return Environment.getExternalStorageDirectory().path + "/" + context.packageName
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }

    companion object {

        lateinit var glideCachePath: String
    }
}

