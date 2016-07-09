package com.example.benben.retrofit.net.api;

import com.example.benben.retrofit.model.MoveBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LiYuanXiong on 2016/7/5 22:09.
 * Email:3187683623@qq.com
 */
public interface MoveApi {


    @GET("index")
    Call<MoveBean> getMoveDetail(@Query("key")String key,
                                 @Query("title")String title,
                                 @Query("smode")String smode);

}
