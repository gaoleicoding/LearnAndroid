package com.android.learn.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;

import com.android.base.utils.KeyboardUtils;


public class SearchViewUtils {

    public static void handleSearchView(final Context context, final View searchView, final EditText searchEt) {

        if (searchView.getVisibility() == View.VISIBLE) {
            //隐藏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Animator animatorHide = ViewAnimationUtils.createCircularReveal(searchView,
                        searchView.getWidth() - dip2px(context, 56),dip2px(context, 23),
                        //确定元的半径（算长宽的斜边长，这样半径不会太短也不会很长效果比较舒服）
                        (float) Math.hypot(searchView.getWidth(), searchView.getHeight()), 0);
                animatorHide.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        searchView.setVisibility(View.GONE);
                        KeyboardUtils.hideKeyboard(searchEt);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorHide.setDuration(300);
                animatorHide.start();
            } else {
                KeyboardUtils.hideKeyboard(searchEt);
                searchView.setVisibility(View.GONE);
            }
            searchEt.setText("");
        } else {
            //显示
            searchView.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Animator animator = ViewAnimationUtils.createCircularReveal(searchView,
                        searchView.getWidth() - dip2px(context, 56),
                        dip2px(context, 23), 0,
                        (float) Math.hypot(searchView.getWidth(), searchView.getHeight()));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        KeyboardUtils.showKeyboard(searchEt);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                animator.setDuration(300);
                animator.start();
            } else {
                searchView.setVisibility(View.VISIBLE);
                KeyboardUtils.showKeyboard(searchEt);
            }

        }
    }


    public static int dip2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5);
    }
}
