package com.example.h071211009_finalmobile.API;

import com.example.h071211009_finalmobile.Model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowAPIService {
    @GET("tv/on_the_air")
    Call<TvShowResponse> getAiringTodayTV(
            @Query("api_key") String apiKey
    );
}
