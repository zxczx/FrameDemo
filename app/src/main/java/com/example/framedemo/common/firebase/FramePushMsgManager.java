package com.example.framedemo.common.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dh on 19-8-7.
 */

public class FramePushMsgManager {
//    private static final String TAG = FBPushMsgManager.class.getSimpleName();
//    public static final String KEY_MSG_TYPE = "msg_type";
//    public static final String MSG_TYPE_THEME = "theme";
//    public static final String MSG_TYPE_FEATURED_THEME = "featured_theme";
//    public static final String MSG_TYPE_ART = "art";
//    public static final String MSG_TYPE_STICKER = "sticker";
//    public static final String MSG_TYPE_STICKER_GALLERY = "sticker_gallery";
//    public static final String MSG_TYPE_HOLIDAY = "holiday";
//    public static final String MSG_TYPE_EMOJI = "emoji";
//    public static final String MSG_TYPE_SEARCH = "search";
//    public static final String MSG_TYPE_SEARCH_TAB2 = "search_tab2";
//    public static final String MSG_TYPE_GIF = "gif";
//    public static final String MSG_TYPE_TIPS = "tips";
//
//    public static final String KEY_NOTIFICATION_TITLE = "notification_title";
//    public static final String KEY_NOTIFICATION_TEXT = "notification_text";
//    public static final String KEY_NOTIFICATION_ICON = "notification_icon";
//    public static final String KEY_NOTIFICATION_TICKER = "notification_ticker";
//    public static final String KEY_NOTIFICATION_BIGTEXT = "notification_bigtext";
//    public static final String KEY_NOTIFICATION_PICTURE = "notification_picture";
//
//    public static final String KEY_THEME_NAME = "theme_name";
//    public static final String KEY_STICKER_NAME = "sticker_name";
//    public static final String KEY_PACKAGE_NAME = "package_name";
//    public static final String KEY_GOOGLE_PLAY_URL = "play_url";
//
//    public static final String KEY_GIF_KEYWORD = "gif_keyword";
//
//    public static final String KEY_SUB_TIP = "sub_tip";

//    public static final ImageSize sIconSize = new ImageSize(128, 128);
    private static final List<String> KEY_LIST = new ArrayList<>();
    static {
//        KEY_LIST.add(KEY_MSG_TYPE);
//        KEY_LIST.add(MSG_TYPE_THEME);
//        KEY_LIST.add(MSG_TYPE_FEATURED_THEME);
//        KEY_LIST.add(MSG_TYPE_ART);
//        KEY_LIST.add(MSG_TYPE_STICKER);
//        KEY_LIST.add(MSG_TYPE_STICKER_GALLERY);
//        KEY_LIST.add(MSG_TYPE_HOLIDAY);
//        KEY_LIST.add(MSG_TYPE_EMOJI);
//        KEY_LIST.add(MSG_TYPE_SEARCH);
//        KEY_LIST.add(MSG_TYPE_SEARCH_TAB2);
//        KEY_LIST.add(MSG_TYPE_GIF);
//        KEY_LIST.add(MSG_TYPE_TIPS);
//        KEY_LIST.add(KEY_NOTIFICATION_TITLE);
//        KEY_LIST.add(KEY_NOTIFICATION_TEXT);
//        KEY_LIST.add(KEY_NOTIFICATION_ICON);
//        KEY_LIST.add(KEY_NOTIFICATION_TICKER);
//        KEY_LIST.add(KEY_NOTIFICATION_BIGTEXT);
//        KEY_LIST.add(KEY_NOTIFICATION_PICTURE);
//        KEY_LIST.add(KEY_THEME_NAME);
//        KEY_LIST.add(KEY_STICKER_NAME);
//        KEY_LIST.add(KEY_PACKAGE_NAME);
//        KEY_LIST.add(KEY_GOOGLE_PLAY_URL);
//        KEY_LIST.add(KEY_GIF_KEYWORD);
//        KEY_LIST.add(KEY_SUB_TIP);
    }

