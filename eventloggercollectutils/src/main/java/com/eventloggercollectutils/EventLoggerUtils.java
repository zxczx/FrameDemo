package com.eventloggercollectutils;

import android.util.Log;

import com.EventLoggerCollectApplication;
import com.eventloggercollectutils.db.EventLoggerDatabase;

import io.reactivex.schedulers.Schedulers;

public class EventLoggerUtils {

    public static void upEventLoggerData(String key) {
        if (EventLoggerCollectApplication.getContext() == null) {
            return;
        }

        if (!EventLoggerCollectApplication.isOpen) {
            return;
        }

        EventLoggerDatabase.getInstance(EventLoggerCollectApplication.getContext()).
                getEventLoggerDataDao().getEventLoggerData(key).subscribeOn(Schedulers.io()).subscribe(eventLoggerData -> {
            eventLoggerData.setIsCheck(1);
            EventLoggerDatabase.getInstance(EventLoggerCollectApplication.getContext()).getEventLoggerDataDao().update(eventLoggerData);
        }, throwable -> {
            Log.i("EventLoggerUtils", throwable.getMessage() + "----key:" + key);
        });

    }

}


