package com.divyansh.dflix.di.detailMovie;

import android.app.Application;

import com.divyansh.dflix.adapters.TrendingAdapter;
import com.divyansh.dflix.network.DetailMovieApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DetailMovieModules {

    @Provides
    static TrendingAdapter getTrendingMoviesAdapter(Application application) {
        return new TrendingAdapter(application);
    }

    @Provides
    static DetailMovieApi getDetailMovieApi(Retrofit retrofit) {
        return retrofit.create(DetailMovieApi.class);
    }
}
