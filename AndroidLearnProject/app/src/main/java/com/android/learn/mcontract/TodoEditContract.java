package com.android.learn.mcontract;

import com.android.learn.base.mmodel.BaseData;
import com.android.learn.base.mmodel.TodoData;
import com.android.learn.base.mview.BaseView;

import java.util.Map;

/**
 * Created by gaolei on 2018/6/18.
 */

public class TodoEditContract {

    public interface Presenter {

        void updateTodo(int id, Map<String, Object> map);

    }

    public interface View extends BaseView {


        void showUpdateTodo(BaseData todoData);
    }
}
