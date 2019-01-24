package com.android.learn.mcontract;



import com.android.learn.base.mmodel.TreeBean;
import com.android.learn.base.mview.BaseView;

import java.util.List;


public class KnowledgeContract {

    public interface View  extends BaseView {
        void showKnowledge(List<TreeBean> data);

    }

    public interface Presenter {
        void getKnowledge();

    }

}
