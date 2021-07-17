package com.example.framedemo.module;

import com.example.framedemo.module.room.RoomModule;
import com.example.framedemo.ui.roomDemo.RoomDemoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module()
public  abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = RoomModule.class)
    abstract RoomDemoActivity contributeRoomDemoActivity();


}
