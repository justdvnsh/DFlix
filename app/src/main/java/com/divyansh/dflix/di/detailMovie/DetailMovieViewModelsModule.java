package com.divyansh.dflix.di.detailMovie;

import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.di.ViewModelKey;
import com.divyansh.dflix.ui.auth.LoginViewModel;
import com.divyansh.dflix.ui.auth.SignupViewModel;
import com.divyansh.dflix.ui.detail.DetailMovieViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class DetailMovieViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailMovieViewModel.class)
    public abstract ViewModel bindDetailMovieViewModel(DetailMovieViewModel viewModel);
}

