package com.example.framedemo.module;
import com.example.framedemo.api.service.LoginService;
import com.example.framedemo.db.user.UserDao;
import com.example.framedemo.repository.LoginRepository;
import com.example.framedemo.repository.RoomRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RepositoryModule {


    @Singleton
    @Provides
    public static LoginRepository provideLoginRepository(LoginService loginService) {
        return new LoginRepository(loginService);
    }

    @Singleton
    @Provides
    public static RoomRepository provideRoomRepository(UserDao userDao) {
        return new RoomRepository(userDao);
    }


}
