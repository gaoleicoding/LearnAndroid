package com.android.learn.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


import com.android.learn.fragment.HomeFragment


class MainTabAdapter(fm: FragmentManager, internal var fragments: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
//        Bundle bundle=new Bundle();
        //        bundle.putString("title",titles[position]);
        //        fragment.setArguments(bundle);
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}

