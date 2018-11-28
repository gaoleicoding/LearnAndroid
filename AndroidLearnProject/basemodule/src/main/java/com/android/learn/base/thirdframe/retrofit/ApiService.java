package com.android.learn.base.thirdframe.retrofit;


import com.android.learn.base.mmodel.BannerListData;
import com.android.learn.base.mmodel.ArticleListData;
import com.android.learn.base.mmodel.BaseData;
import com.android.learn.base.mmodel.RegisterLoginData;
import com.android.learn.base.mmodel.ProjectListData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {


    @GET("project/list/{page}/json")
    Observable<ProjectListData> getProjectListData(@Path("page") int page, @Query("cid") int cid);

    @GET("banner/json")
    Observable<BannerListData> getBannerListData();

    @GET("article/list/{num}/json")
    Observable<ArticleListData> getFeedArticleList(@Path("num") int num);

    @POST("user/login")
    @FormUrlEncoded
    Observable<RegisterLoginData> login(@Field("username") String username, @Field("password") String password);

    @POST("user/logout")
    @FormUrlEncoded
    Observable<BaseData> logout();

    @POST("user/register")
    @FormUrlEncoded
    Observable<RegisterLoginData> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

}
