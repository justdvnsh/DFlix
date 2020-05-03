package com.divyansh.dflix.di.main;

import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.di.ViewModelKey;
import com.divyansh.dflix.ui.main.home.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel bindsHomeViewModel(HomeViewModel homeViewModel);
}
