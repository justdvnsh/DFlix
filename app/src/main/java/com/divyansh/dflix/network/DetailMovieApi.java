package com.divyansh.dflix.network;

import com.divyansh.dflix.models.TrendingMovies;
import com.divyansh.dflix.models.detailMovie.DetailMovie;
import com.divyansh.dflix.models.detailTv.DetailTv;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailMovieApi {

    @GET("/3/movie/{id}")
    Flowable<DetailMovie> getMovieDetails(
            @Path("id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/tv/{id}")
    Flowable<DetailTv> getTvDetails(
            @Path("id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/{type}/{id}/similar")
    Flowable<TrendingMovies> getSimilar(
            @Path("type") String type,
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
