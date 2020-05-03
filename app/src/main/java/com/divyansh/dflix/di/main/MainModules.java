package com.divyansh.dflix.di.main;

import com.divyansh.dflix.network.HomeApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModules {

    @Provides
    static HomeApi getHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
    }
}
