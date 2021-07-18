package com.example.framedemo.mvp.room;


import com.example.framedemo.db.user.User;
import com.example.framedemo.mvp.MvpPresenter;
import com.example.framedemo.mvp.MvpView;

import java.util.List;

public interface RoomContract {
    interface View extends MvpView {
        void usersData(List<User> user);
        void updateSuccess(Boolean is);
    }

    interface Presenter extends MvpPresenter<View> {
        void getUsers();
        void updateUsers(List<User> users);
    }
}
