package com.example.framedemo.module.room;

import android.util.Log;

import com.example.framedemo.mvp.room.RoomPresenter;
import com.example.framedemo.repository.RoomRepository;
import com.example.framedemo.ui.my.roomDemo.adapter.RoomAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    @Provides
    public static RoomPresenter provideRoomPresenter(RoomRepository repository) {
        return new RoomPresenter(repository);
    }

    @Provides
    public static RoomAdapter provideRoomAdapter() {
        Log.i("zxc","------------RoomAdapter----------");
        return new RoomAdapter();
    }

}
