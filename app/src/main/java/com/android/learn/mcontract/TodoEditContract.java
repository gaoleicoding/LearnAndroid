package com.android.learn.mcontract;

import com.android.base.mmodel.BaseData;

import java.util.Map;

/**
 * Created by gaolei on 2018/6/18.
 */

public class TodoEditContract {

    public interface Presenter {

        void updateTodo(int id, Map<String, Object> map);

    }

    public interface View {


        void showUpdateTodo(BaseData todoData);
    }
}
