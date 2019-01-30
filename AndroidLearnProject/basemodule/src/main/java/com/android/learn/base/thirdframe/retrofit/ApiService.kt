package com.android.learn.base.thirdframe.retrofit


import com.android.learn.base.mmodel.BannerListData
import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.BaseResponse
import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.HotKeyData
import com.android.learn.base.mmodel.RegisterLoginData
import com.android.learn.base.mmodel.ProjectListData
import com.android.learn.base.mmodel.TodoData
import com.android.learn.base.mmodel.TreeBean
import com.android.learn.base.mmodel.WxArticle

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface ApiService {

    @get:GET("banner/json")
    val bannerListData: Observable<BannerListData>

    @get:GET("tree/json")
    val knowledgeTree: Observable<BaseResponse<List<TreeBean>>>

    @get:GET("hotkey/json")
    val hotKey: Observable<BaseResponse<List<HotKeyData>>>

    @get:GET("wxarticle/chapters/json")
    val wxArticle: Observable<BaseResponse<List<WxArticle>>>


    @GET("project/list/{page}/json")
    fun getProjectListData(@Path("page") page: Int, @Query("cid") cid: Int): Observable<ProjectListData>

    @GET("article/list/{num}/json")
    fun getFeedArticleList(@Path("num") num: Int): Observable<BaseResponse<FeedArticleListData>>

    @GET("article/list/{num}/json")
    fun getKnowledgeArticleList(@Path("num") num: Int, @Query("cid") cid: Int): Observable<BaseResponse<FeedArticleListData>>

    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<RegisterLoginData>

    @GET("user/logout/json")
    fun logout(): Observable<BaseData>

    @POST("user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String): Observable<RegisterLoginData>

    @POST("lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Observable<BaseData>

    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int): Observable<BaseResponse<FeedArticleListData>>

    @POST("lg/uncollect_originId/{id}/json")
    @FormUrlEncoded
    fun cancelCollectArticle(@Path("id") id: Int, @Field("originId") originId: Int): Observable<BaseData>

    @GET("wxarticle/list/{id}/{num}/json")
    fun getWxArtileById(@Path("id") id: Int, @Path("num") num: Int): Observable<BaseResponse<FeedArticleListData>>

    @POST("article/query/{num}/json")
    @FormUrlEncoded
    fun search(@Path("num") num: Int, @Field("k") key: String): Observable<BaseResponse<FeedArticleListData>>

    @GET("lg/todo/list/{type}/json")
    fun getTodoList(@Path("type") type: Int): Observable<BaseResponse<TodoData>>

    @POST("lg/todo/listnotdo/{type}/json/{page}")
    fun getListNotDone(@Path("type") type: Int, @Path("page") page: Int): Observable<BaseResponse<TodoData>>

    @POST("lg/todo/listdone/{type}/json/{page}")
    fun getListDone(@Path("type") type: Int, @Path("page") page: Int): Observable<BaseResponse<TodoData>>

    @POST("lg/todo/add/json")
    fun addTodo(@QueryMap map: Map<String, Any>): Observable<BaseData>

    @POST("lg/todo/update/{id}/json")
    fun updateTodo(@Path("id") id: Int, @QueryMap map: Map<String, Any>): Observable<BaseData>

    @POST("lg/todo/delete/{id}/json")
    fun deleteTodo(@Path("id") id: Int): Observable<BaseData>

    //status: 0或1，传1代表未完成到已完成，反之则反之。
    @POST("lg/todo/done/{id}/json")
    fun updateTodoStatus(@Path("id") id: Int, @Query("status") status: Int): Observable<BaseData>
}
