package com.example.framedemo.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.framedemo.R;


/**
 * Created by wyjia on 12/25/13.
 */
public class PromotionManager {

    public static final String MARKET_APP_PREFIX = "market://details?id=";

    public static boolean searchAppOnPlay(Context context, String pkg) {
        if (context == null) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.android.vending");
        intent.setData(Uri.parse(MARKET_APP_PREFIX + pkg));
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

    public static void openUri(Context context, String uriString) {
        if (TextUtils.isEmpty(uriString)) {
            return;
        }
        Uri uri = Uri.parse(uriString);
        Intent support = new Intent(Intent.ACTION_VIEW);
        support.setData(uri);
        try {
            context.startActivity(support);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
