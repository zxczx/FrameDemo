package com.eventloggercollectutils.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface OrderDateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderDate... orderDates);

    @Update
    int update(OrderDate... orderDates);

    @Query("SELECT * FROM t_order_data where `key` = :key")
    Single<OrderDate> getOrderDate(String key);

    @Query("SELECT * FROM t_order_data order by type,time ")
    Single<List<OrderDate>> getAllOrderDate();

    @Query("UPDATE t_order_data set checkNum = 0,time=0,msg='' ")
    int reset();
}
