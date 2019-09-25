package com.photo.comicsapplication.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    String JSONURL = "http://128.199.169.10:8080/";

    @GET("home/")
    Call<String> getString();
}
