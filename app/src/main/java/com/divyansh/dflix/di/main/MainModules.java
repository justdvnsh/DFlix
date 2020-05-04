package com.divyansh.dflix.di.main;

import android.app.Application;

import com.divyansh.dflix.adapters.TrendingMoviesAdapter;
import com.divyansh.dflix.models.TrendingMovies;
import com.divyansh.dflix.network.HomeApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModules {

    @Provides
    static TrendingMoviesAdapter getTrendingMoviesAdapter(Application application) {
        return new TrendingMoviesAdapter(application);
    }

    @Provides
    static HomeApi getHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
    }
}
