package com.android.learn.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


public class MainTabAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MainTabAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;


    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
//        Bundle bundle=new Bundle();
//        bundle.putString("title",titles[position]);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

