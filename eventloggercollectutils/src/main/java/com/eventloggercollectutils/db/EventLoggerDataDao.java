package com.eventloggercollectutils.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface EventLoggerDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EventLoggerData... eventLoggerData);

    @Update
    int update(EventLoggerData... eventLoggerData);

    @Delete
    void delete(EventLoggerData... eventLoggerData);

    @Query("SELECT * FROM t_event_logger_data")
    Single<List<EventLoggerData>> getAllEventLoggerData();

    @Query("SELECT * FROM t_event_logger_data where isCheck = 1")
    Single<List<EventLoggerData>> getAlreadyEventLoggerData();

    @Query("SELECT * FROM t_event_logger_data where isImportance = 1")
    Single<List<EventLoggerData>> getNecessaryEventLoggerData();

    @Query("SELECT * FROM t_event_logger_data where isImportance = 1 and isCheck = 1")
    Single<List<EventLoggerData>> getAlreadyNecessaryEventLoggerData();

    @Query("SELECT * FROM t_event_logger_data where `key` = :key")
    Single<EventLoggerData> getEventLoggerData(String key);

    @Query("UPDATE T_EVENT_LOGGER_DATA set isCheck = 0 ")
    int reset();

}
