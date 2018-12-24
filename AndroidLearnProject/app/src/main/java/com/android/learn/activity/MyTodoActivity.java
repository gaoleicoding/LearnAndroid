package com.android.learn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Window;
import android.widget.TextView;


import com.android.learn.R;
import com.android.learn.base.activity.BaseActivity;
import com.android.learn.base.utils.StatusBarUtil;
import com.android.learn.fragment.TodoFragment;
import com.android.learn.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTodoActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    TodoFragment commentFragment;

    private List<Fragment> mFragments=new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyTodoActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_course_discuss);
        ButterKnife.bind(this);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.blue, true);
        initView();
    }

    @Override
    public void initView() {
        tv_title.setText(getString(R.string.course_discuss));
        discussFragment=new TodoFragment();
        mFragments.add(discussFragment);
        mFragments.add(commentFragment);
        CourseDiscussAdapter adapter = new CourseDiscussAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewPager);
        initTab();

    }

    private void initTab() {

        tabLayout.getTabAt(0).setCustomView(R.layout.tab_my_publish);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_my_praise_comment);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //标签选中之后执行的方法
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                title.setText(titles.get(tab.getPosition()));
            }

            //标签没选中
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //默认选中的Tab
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }
    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
    public class CourseDiscussAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments;

        public CourseDiscussAdapter(FragmentManager fm, List<Fragment> fragments) {
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
}
