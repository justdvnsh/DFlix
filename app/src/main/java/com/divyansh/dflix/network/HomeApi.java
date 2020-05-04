package com.divyansh.dflix.network;

import com.divyansh.dflix.models.TrendingMovies;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeApi {

    @GET("/3/trending/movie/week")
    Flowable<TrendingMovies> getTrendingMovies(@Query("api_key") String apiKey);

    @GET("/3/trending/tv/week")
    Flowable<TrendingMovies> getTrendingTVShows(@Query("api_key") String apiKey);
}
