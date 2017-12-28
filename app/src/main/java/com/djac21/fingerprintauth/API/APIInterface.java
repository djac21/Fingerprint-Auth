package com.djac21.fingerprintauth.API;

import com.djac21.fingerprintauth.Models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("everything?sources=techradar")
    Call<NewsResponse> getNews(@Query("apiKey") String apiKey);
}