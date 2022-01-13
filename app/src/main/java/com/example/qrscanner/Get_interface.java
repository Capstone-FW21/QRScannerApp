package com.example.qrscanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Get_interface {

    @GET("posts")
    Call<List<Get>> getPosts();

    @GET("student")
    Call<Get> getStudent();
}
