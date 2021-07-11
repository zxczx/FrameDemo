package com.example.framedemo.di;


import com.example.framedemo.di.login.LoginModule;
import com.example.framedemo.ui.login.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginFragment contributeLoginFragment();
}

