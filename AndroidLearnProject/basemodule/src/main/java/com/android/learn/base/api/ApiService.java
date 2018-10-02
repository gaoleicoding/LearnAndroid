package com.android.learn.base.api;


import com.android.learn.base.mmodel.BannerListData;
import com.android.learn.base.mmodel.ArticleListData;
import com.android.learn.base.mmodel.LoginData;
import com.android.learn.base.mmodel.ProjectListData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    /*
        http://www.wanandroid.com/project/list/1/json?cid=294
        方法：GET
        参数：cid 分类的id，页码：拼接在链接中，从1开始。
        */
    @GET("project/list/{page}/json")
    Observable<ProjectListData> getProjectListData(@Path("page") int page, @Query("cid") int cid);

    /*
        http://www.wanandroid.com/banner/json
        广告栏数据
    */
    @GET("banner/json")
    Observable<BannerListData> getBannerListData();

    /**
     * 获取feed文章列表
     *
     * @param num 页数
     * @return feed文章列表数据
     */
    @GET("article/list/{num}/json")
    Observable<ArticleListData> getFeedArticleList(@Path("num") int num);

    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return 登陆数据
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<LoginData> getLoginData(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username   user name
     * @param password   password
     * @param repassword re password
     * @return 注册数据
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<LoginData> getRegisterData(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

}
