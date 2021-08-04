package com.example.framedemo.module.my;

import android.util.Log;

import com.example.framedemo.ui.my.adapter.MyAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {
    @Provides
    public static MyAdapter provideMyAdapter() {
        Log.i("zxc","-----------MyModule---------");
        return new MyAdapter();
    }
}
