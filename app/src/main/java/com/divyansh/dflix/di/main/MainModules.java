package com.divyansh.dflix.di.main;

import android.app.Application;

import com.divyansh.dflix.adapters.TrendingAdapter;
import com.divyansh.dflix.network.HomeApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModules {

    @Provides
    static TrendingAdapter getTrendingMoviesAdapter(Application application) {
        return new TrendingAdapter(application);
    }

    @Provides
    static HomeApi getHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
    }
}
