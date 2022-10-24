package com.android.base.thirdframe.glide;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.android.base.thirdframe.retrofit.interceptor.ProgressInterceptor;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.android.base.application.CustomApplication;

import java.io.InputStream;

import okhttp3.OkHttpClient;

@GlideModule
public final class CustomGlideModule extends AppGlideModule {

    public static String glideCachePath;
    @Override
    public boolean isManifestParsingEnabled() {
//    return super.isManifestParsingEnabled();
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 50 MB
        glideCachePath= CustomApplication.context.getExternalFilesDir("glide_cache").getAbsolutePath();
        builder.setDiskCache(
                new DiskLruCacheFactory( glideCachePath, diskCacheSizeBytes )
        );
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        //设置BitmapPool缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));

    }
    private String getStorageDirectory(Context context){
        return Environment.getExternalStorageDirectory().getPath()+"/"+context.getPackageName();
    }
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());  //拦截器
        OkHttpClient okHttpClient = builder.build();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
        super.registerComponents(context, glide, registry);
    }
}

