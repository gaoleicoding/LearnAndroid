package com.android.learn.base.thirdframe.retrofit;


import com.android.learn.base.mmodel.BannerListData;
import com.android.learn.base.mmodel.BaseData;
import com.android.learn.base.mmodel.BaseResponse;
import com.android.learn.base.mmodel.FeedArticleListData;
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
    Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(@Path("num") int num);

    @POST("user/login")
    @FormUrlEncoded
    Observable<RegisterLoginData> login(@Field("username") String username, @Field("password") String password);

    @GET("user/logout/json")
    Observable<BaseData> logout();

    @POST("user/register")
    @FormUrlEncoded
    Observable<RegisterLoginData> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @POST("lg/collect/{id}/json")
    Observable<BaseData> addCollectArticle(@Path("id") int id);

    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<FeedArticleListData>> getCollectList(@Path("page") int page);

    @POST("lg/uncollect_originId/{id}/json")
    @FormUrlEncoded
    Observable<BaseData> cancelCollectArticle(@Path("id") int id, @Field("originId") int originId);

}
