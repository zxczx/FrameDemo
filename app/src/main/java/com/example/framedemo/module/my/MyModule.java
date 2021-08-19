package com.example.framedemo.module.my;

import android.util.Log;

import com.example.framedemo.ui.my.adapter.MyAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * MyModule 初始化My界面相关方法
 */
@Module
public class MyModule {
    @Provides
    public static MyAdapter provideMyAdapter() {
        return new MyAdapter();
    }
}
