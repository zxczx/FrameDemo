package com.example.framedemo.di.login;


import com.example.framedemo.mvp.login.LoginPresenter;
import com.example.framedemo.repository.LoginRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    public static LoginPresenter provideLoginPresenter(LoginRepository repository) {
        return new LoginPresenter(repository);
    }
}
