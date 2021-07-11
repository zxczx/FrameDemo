package com.example.framedemo.di;

import com.example.framedemo.FrameApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {
        ApiModule.class,
        RepositoryModule.class,
        AndroidInjectionModule.class,
        FragmentBuildersModule.class,
        ActivityBuildersModule.class
    })
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(FrameApplication application);
        AppComponent build();
    }
    void inject(FrameApplication application);
}