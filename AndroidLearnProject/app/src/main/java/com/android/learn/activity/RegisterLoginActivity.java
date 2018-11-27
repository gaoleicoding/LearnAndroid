package com.android.learn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.learn.R;
import com.android.learn.base.activity.BaseMvpActivity;
import com.android.learn.base.mpresenter.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class RegisterLoginActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_register_free)
    TextView tv_register_free;

    @BindView(R.id.tv_register)
    TextView tv_register;

    @BindView(R.id.layout_login)
    LinearLayout layout_login;
    @BindView(R.id.layout_register)
    LinearLayout layout_register;


    @BindView(R.id.et_register_phone_num)
    EditText et_register_phone_num;

    @BindView(R.id.et_register_password)
    EditText et_register_password;
    @BindView(R.id.et_register_confirm_password)
    EditText et_register_confirm_password;



    @BindView(R.id.et_login_phone_num)
    EditText et_login_phone_num;
    @BindView(R.id.et_login_password)
    EditText et_login_password;

    TextView tv_register_contract_content;
    boolean isInContract;

    boolean isInRegister = false, isInForgetPwd = false;
    String registerFlag;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.login));
        iv_back.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.tv_login, R.id.tv_register_free, R.id.tv_register})
    public void click(View view) {
        switch (view.getId()) {
//            case R.id.iv_back:
//
//                back();
//
//                break;
//            case R.id.tv_login:
//                String phoneNum = et_login_phone_num.getText().toString();
//                String password = et_login_password.getText().toString();
//                mPresenter.getLoginData(phoneNum, password);
//                break;
//            case R.id.tv_register_free:
//                layout_register.setVisibility(View.VISIBLE);
//                title.setText(getString(R.string.register));
//                isInRegister = true;
//                break;
//            case R.id.tv_forget_password:
//                layout_forget_password.setVisibility(View.VISIBLE);
//                title.setText(getString(R.string.forget_password));
//                isInForgetPwd = true;
//                break;
//
//            case R.id.tv_register_get_verify_code:
//
//                phoneNum = et_register_phone_num.getText().toString();
//                if (Utils.isMobileNO(phoneNum)) {
//                    if (tv_register_get_verify_code.isFinish()) {
//                        //发送验证码请求成功后调用
//                        tv_register_get_verify_code.start();
//                    }
//                    mPresenter.getMessageCode(phoneNum, 1);
//                }
//
//                break;
//
//            case R.id.tv_forget_pwd_get_verify_code:
//                phoneNum = et_forget_pwd_phone_num.getText().toString();
//                if (Utils.isMobileNO(phoneNum)) {
//                    if (tv_forget_pwd_get_verify_code.isFinish()) {
//                        //发送验证码请求成功后调用
//                        tv_forget_pwd_get_verify_code.start();
//                    }
//                    mPresenter.getMessageCode(phoneNum, 2);
//                }
//
//                break;
//            case R.id.tv_register:
//                registerFlag = "tv_register";
//
//                phoneNum = et_register_phone_num.getText().toString().trim();
//                String inviteCode = et_register_invite_code.getText().toString().trim();
//                password = et_register_password.getText().toString().trim();
//                String confirmPassword = et_register_confirm_password.getText().toString().trim();
//                String code = et_register_verify_code.getText().toString().trim();
//                if (!Utils.isMobileNO(phoneNum)) {
//                    return;
//                }
//                if ("".equals(inviteCode)) {
//                    Utils.showToast("请输入邀请码", true);
//                    return;
//                }
//                if (password.length() < 6) {
//                    Utils.showToast(getString(R.string.register_password_too_short), true);
//                    return;
//                }
//                if (!cb_register_contract.isChecked()) {
//                    Utils.showToast(getResources().getString(R.string.read_agree_register_contract), true);
//                    return;
//                }
//
//                mPresenter.getRegisterData(phoneNum, inviteCode, password, confirmPassword, code);
//                break;
//
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }





    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
