package com.divyansh.dflix.di.detailMovie;

import com.divyansh.dflix.network.DetailMovieApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DetailMovieModules {

    @Provides
    static DetailMovieApi getDetailMovieApi(Retrofit retrofit) {
        return retrofit.create(DetailMovieApi.class);
    }
}
