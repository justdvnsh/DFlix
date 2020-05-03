package com.divyansh.dflix.network;

import com.divyansh.dflix.models.TrendingMovies;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeApi {

    @GET("/3/trending/all/day")
    Flowable<TrendingMovies> getTrendingMovies(@Query("api_key") String apiKey);
}
