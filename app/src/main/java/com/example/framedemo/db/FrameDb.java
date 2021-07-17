package com.example.framedemo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.framedemo.db.user.User;
import com.example.framedemo.db.user.UserDao;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class FrameDb extends RoomDatabase {

    public abstract UserDao userDao();

}
