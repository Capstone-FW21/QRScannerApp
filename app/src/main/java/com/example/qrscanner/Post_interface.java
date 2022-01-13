package com.example.qrscanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Post_interface {

    //String a = "record_data";
    //@Headers( "accept: application/json")
    @POST("record_data")
    //@FormUrlEncoded
    Call<String> createPost(@Query("email") String email, @Query("room_id") String room_id);
    //Call<Post> createPost(@Body Post post);
}
