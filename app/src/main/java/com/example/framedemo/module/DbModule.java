package com.example.framedemo.module;

import android.content.Context;

import androidx.room.Room;

import com.example.framedemo.FrameApplication;
import com.example.framedemo.db.FrameDb;
import com.example.framedemo.db.user.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 数据库的初始化
 */
@Module
public class DbModule {

    @Singleton @Provides
    public static Context provideAppContext(FrameApplication application) {
        return application.getApplicationContext();
    }

    @Singleton @Provides
    public static FrameDb provideFrameDb(Context applicationContext) {
        return Room.databaseBuilder(applicationContext, FrameDb.class,"frame.db").allowMainThreadQueries().build();
    }

    @Singleton @Provides
    public static UserDao provideWordDao(FrameDb db) {
        return db.userDao();
    }

}
