package com.android.learn.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;



public class CustomViewPager extends ViewPager {


    public boolean canScroll=true;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPager(Context context) {
        super(context);
    }


    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }
}
