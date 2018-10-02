package com.android.learn.mcontract;

import com.android.learn.base.mmodel.BannerListData;
import com.android.learn.base.mmodel.ArticleListData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class HomeContract {

    public interface Presenter {

        /**
         * Get feed article list
         */
        void getFeedArticleList(int num);

        void getBannerInfo();

        void onRefreshMore();

        void onLoadMore();
    }

    public interface View {

        void showArticleList(ArticleListData itemBeans, boolean isRefresh);

        void showBannerList(BannerListData itemBeans);
    }
}
