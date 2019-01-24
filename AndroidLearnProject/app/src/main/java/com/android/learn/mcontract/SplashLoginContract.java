package com.android.learn.mcontract;

import com.android.learn.base.mmodel.RegisterLoginData;
import com.android.learn.base.mview.BaseView;

/**
 * Created by gaolei on 2018/6/18.
 */

public class SplashLoginContract {

    public interface Presenter {


        void login(String username, String password);


    }

    public interface View extends BaseView {

        void showLoginResData(RegisterLoginData loginResData);

    }

}
