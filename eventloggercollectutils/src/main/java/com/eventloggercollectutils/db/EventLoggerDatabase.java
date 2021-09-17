package com.eventloggercollectutils.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { EventLoggerData.class }, version = 1,exportSchema = false)
public abstract class EventLoggerDatabase extends RoomDatabase {
    private static final String DB_NAME = "EventLoggerDatabase.db";
    private static volatile EventLoggerDatabase instance;

    public static synchronized EventLoggerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static EventLoggerDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                EventLoggerDatabase.class,
                DB_NAME).build();
    }

    public abstract EventLoggerDataDao getEventLoggerDataDao();
}
