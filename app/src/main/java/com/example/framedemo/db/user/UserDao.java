package com.example.framedemo.db.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertUser(List<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Delete
    int deleteUser(User user);

    @Query("SELECT * FROM t_user where uid = :uid")
    Single<List<User>> getUserByAId(String uid);

    @Query("SELECT * FROM t_user")
    Single<List<User>> getUsers();

}
