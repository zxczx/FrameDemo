package com.example.framedemo.mvp.login;


import com.example.framedemo.model.LoginModel;
import com.example.framedemo.mvp.MvpPresenter;
import com.example.framedemo.mvp.MvpView;

public interface LoginContract {
    interface View extends MvpView {
        void login(LoginModel loginModel);

    }

    interface Presenter extends MvpPresenter<View> {
        void login(String userName, String password);
    }
}
