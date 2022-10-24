package com.android.learn.mcontract;

import com.android.base.mmodel.WxArticle;

import java.util.List;

/**
 * Created by gaolei on 2018/6/18.
 */

public class WechatContract {

    public interface Presenter {

        void getWxArticle();

    }

    public interface View {

        void showWxArticle(List<WxArticle> list);


    }
}
