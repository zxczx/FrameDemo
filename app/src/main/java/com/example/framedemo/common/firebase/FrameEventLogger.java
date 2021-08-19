package com.example.framedemo.common.firebase;

import android.content.Context;
import android.os.Bundle;

import com.example.framedemo.FrameApplication;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * 实现打点的关键类
 */
public class FrameEventLogger {

    public static void logEvent(String trackerName) {
        logEvent(trackerName, null);
    }

    public static void logEvent(String trackerName, Bundle bundle) {
        logEvent(FrameApplication.getApp(), trackerName, bundle);
    }

    public static void logEvent(Context context, String trackerName) {
        if (context == null) {
            return;
        }
        logEvent(context.getApplicationContext(), trackerName, null);
    }

    public static void logEvent(Context context, String trackerName, Bundle bundle) {
        if (context == null) {
            return;
        }
        putFirebaseTracker(context, trackerName, bundle);
    }

    private static void putFirebaseTracker(Context context, String trackerName, Bundle bundle) {
        // use Firebase analytics
        try {
            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
            firebaseAnalytics.logEvent(trackerName, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
