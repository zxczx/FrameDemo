package com.example.framedemo.di;
import com.example.framedemo.api.service.LoginService;
import com.example.framedemo.repository.LoginRepository;
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
}
