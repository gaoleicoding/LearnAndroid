package com.android.learn.mcontract;

import com.android.learn.base.mmodel.TodoData;

import java.util.Map;

/**
 * Created by gaolei on 2018/6/18.
 */

public class TodoContract {

    public interface Presenter {


        void getListNotDone(int type );

        void getListDone(int type);

        void addTodo(Map<String, String> map);

        void updateTodo(int id, Map<String, String> map);

        void deleteTodo(int id);

        void updateTodoStatus(int id, int status);

    }

    public interface View {

        void showListNotDone(TodoData todoData);

        void showListDone(TodoData todoData);
    }
}
