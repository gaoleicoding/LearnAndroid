package com.gaolei.famousfameproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;


import com.gaolei.famousfameproject.R;
import com.gaolei.famousfameproject.api.RestService;
import com.gaolei.famousfameproject.api.UrlConfig;
import com.gaolei.famousfameproject.application.FJDApplication;
import com.gaolei.famousfameproject.fragment.BaseFragment;
import com.gaolei.famousfameproject.net.client.RestApiProvider;
import com.gaolei.famousfameproject.net.interceptor.SignInterceptor;
import com.gaolei.famousfameproject.utils.ToastUtil;

import butterknife.BindColor;
import butterknife.BindView;

public class MainActivity extends BaseActivity {
    //账户开通完成，跳首页投资
    public static final int TYPE_PAY_ACCOUNT_FINISH = 1001;
    //定期定制跳转投资
    public static final int TYPE_CUSTOM_FINISH = 1033;

    private static final int NOTIFICATION_POSITION = 2;
    private int mLastPosition;
    private FragmentPagerAdapter mFragmentAdapter;

    @BindView(R.id.bnv_main)
    BottomNavigationView mBottomView;
    @BindView(R.id.bvp_content)
    BottomNavigationViewPager mFragmentVp;
    @BindColor(R.color.blue_00)
    int mTitleColor;


    public int mType;
    RestService mRestService;
    public int mMinePositon = 0;
    private long mExitTime;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        //
        mRestService = RestApiProvider.getInstance().withInterceptor(new SignInterceptor(FJDApplication.getZMContext())).builder().getApiService(RestService.class);
        setStatusBarColor(mTitleColor);


        mBottomView.setTitleState(BottomNavigationView.TitleState.ALWAYS_SHOW);
        BottomNavigationItem itemHome = new BottomNavigationItem(R.string.bottomview_main_home, R.drawable.common_btn_ico_home_normal, R.drawable.common_btn_ico_home_press);
        BottomNavigationItem itemInvest = new BottomNavigationItem(R.string.bottomview_main_invest, R.drawable.common_btn_ico_finance_normal, R.drawable.common_btn_ico_finance_press);
        BottomNavigationItem itemMine = new BottomNavigationItem(R.string.bottomview_main_mine, R.drawable.common_btn_ico_my_normal, R.drawable.common_btn_ico_my_press);
        BottomNavigationItem itemHelp = new BottomNavigationItem(R.string.bottomview_main_help, R.drawable.common_btn_ico_my_normal, R.drawable.common_btn_ico_my_press);

        mBottomView.addItem(itemHome);
        mBottomView.addItem(itemInvest);
        mBottomView.addItem(itemMine);
        mBottomView.addItem(itemHelp);
        mBottomView.setItemForMenu(false);
        mFragmentVp.setOffscreenPageLimit(mBottomView.getChildCount() + 1);

        mFragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        mFragmentAdapter.addFragment(new HomeFragment());
        mFragmentAdapter.addFragment(new InvestFragment());
        mFragmentAdapter.addFragment(new MineFragment());

        HelpFragment helpFragment = new HelpFragment();
        Bundle helpBundle = new Bundle();
        helpBundle.putString(ConstantUtils.KEY_URL, UrlConfig.H5_HELP);
        helpFragment.setArguments(helpBundle);
        mFragmentAdapter.addFragment(helpFragment);
        mFragmentVp.setAdapter(mFragmentAdapter);
        onPagerSetRedPoint();
        mBottomView.setOnTabSelectedListener(new BottomNavigationView.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (wasSelected) {
                    return true;
                }
                if (mLastPosition != position) {
                    mFragmentVp.setCurrentItem(position, false);
                    BaseFragment currentFragment = mFragmentAdapter.getCurrentFragment();
                    currentFragment.willBeDisplayed();
                    mLastPosition = position;
                }
                return true;
            }
        });
    }

    /**
     * 根据viewpager来监听小红点显示
     */
    public void onPagerSetRedPoint() {
        mFragmentVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mMinePositon = position;
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 小红点请求
     */
    @Override
    protected void onResume() {
        super.onResume();
//        CommonUtils.setNotificationPointOfActivity(MainActivity.this, SharedPreferencesUtil.getSharedPreferences(this, ConstantUtils.SHAREDPREFERENCES_NAME), mMinePositon, mFragmentAdapter.getCount() - 1, mMineFragment.getImageview());
        if (mMinePositon == mFragmentAdapter.getCount() - 1) {
//            SharedPreferencesUtil.put(MainActivity.this, ConstantUtils.SHAREDPREFERENCES_NAME, ConstantUtils.SP_MESSAGE_MINE, false);
            hideNotification();
        }
    }


    /**
     * 显示小红点
     */
    public void showNotification() {
        if (mLastPosition != NOTIFICATION_POSITION) {
//            mBottomView.setNotification(NOTIFICATION_POSITION);
        }
    }

    /**
     * 隐藏小红点
     */
    public void hideNotification() {
//        mBottomView.closeNotification(NOTIFICATION_POSITION);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        //如果是修改密码后登陆返回则跳 我的
//        if (mType == LoginFragment.TYPE_CHANGE_LOGIN_PW || mType == LoginFragment.TYPE_CHANGE_BIND_PHONE) {
//            mFragmentVp.setCurrentItem(2);
//            mBottomView.setCurrentItem(2);
//        } else if (mType == MainActivity.TYPE_PAY_ACCOUNT_FINISH || mType == MainActivity.TYPE_CUSTOM_FINISH) {
//            mFragmentVp.setCurrentItem(1);
//            mBottomView.setCurrentItem(1);
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //moveTaskToBack(true);
    public void exitApp() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.showShortToast(this, getString(R.string.click_again_exit) + getString(R.string.app_name));
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
