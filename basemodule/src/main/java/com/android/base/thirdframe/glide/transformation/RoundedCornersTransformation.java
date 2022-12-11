package com.android.base.thirdframe.glide.transformation;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

public class RoundedCornersTransformation extends BitmapTransformation {

    private static final int VERSION = 1;
    //这个ID值 随意
    private static final String ID = "jp.wasabeef.glide.transformations.RoundedCornersTransformation." + VERSION;

    public static final int CORNER_NONE = 0;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    public static final int CORNER_TOP = CORNER_TOP_LEFT | CORNER_TOP_RIGHT;
    public static final int CORNER_BOTTOM = CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    public static final int CORNER_LEFT = CORNER_TOP_LEFT | CORNER_BOTTOM_LEFT;
    public static final int CORNER_RIGHT = CORNER_TOP_RIGHT | CORNER_BOTTOM_RIGHT;

    public static final int FIT_CENTER = 1;
    public static final int CENTER_CROP = 2;
    public static final int CENTER_INSIDE = 3;
    @IntDef({FIT_CENTER, CENTER_CROP, CENTER_INSIDE})
    public @interface ScaleType {}

    private float radius;
    private float diameter;
    private float margin;
    private int cornerType;

    private @ScaleType int scaleType;

    public RoundedCornersTransformation(int dpRadius, int marginDp) {
        this(dpRadius, marginDp, CORNER_ALL, FIT_CENTER);
    }

    public RoundedCornersTransformation(int dpRadius, int marginDp, int cornerType, @ScaleType int scaleType) {
        this.radius = Resources.getSystem().getDisplayMetrics().density * dpRadius;
        this.diameter = this.radius * 2;
        this.margin = Resources.getSystem().getDisplayMetrics().density * marginDp;
        this.cornerType = cornerType;
        this.scaleType = scaleType;

    }

        @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap;
        switch (scaleType) {
            case CENTER_CROP:
                bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
                break;
            case CENTER_INSIDE:
                bitmap = TransformationUtils.centerInside(pool, toTransform, outWidth, outHeight);
                break;
            case FIT_CENTER:
            default:
                bitmap = TransformationUtils.fitCenter(pool, toTransform, outWidth, outHeight);
                break;
        }

        return roundCrop(pool, bitmap);

    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        result.setHasAlpha(true);

        Canvas canvas = new Canvas(result);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        drawRoundRect(canvas, paint, source.getWidth(), source.getHeight());
        return result;
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
        float right = width - margin;
        float bottom = height - margin;

        canvas.drawRoundRect(new RectF(margin, margin, right, bottom), radius, radius, paint);

        //把不需要的圆角去掉
        int notRoundedCorners = cornerType ^ CORNER_ALL;
        if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
            clipTopLeft(canvas, paint, radius);
        }
        if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
            clipTopRight(canvas, paint, radius, right);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
            clipBottomLeft(canvas, paint, radius, bottom);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
            clipBottomRight(canvas, paint, radius, right, bottom);
        }
    }

    private static void clipTopLeft(final Canvas canvas, final Paint paint, float offset) {
        final RectF block = new RectF(0, 0, offset, offset);
        canvas.drawRect(block, paint);
    }

    private static void clipTopRight(final Canvas canvas, final Paint paint, float offset, float width) {
        final RectF block = new RectF(width - offset, 0, width, offset);
        canvas.drawRect(block, paint);
    }

    private static void clipBottomLeft(final Canvas canvas, final Paint paint, float offset, float height) {
        final RectF block = new RectF(0, height - offset, offset, height);
        canvas.drawRect(block, paint);
    }

    private static void clipBottomRight(final Canvas canvas, final Paint paint, float offset, float width, float height) {
        final RectF block = new RectF(width - offset, height - offset, width, height);
        canvas.drawRect(block, paint);
    }


    @NonNull
    @Override
    public String toString() {
        return "RoundedTransformation(radius=" + radius + ", margin=" + margin + ", diameter="
                + diameter + ", cornerType=" + cornerType + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RoundedCornersTransformation &&
                ((RoundedCornersTransformation) o).radius == radius &&
                ((RoundedCornersTransformation) o).diameter == diameter &&
                ((RoundedCornersTransformation) o).margin == margin &&
                ((RoundedCornersTransformation) o).cornerType == cornerType;
    }

    @Override
    public int hashCode() {
        return (int) (ID.hashCode() + radius * 10000 + diameter * 1000 + margin * 100 + cornerType * 10);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + radius + diameter + margin + cornerType).getBytes(CHARSET));
    }
}

