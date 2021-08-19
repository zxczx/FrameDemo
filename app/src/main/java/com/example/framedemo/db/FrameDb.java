package com.example.framedemo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.framedemo.db.user.User;
import com.example.framedemo.db.user.UserDao;


/**
 * 数据库持有者，并作为与应用持久关联数据的底层连接的主要访问点。
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class FrameDb extends RoomDatabase {

    public abstract UserDao userDao();

}
