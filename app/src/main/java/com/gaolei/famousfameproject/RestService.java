package com.gaolei.famousfameproject;


import com.weiyankeji.library.net.client.BaseServerResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by liuhaiyang on 2016/9/6.
 */
public interface RestService {
    @GET
    Call<BaseServerResponse> loadHomeList(@Url String url, @Query("strRecord") int strRecord, @Query("pageSize") int pageSize);

    @GET
    Call<BaseServerResponse> loadData(@Url String url, @QueryMap Map<String, Object> map);

    @GET
    Call<BaseServerResponse> loadData(@Url String url);


    @POST
    Call<BaseServerResponse> postData(@Url String url, @Body Object body);


    @FormUrlEncoded
    @POST
    Call<BaseServerResponse> login(@Url String url, @Field("username") String username,
                                   @Field("password") String pwd
    );
}
