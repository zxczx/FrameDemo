package com;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.eventloggercollectutils.EventLoggerCollectInitSuccess;
import com.eventloggercollectutils.db.EventLoggerData;
import com.eventloggercollectutils.db.EventLoggerDatabase;

import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 需要在主Application初始化
 * 首次进入EventLoggerCollectActivity 记录打点开始
 * 使用直接调用EventLoggerUtils.upEventLoggerData
 */
public class EventLoggerCollectApplication {

    private static Context mContext;

    private static volatile EventLoggerCollectApplication instance = null;

    public static boolean isOpen = false;

    private EventLoggerCollectInitSuccess mEventLoggerCollectInitSuccess;


    public static EventLoggerCollectApplication getInstance() {
        if (instance == null) {
            synchronized (EventLoggerCollectApplication.class) {
                if (instance == null) {
                    instance = new EventLoggerCollectApplication();
                }
            }
        }
        return instance;
    }

    public void initialize(@NonNull Context context, EventLoggerCollectInitSuccess eventLoggerCollectInitSuccess) {
        mContext = context;
        mEventLoggerCollectInitSuccess = eventLoggerCollectInitSuccess;
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoggerDatabase.getInstance(mContext).getEventLoggerDataDao().getAllEventLoggerData().subscribe(
                        eventLoggerData -> {
                            if (eventLoggerData != null && eventLoggerData.size() > 0) {
                                mEventLoggerCollectInitSuccess.initFinishCallBack();
                            } else {
                                initDataFromSheet();
                            }
                        });
            }
        }).start();

    }

    public static Context getContext() {
        return mContext;
    }

    @SuppressLint("CheckResult")
    public void initDataFromSheet() {


        try {
            AssetManager assetManager = mContext.getAssets();
            Workbook book = Workbook.getWorkbook(assetManager.open("eventlogger.xls"));
            Sheet sheet = book.getSheet(0);
            for (int i = 0; i < sheet.getRows(); i++) {
                EventLoggerData eventLoggerData = new EventLoggerData();
                eventLoggerData.setKey(sheet.getCell(2, i).getContents());
                eventLoggerData.setAction(sheet.getCell(3, i).getContents());
                eventLoggerData.setIsImportance("1".equals(sheet.getCell(4, i).getContents()) ? 1 : 0);
                EventLoggerDatabase.getInstance(mContext).getEventLoggerDataDao().insert(eventLoggerData);
            }

            book.close();
            mEventLoggerCollectInitSuccess.initFinishCallBack();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }


}
