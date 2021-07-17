package com.example.framedemo.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class ActivityUtil {

    public static boolean isActivityDestroyed(Activity activity) {
        return activity == null || activity.isFinishing() ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed());
    }

    /**
     * 设置透明状态栏。
     */
    public static void setTransparentStatusBar(Activity activity,String color) {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (TextUtils.isEmpty(color)){
                window.setStatusBarColor(Color.TRANSPARENT);
            }else {
                window.setStatusBarColor(Color.parseColor(color));
            }

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        View content = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        if (content != null) {
            content.setFitsSystemWindows(true);
        }
    }
}
