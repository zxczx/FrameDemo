package com.example.framedemo.module;

import com.example.framedemo.FrameApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * 在FrameApplication初始化所有modules，生成相应的配置文件
 */
@Singleton
@Component(modules = {
        DbModule.class,
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
