package com;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.amessage.eventloggercollectutils.BuildConfig;
import com.amessage.eventloggercollectutils.R;
import com.amessage.eventloggercollectutils.db.EventLoggerData;
import com.amessage.eventloggercollectutils.db.EventLoggerDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 需要在主Application初始化
 * 首次进入EventLoggerCollectActivity 记录打点开始
 * 使用直接调用EventLoggerUtils.upEventLoggerData
 */
public class EventLoggerCollectUtilsApplication {

    private static Context mContext;

    private static volatile EventLoggerCollectUtilsApplication instance = null;

    public static boolean isOpen = false;


    public static EventLoggerCollectUtilsApplication getInstance() {
        if (instance == null) {
            synchronized (EventLoggerCollectUtilsApplication.class) {
                if (instance == null) {
                    instance = new EventLoggerCollectUtilsApplication();
                }
            }
        }

        return instance;
    }

    public void initialize(@NonNull Context context) {
        mContext = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                initDataFromSheet();
            }
        }).start();

    }

    public static Context getContext() {
        return mContext;
    }

    public void initDataFromSheet() {
        AssetManager assetManager = mContext.getAssets();
        try {

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
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }


}
