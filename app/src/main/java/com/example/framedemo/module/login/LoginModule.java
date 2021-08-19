package com.example.framedemo.module.login;


import com.example.framedemo.mvp.login.LoginPresenter;
import com.example.framedemo.repository.LoginRepository;

import dagger.Module;
import dagger.Provides;

/**
 * LoginModule 初始化 Login界面相关方法
 */

@Module
public class LoginModule {

    @Provides
    public static LoginPresenter provideLoginPresenter(LoginRepository repository) {
        return new LoginPresenter(repository);
    }
}
