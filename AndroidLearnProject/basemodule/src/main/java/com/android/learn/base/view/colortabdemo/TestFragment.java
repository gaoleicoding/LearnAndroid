package com.android.learn.base.view.colortabdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaolei.basemodule.R;

/**
 * Created by rookie on 2018/4/24.
 */

public class TestFragment extends android.support.v4.app.Fragment {
    private Context context;
    private TextView textView;

    public static TestFragment newInstance(int i) {
        TestFragment testFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", String.valueOf(i));
        testFragment.setArguments(bundle);
        return testFragment;
    }

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.fragment_test_layout, container, false);
        textView = rootView.findViewById(R.id.text);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView.setText(getArguments().getString("text"));
    }
}
