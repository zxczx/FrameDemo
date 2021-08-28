package com.amessage.eventloggercollectutils;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import com.EventLoggerCollectUtilsApplication;
import com.amessage.eventloggercollectutils.db.EventLoggerData;
import com.amessage.eventloggercollectutils.db.EventLoggerDatabase;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class EventLoggerUtils {

    public static void upEventLoggerData(String key) {
        if (EventLoggerCollectUtilsApplication.getContext() == null) {
            return;
        }
        if (!EventLoggerCollectUtilsApplication.isOpen) {
            return;
        }

        EventLoggerDatabase.getInstance(EventLoggerCollectUtilsApplication.getContext()).
                getEventLoggerDataDao().getEventLoggerData(key).subscribeOn(Schedulers.io()).subscribe(eventLoggerData -> {
            eventLoggerData.setIsCheck(1);
            EventLoggerDatabase.getInstance(EventLoggerCollectUtilsApplication.getContext()).getEventLoggerDataDao().update(eventLoggerData);
        }, throwable -> {
            Log.i("EventLoggerUtils", throwable.getMessage() + "----key:" + key);
        });

    }

}


