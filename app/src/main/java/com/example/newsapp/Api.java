package com.example.newsapp;

import com.example.newsapp.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("top-headlines")
    Call<ResponseModel> getPosts(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );


}
