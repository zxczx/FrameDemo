package com.example.framedemo.common.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import com.example.framedemo.ui.my.roomDemo.RoomDemoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FramePushMsgManager {
    private static final String TAG = FramePushMsgManager.class.getSimpleName();
    public static final String KEY_MSG_TYPE = "msg_type";
    public static final String MSG_TYPE_ROOM_DEMO = "room_demo";

    public static final String KEY_NOTIFICATION_PICTURE = "notification_picture";

    public static final String KEY_PACKAGE_NAME = "package_name";
    public static final String KEY_GOOGLE_PLAY_URL = "play_url";



    private static final List<String> KEY_LIST = new ArrayList<>();

    static {
        KEY_LIST.add(KEY_MSG_TYPE);
        KEY_LIST.add(MSG_TYPE_ROOM_DEMO);
        KEY_LIST.add(KEY_NOTIFICATION_PICTURE);
        KEY_LIST.add(KEY_PACKAGE_NAME);
        KEY_LIST.add(KEY_GOOGLE_PLAY_URL);
    }

    public static void handlePushMsg(Context context, Map<String, String> map, boolean showNotification) {

        String type = map.get(KEY_MSG_TYPE);
        if (MSG_TYPE_ROOM_DEMO.equalsIgnoreCase(type)) {
            handleThemePushMsg(context, map, showNotification);
        } else {
            Log.e("XXX", "Unknown MSG TYPE");
        }
    }

    private static void handleThemePushMsg(Context context, Map<String, String> map, boolean showNotification) {
        String theme_name = map.get(MSG_TYPE_ROOM_DEMO);
        String package_name = map.get(KEY_PACKAGE_NAME);
        String google_play_url = map.get(KEY_GOOGLE_PLAY_URL);

        if (isPackageInstalled(context, package_name)) {
            return;
        }

        Intent intent = new Intent(context, RoomDemoActivity.class);
        intent.putExtra("title", theme_name);
        intent.putExtra("package_name", package_name);
        intent.putExtra("url", google_play_url);
        if (showNotification) {
//            createNotification(context, map, intent, RoomDemoActivity.class);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    private static boolean isPackageInstalled(Context context, String pkg) {
        boolean installed = false;
        PackageManager pkgMgr = context.getPackageManager();
        try {
            pkgMgr.getPackageInfo(pkg, 0);
            installed = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return installed;
    }

    public static boolean isPushKey(String key) {
        return KEY_LIST.contains(key);
    }

    public static boolean isFirebasePushMessage(Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return false;
        }
        Set<String> keySet = intent.getExtras().keySet();
        Map<String, String> map = new HashMap<>();
        for (String key : keySet) {
            Object value = intent.getExtras().get(key);
            if (value instanceof String) {
                if (!isPushKey(key)) {
                    continue;
                }
                map.put(key, (String) value);
            }
        }
        return map.size() > 0;
    }
}
