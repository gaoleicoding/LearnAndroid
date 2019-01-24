package com.android.learn.mcontract;

import com.android.learn.base.mmodel.BaseData;
import com.android.learn.base.mmodel.TodoData;
import com.android.learn.base.mview.BaseView;

import java.util.Map;

/**
 * Created by gaolei on 2018/6/18.
 */

public class TodoContract {

    public interface Presenter {


        void getListNotDone(int type);

        void getListDone(int type);

        void deleteTodo(int id);

        void updateTodoStatus(int id, int status);

    }

    public interface View extends BaseView {

        void showListNotDone(TodoData todoData);

        void showListDone(TodoData todoData);
        void showUpdateTodoStatus(BaseData todoData);
    }
}
