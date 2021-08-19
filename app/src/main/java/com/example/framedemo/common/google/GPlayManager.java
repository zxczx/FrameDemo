package com.example.framedemo.common.google;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by shewenbiao on 17-10-20.
 */

public class GPlayManager {

//    public static final String PLAY_APP_PREFIX = "market://details?id=";
    public static final String PLAY_APP_PREFIX = "https://play.google.com/store/apps/details?id=";

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

    public static String getUrlWithUtmSrc(String packageName, String utmCampaign) {
        return PLAY_APP_PREFIX + packageName + "&referrer=utm_source%3Dappcross%26utm_medium%3Drecommend%26utm_campaign%3D" + utmCampaign;
    }
}
