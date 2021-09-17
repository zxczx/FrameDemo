package com.example.framedemo.common.google;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 开启某应用（packageName）的PlayStore主页。
 * 常用于应用导量。
 */
public class GPlayManager {

    public static final String PLAY_APP_PREFIX = "https://play.google.com/store/apps/details?id=";

    /**
     * 启动PlayStore，并深度链接到query的应用的主页。
     *
     * @param context 当前上下文。
     * @param query   [getQuery]
     */
    public static boolean findAppFromGooglePlay(Context context, String query) {
        if (context == null) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.android.vending");
        intent.setData(Uri.parse(query));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取用于启动应用PlayStore主页的深度链接。
     *todo
     * @param packageName 目标应用的包名。
     * @param utmCampaign 推荐源描述，一般为当前应用名称或某个推荐位的描述，取决于实际安排。
     */
    public static String getUrlWithUtmSrc(String packageName, String utmCampaign) {
        return PLAY_APP_PREFIX + packageName + "&referrer=utm_source%3Dappcross%26utm_medium%3Drecommend%26utm_campaign%3D" + utmCampaign;
    }

    public static String getUrlWithUtmSrc(String packageName) {
        return PLAY_APP_PREFIX + packageName;
    }
}
