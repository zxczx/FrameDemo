package com.example.framedemo;


import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDexApplication;

import com.example.framedemo.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class FrameApplication extends MultiDexApplication implements HasSupportFragmentInjector, HasActivityInjector {

    public static FrameApplication application;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        DaggerAppComponent.builder().application(this).build().inject(this);
    }



    public static Context context() {
        return application.getApplicationContext();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
