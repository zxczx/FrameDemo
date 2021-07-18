package com.example.framedemo.mvp.login;


import com.example.framedemo.db.user.User;
import com.example.framedemo.mvp.MvpPresenter;
import com.example.framedemo.mvp.MvpView;

public interface LoginContract {
    interface View extends MvpView {
        void login(User user);

    }

    interface Presenter extends MvpPresenter<View> {
        void login(String userName, String password);
    }
}
