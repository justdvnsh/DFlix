package com.divyansh.dflix.di.main;

import com.divyansh.dflix.ui.main.favorites.FavoriteFragment;
import com.divyansh.dflix.ui.main.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract FavoriteFragment contributeFavoriteFragment();
}
