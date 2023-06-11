package com.example.h071211009_finalmobile.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "016effe494244c077487d2f9f43b9bd3";

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

