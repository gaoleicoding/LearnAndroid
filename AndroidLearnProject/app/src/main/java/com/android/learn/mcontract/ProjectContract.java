package com.android.learn.mcontract;

import com.android.learn.base.mmodel.ProjectListData;
import com.android.learn.base.mview.BaseView;

/**
 * Created by gaolei on 2018/6/18.
 */

public class ProjectContract {

    public interface Presenter {

        void getProjectInfo(int page, int cid);

        void onRefreshMore(int cid);

        void onLoadMore(int cid);
    }

    public interface View extends BaseView {

        void showProjectList(ProjectListData itemBeans, boolean isRefresh);
    }
}
