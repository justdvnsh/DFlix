package com.divyansh.dflix.di;

import com.divyansh.dflix.di.auth.AuthViewModelsModule;
import com.divyansh.dflix.di.detailMovie.DetailMovieModules;
import com.divyansh.dflix.di.detailMovie.DetailMovieViewModelsModule;
import com.divyansh.dflix.di.main.MainFragmentBuilderModule;
import com.divyansh.dflix.di.main.MainModules;
import com.divyansh.dflix.di.main.MainViewModelsModule;
import com.divyansh.dflix.ui.auth.LoginActivity;
import com.divyansh.dflix.ui.auth.SignupActivity;
import com.divyansh.dflix.ui.detail.DetailMovieActivity;
import com.divyansh.dflix.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class
            }
    )
    abstract SignupActivity contributeSignupActivity();

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class
            }
    )
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(
            modules = {
                    MainModules.class,
                    MainFragmentBuilderModule.class,
                    MainViewModelsModule.class
            }
    )
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(
            modules = {
                    DetailMovieViewModelsModule.class,
                    DetailMovieModules.class
            }
    )
    abstract DetailMovieActivity contributeDetailMovieActivity();
}
