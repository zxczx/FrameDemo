package com.example.framedemo;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDexApplication;

import com.example.framedemo.common.firebase.EventLogger;
import com.example.framedemo.common.google.billing.BillingClientLifecycle;
import com.example.framedemo.common.google.installreferrertool.InstallReferrerTool;
import com.example.framedemo.common.google.installreferrertool.ICollector;
import com.example.framedemo.downloader.Downloader;
import com.example.framedemo.module.DaggerAppComponent;
import com.example.framedemo.util.sp.FrameMMkvImpl;
import com.tencent.mmkv.MMKV;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;


public class FrameApplication extends MultiDexApplication implements HasSupportFragmentInjector, HasActivityInjector {

    private static FrameApplication sApp;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        DaggerAppComponent.builder().application(this).build().inject(this);
        InstallReferrerTool();

        //下载初始化
        Downloader.setup(this);

        //mmkv初始化
        FrameMMkvImpl.init(this,null);

        //Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }

    public static FrameApplication getApp() {
        return sApp;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    public BillingClientLifecycle getBillingClientLifecycle() {
        return BillingClientLifecycle.getInstance(this);
    }

    //渠道分析
    public void InstallReferrerTool() {
        InstallReferrerTool.getInstance().fetchReferrer(FrameApplication.getApp(), new ICollector() {
            @Override
            public void collect(Context context, String key) {
                EventLogger.logEvent(context, key);
            }

            @Override
            public void collectWithParams(Context context, String key, Bundle params) {
                EventLogger.logEvent(context, key, params);
            }
        });
    }


}
