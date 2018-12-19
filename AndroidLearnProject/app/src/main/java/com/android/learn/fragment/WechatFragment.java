package com.android.learn.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.learn.R;
import com.android.learn.base.fragment.BaseMvpFragment;
import com.android.learn.base.mpresenter.BasePresenter;
import com.android.learn.base.view.colortabdemo.ColorClipTabLayout;
import com.android.learn.base.view.colortabdemo.TestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class WechatFragment extends BaseMvpFragment {

    @BindView(R.id.tab_layout)
    ColorClipTabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    List<Fragment> list= new ArrayList();
    List<String> list2= new ArrayList();
    @Override
    protected void loadData() {

    }

    @Override
    public void initData(Bundle bundle) {
        for (int i=0;i<9;i++) {
            list.add(TestFragment.newInstance(i));
            list2.add("栏目" + i);
        }
        CustomPagerAdapter adapter = new CustomPagerAdapter(getFragmentManager(),list);
        view_pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(view_pager);
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_wechat;
    }

    @Override
    public void reload() {
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
    public class CustomPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public CustomPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
            fm.beginTransaction().commitAllowingStateLoss();
        }

        @Override
        public Fragment getItem(int position) {
            return this.mFragments.get(position);
        }

        @Override
        public int getCount() {
            return this.mFragments.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return list2.get(position);
        }
    }
}