    public static void handlePushMsg(Context context, Map<String, String> map, boolean showNotification) {

//        String type = map.get(KEY_MSG_TYPE);
//        if (MSG_TYPE_TIPS.equalsIgnoreCase(type)) {
//            handleTipsPushMsg(context, map, showNotification);
//        } else {
//            Log.e("XXX", "Unknown MSG TYPE");
//        }
    }

    private static void handleTipsPushMsg(Context context, Map<String, String> map, boolean showNotification) {
//        String subTipStr = map.get(KEY_SUB_TIP);
//
//        TipsManager.Tips subTip = TipsManager.getInstance(context).getSubTip(subTipStr);
//        if (subTip != null) {
//            Intent intent = new Intent(context, TipsActivity.class);
//            intent.putExtra("sub_tip", subTip.name());
//            intent.putExtra(KEY_NOTIFICATION_TITLE, map.get(KEY_NOTIFICATION_TITLE));
//            intent.putExtra(KEY_NOTIFICATION_TEXT, map.get(KEY_NOTIFICATION_TEXT));
//            intent.putExtra(KEY_NOTIFICATION_PICTURE, map.get(KEY_NOTIFICATION_PICTURE));
//            if (subTip == TipsManager.Tips.upgrade) {
//                String package_name = map.get(KEY_PACKAGE_NAME);
//                intent.putExtra("package_name", package_name);
//            }
//            if (showNotification) {
//                createNotification(context, map, intent, TipsActivity.class);
//            } else {
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        }
    }

    private static void createNotification(Context context, Map<String, String> map, Intent intent, Class<?> sourceActivityClass) {

//        String mNotificationPicture = map.get(KEY_NOTIFICATION_PICTURE);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(sourceActivityClass);
//        stackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//
//        builder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.ic_inapp_logo);
//
//        if (!TextUtils.isEmpty(mNotificationPicture)) {
//            Bitmap picture = ImageLoaderUtil.loadImageSync(mNotificationPicture, null);
//            if (picture != null) {
//                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_custom_view);
//                remoteViews.setImageViewBitmap(R.id.notification_custom_image, picture);
//                builder.setContent(remoteViews);
//            } else {
//                showNormalNotification(map, builder);
//            }
//        } else {
//            showNormalNotification(map, builder);
//        }
//        NotificationManager mgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        Utils.setChannel(mgr, builder, context, NotificationManager.IMPORTANCE_LOW);
//        Notification notification = builder.build();
//
//        try {
//            mgr.notify(0, notification);
//        } catch (SecurityException ex) {
//            Log.w(TAG, "SecurityException happens after showing notify. ");
//        }
    }

    private static void showNormalNotification(Map<String, String> map, NotificationCompat.Builder builder) {
//        String mNotificationIcon = map.get(KEY_NOTIFICATION_ICON);
//        String mNotificationTitle = map.get(KEY_NOTIFICATION_TITLE);
//        String mNotificationText = map.get(KEY_NOTIFICATION_TEXT);

//        String mNotificationTicker = map.get(KEY_NOTIFICATION_TICKER);
//        String mNotificationBigtext = map.get(KEY_NOTIFICATION_BIGTEXT);
//        if (!TextUtils.isEmpty(mNotificationIcon)) {
//            Bitmap icon = ImageLoaderUtil.loadImageSync(mNotificationIcon, sIconSize);
//            if (icon != null) {
//                builder.setLargeIcon(icon);
//            }
//        }

//        if (!TextUtils.isEmpty(mNotificationTitle)) {
//            builder.setContentTitle(mNotificationTitle);
//        }
//
//        if (!TextUtils.isEmpty(mNotificationText)) {
//            builder.setContentText(mNotificationText);
//        }

//        if (!TextUtils.isEmpty(mNotificationTicker)) {
//            builder.setTicker(mNotificationTicker);
//        }
//
//        if (!TextUtils.isEmpty(mNotificationBigtext)) {
//            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(mNotificationBigtext));
//        }
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
