package com.android.learn.mcontract;

import com.android.base.mmodel.FeedArticleListData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class CollectContract {

    public interface Presenter {


        void getCollectList();

        void cancelCollectArticle(int position, int id);
    }

    public interface View {

        void showCollectList(FeedArticleListData feedArticleListData);

        void showCancelCollectArticle(int position, int id);
    }
}
