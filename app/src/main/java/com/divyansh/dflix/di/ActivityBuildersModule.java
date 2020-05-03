package com.divyansh.dflix.di;

import com.divyansh.dflix.di.auth.AuthViewModelsModule;
import com.divyansh.dflix.ui.auth.LoginActivity;
import com.divyansh.dflix.ui.auth.SignupActivity;

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
}
