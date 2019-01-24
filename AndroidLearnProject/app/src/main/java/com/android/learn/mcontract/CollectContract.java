package com.android.learn.mcontract;

import com.android.learn.base.mmodel.FeedArticleListData;
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData;
import com.android.learn.base.mview.BaseView;

/**
 * Created by gaolei on 2018/6/18.
 */

public class CollectContract {

    public interface Presenter {


        void getCollectList();

        void cancelCollectArticle(int position, int id);
    }

    public interface View extends BaseView{

        void showCollectList(FeedArticleListData feedArticleListData);

        void showCancelCollectArticle(int position, int id);
    }
}
