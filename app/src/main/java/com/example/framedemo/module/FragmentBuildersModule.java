package com.example.framedemo.module;


import com.example.framedemo.module.login.LoginModule;
import com.example.framedemo.module.my.MyModule;
import com.example.framedemo.ui.login.LoginFragment;
import com.example.framedemo.ui.my.MyFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector(modules = MyModule.class)
    abstract MyFragment contributeMyFragment();


}

