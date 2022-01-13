package com.example.qrscanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Post_interface {

    @Headers( "accept: application/json")
    @POST("record_data")
    Call<Post> createPost(@Body Post post);
}
