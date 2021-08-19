package com.example.framedemo.util;


import android.widget.Toast;

import androidx.annotation.StringRes;


import com.example.framedemo.FrameApplication;
import com.example.framedemo.ui.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Toaster工具类
 */
public class Toaster {
    public static void show(@StringRes int resId) {
        Toast.makeText(FrameApplication.getApp(), FrameApplication.getApp().getString(resId), Toast.LENGTH_SHORT).show();
    }

    public static void show(CharSequence text) {
        Toast.makeText(FrameApplication.getApp(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }

}
