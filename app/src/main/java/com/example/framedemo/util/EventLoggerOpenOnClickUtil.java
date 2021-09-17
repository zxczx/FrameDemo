package com.example.framedemo.util;


public class EventLoggerOpenOnClickUtil {

    /**
     * 最小允许间隔，高于则无法生效
     */
    private static final long INTERVAL = 500;

    /**
     * 上次点击时间点
     */
    private static long lastTime;

    /**
     * 连续点击总次数
     */
    private static final int LAST_COUNT = 10;
    /**
     * 连续点击次数
     */
    private static int num = 0;

    public static boolean isOpen() {
        long nowTime = System.currentTimeMillis();
        long interval = nowTime - lastTime;
        lastTime = System.currentTimeMillis();
        if (interval > 0 && interval < INTERVAL) {
            num = num + 1;
        } else {
            num = 0;
        }
        if (num >= LAST_COUNT) {
            num = 0;
            return true;
        } else {
            return false;
        }

    }

}
