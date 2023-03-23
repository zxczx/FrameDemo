package com.eventloggercollectutils;

import android.util.Log;

import com.EventLoggerCollectApplication;
import com.eventloggercollectutils.db.EventLoggerDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

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


        EventLoggerDatabase.getInstance(EventLoggerCollectApplication.getContext()).
                getOrderDateDao().getOrderDate(key).subscribeOn(Schedulers.io()).subscribe(orderDate -> {
                    int num = orderDate.getCheckNum();
                    if (num==0){
                        orderDate.setTime(System.currentTimeMillis());
                        orderDate.setCheckNum(1);
                    }else {
                        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
                        String t=format.format(new Date());
                        orderDate.setCheckNum(1+num);
                        orderDate.setMsg(orderDate.getMsg()+"  "+"第 "+(num+1)+" 次时间："+t);
                    }
                    EventLoggerDatabase.getInstance(EventLoggerCollectApplication.getContext()).getOrderDateDao().update(orderDate);
                }, throwable -> {
                    Log.i("EventLoggerUtils", throwable.getMessage() + "----key:" + key);
                });

    }

}


