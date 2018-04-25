package com.gaolei.famousfameproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.gaolei.famousfameproject.R;


/**
 * Created by lilei on 2017/8/10.
 */
public class FragmentContainerActivity extends AppCompatActivity {
    public static final String STAG_FRAGMENT_CLASS_NAME = "FRAGMENT_CLASS_NAME";
    public static final String STAG_FRAGMENT_ARGUMENTS = "FRAGMENT_ARGUMENTS";

    private FrameLayout mFlFragmentContainer;


    /**
     * @param mActivity
     * @param mContainerFragment
     * @return Intent
     * 功能：通过fragment.class得到intent
     */
    public static Intent getFragmentContainerActivityIntent(Activity mActivity, Class<? extends Fragment> mContainerFragment) {
        Intent mIntent = new Intent(mActivity, FragmentContainerActivity.class);
        mIntent.putExtra(STAG_FRAGMENT_CLASS_NAME, mContainerFragment.getName());
        return mIntent;
    }

    /**
     * @param mActivity
     * @param mContainerFragment
     * @param mArgs
     * @return Intent
     * 功能：通过fragment.class得到intent
     */

    public static Intent getFragmentContainerActivityIntent(Activity mActivity, Class<? extends Fragment> mContainerFragment, Bundle mArgs) {
        Intent intent = new Intent(mActivity, FragmentContainerActivity.class);
        intent.putExtra(STAG_FRAGMENT_CLASS_NAME, mContainerFragment.getName());
        intent.putExtra(STAG_FRAGMENT_ARGUMENTS, mArgs);
        return intent;
    }

    /**
     * @param savedInstanceState 反射实例化Fragment,参数传递
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        mFlFragmentContainer = (FrameLayout) findViewById(R.id.fl_fragment_container);
        Intent mIntent = getIntent();
        String mFragmentname = mIntent.getStringExtra(STAG_FRAGMENT_CLASS_NAME);
        if (TextUtils.isEmpty(mFragmentname)) {
            return;
        }
        Bundle mArgs = (Bundle) mIntent.getBundleExtra(STAG_FRAGMENT_ARGUMENTS);
        try {
            Class<Fragment> mFragmentClazz = (Class<Fragment>) this.getClass().getClassLoader().loadClass(mFragmentname);
            Fragment mFragment = mFragmentClazz.newInstance();
            if (mArgs != null) {
                mFragment.setArguments(mArgs);
            }
            addFragment(mFragment);
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param mNewFragment
     */
    public void addFragment(Fragment mNewFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_fragment_container, mNewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 根据栈中fragment结束自己
     */
    @Override
    public void onBackPressed() {
        boolean mbackResult = getSupportFragmentManager().popBackStackImmediate();
        int mfragmentSize = getSupportFragmentManager().getBackStackEntryCount();
        if (mfragmentSize == 0) {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
    }


}

