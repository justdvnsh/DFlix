package com.divyansh.dflix.di.auth;

import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.di.ViewModelKey;
import com.divyansh.dflix.ui.auth.LoginViewModel;
import com.divyansh.dflix.ui.auth.SignupViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel.class)
    public abstract ViewModel bindSignupViewModel(SignupViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);
}
