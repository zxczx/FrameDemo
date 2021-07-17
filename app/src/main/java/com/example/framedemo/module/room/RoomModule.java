package com.example.framedemo.module.room;

import com.example.framedemo.mvp.room.RoomPresenter;
import com.example.framedemo.repository.RoomRepository;
import com.example.framedemo.ui.roomDemo.adapter.RoomAdapter;

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
        return new RoomAdapter();
    }

}
