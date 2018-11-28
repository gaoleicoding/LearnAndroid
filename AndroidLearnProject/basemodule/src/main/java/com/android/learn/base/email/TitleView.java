package com.android.learn.base.email;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gaolei.basemodule.R;

public class TitleView extends FrameLayout {


    private TextView title;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_view, this);
        title = findViewById(R.id.title);

    }

    public void setTitleText(String text) {
        title.setText(text);
    }

}
