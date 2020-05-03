package com.divyansh.dflix.di;

import android.app.Activity;
import android.app.Application;

import com.divyansh.dflix.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                ViewModelFactoryModule.class,
                AppModule.class
        }
)
public interface AppComponent  extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
