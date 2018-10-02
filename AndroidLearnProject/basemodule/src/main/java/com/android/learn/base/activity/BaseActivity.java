package com.android.learn.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaolei.basemodule.R;
import com.gaolei.basemodule.R2;
import com.android.learn.base.utils.ExitAppUtils;
import com.android.learn.base.utils.PermissionUtil;
import com.android.learn.base.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.learn.base.utils.PermissionUtil.PERMISSION_CODE;


/**
 * Created by gaolei on 2018/4/26.
 */

public abstract class BaseActivity extends BasePermisssionActivity implements View.OnClickListener {
    private PermissionUtil.RequestPermissionCallBack mRequestPermissionCallBack;

    public static Activity context;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        ExitAppUtils.getInstance().addActivity(this);

        context=this;
        setStatusBarColor(R.color.app_color);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            bundle = savedInstanceState;
        }
        initData(bundle);

    }


    protected abstract int getLayoutId();

    protected abstract void initData(Bundle bundle);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(int resColor) {
        StatusBarUtil.setWindowStatusBarColor(this, resColor, true);
    }

//    /**
//     * 发起权限请求
//     *
//     * @param context
//     * @param permissions
//     * @param callback
//     */
//
//    public void requestPermission(final Context context,
//                                  PermissionUtil.RequestPermissionCallBack callback, final String... permissions) {
//        this.mRequestPermissionCallBack = callback;
//
//        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
//        boolean isAllGranted = true;
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
//                isAllGranted = false;
//                ActivityCompat.requestPermissions(((Activity) context), permissions, PERMISSION_CODE);
//            }
//        }
//        if (isAllGranted) {
//            mRequestPermissionCallBack.granted();
//        }
//    }
//
//    /**
//     * 权限请求结果回调
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        boolean hasAllGranted = true;
//
//        switch (requestCode) {
//            case PERMISSION_CODE: {
//                for (int i = 0; i < grantResults.length; ++i) {
//                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        hasAllGranted = false;
//                        //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
//                        // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
//                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                            PermissionUtil.requestForeverDenyDialog(BaseActivity.this, permissions);
//
//                        } else {
//                            PermissionUtil.requestDenyDialog(BaseActivity.this, permissions);
//
//                            //用户拒绝权限请求，但未选中“不再提示”选项
//                        }
//                        mRequestPermissionCallBack.denied();
//                    }
//                }
//                if (grantResults.length > 0 && hasAllGranted) {
//                    mRequestPermissionCallBack.granted();
//                }
//            }
//        }
//    }
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    protected void onDestroy() {
        super.onDestroy();
        context=null;
        ExitAppUtils.getInstance().delActivity(this);

    }


}
