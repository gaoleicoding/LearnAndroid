package com.android.base.thirdframe.glide.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;

import java.security.MessageDigest;

/**
 * 裁剪图片为圆
 */
public class CircleTransformation extends BitmapTransformation {
    private final String TAG = getClass().getName();
 
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        //得到图片最小边
        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
        //计算图片起点
        int x = (toTransform.getWidth() - size) / 2;
        int y = (toTransform.getHeight() - size) / 2;
        //创建新的bitmaop
        Bitmap square = Bitmap.createBitmap(toTransform, x, y, size, size);
        //得到glide中BitmapPool的bitmap位图对象
        Bitmap circle = pool.get(size, size, Bitmap.Config.ARGB_8888);
 
        Canvas canvas = new Canvas(circle);
        Paint paint = new Paint();
        //设置TileMode的样式 CLAMP 拉伸 REPEAT 重复  MIRROR 镜像
        paint.setShader(new BitmapShader(square, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        //画圆
        canvas.drawCircle(r, r, r, paint);
        return circle;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlurTransformation) {
            return this == obj;
        }
        return false;
    }
 
    @Override
    public int hashCode() {
        return Util.hashCode(TAG.hashCode());
    }
 
    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(TAG.getBytes(CHARSET));
    }
}
