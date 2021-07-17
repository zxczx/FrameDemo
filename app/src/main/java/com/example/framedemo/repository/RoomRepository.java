package com.example.framedemo.repository;

import com.example.framedemo.db.user.User;
import com.example.framedemo.db.user.UserDao;

import java.util.List;

import io.reactivex.Single;

public class RoomRepository {
    public UserDao mUserDao;

    public RoomRepository(UserDao userDao) {
        mUserDao = userDao;
    }

    public Single<List<User>> getUsers() {

        return mUserDao.getUsers();
    }


    public Single<List<Long>> insertUsers(List<User> users) {
        return Single.fromCallable(() -> mUserDao.insertUser(users));

    }

}
