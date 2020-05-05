package com.divyansh.dflix.network;

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
}
