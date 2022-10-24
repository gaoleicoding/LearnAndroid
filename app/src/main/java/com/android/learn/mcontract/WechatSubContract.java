package com.android.learn.mcontract;

import com.android.base.mmodel.FeedArticleListData;
import com.android.base.mmodel.FeedArticleListData.FeedArticleData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class WechatSubContract {

    public interface Presenter {


        void getWxArtileById(int id);


        void addCollectArticle(int position, FeedArticleData feedArticleData);

        void cancelCollectArticle(int position, FeedArticleData feedArticleData);
    }

    public interface View {


        void showWxArticleById(FeedArticleListData datas);

        void showCollectArticleData(int position, FeedArticleData feedArticleData);

        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData);

        void showCancelCollectArticleData(int id);
    }
}
