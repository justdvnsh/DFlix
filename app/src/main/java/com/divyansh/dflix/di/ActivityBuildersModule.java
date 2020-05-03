package com.divyansh.dflix.di;

import com.divyansh.dflix.di.auth.AuthViewModelsModule;
import com.divyansh.dflix.ui.auth.AuthActivity;
import com.divyansh.dflix.ui.auth.AuthViewModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();
}
